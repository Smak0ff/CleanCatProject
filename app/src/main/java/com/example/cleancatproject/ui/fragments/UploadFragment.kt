package com.example.cleancatproject.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.FragmentUploadBinding
import com.example.cleancatproject.model.upload.Upload
import com.example.cleancatproject.ui.pagedList.uploadAdapter.UploadAdapter
import com.example.cleancatproject.utils.CAT_ID
import com.example.cleancatproject.utils.nextFragment
import com.example.cleancatproject.viewmodel.UploadFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.AndroidSupportInjection
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UploadFragment : Fragment(R.layout.fragment_upload), UploadAdapter.Listener {
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var mBinding: FragmentUploadBinding
    private lateinit var mButton: FloatingActionButton
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: UploadAdapter
    private lateinit var file: File
    private lateinit var requestBody: RequestBody
    private lateinit var multipart: MultipartBody.Part
    private lateinit var currentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE = 1
    private val mUploadFragmentViewModel: UploadFragmentViewModel by viewModels { vmFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUploadBinding.inflate(layoutInflater)
        adapter = UploadAdapter(this)
        mButton = mBinding.uploadPhotoId
        mRecyclerView = mBinding.recyclerViewUploadId
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mUploadFragmentViewModel.uploadPagedListLiveData.observe(this, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
        mButton.setOnClickListener {
            try {
                file = createImageFile()
                var uriFile = FileProvider.getUriForFile(
                    requireContext(), "com.example.cleancatproject.fileprovider", file
                )
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile)
                val pickIntent = Intent(
                    Intent.ACTION_GET_CONTENT
                )
                pickIntent.type = "image/*"
                val chooser =
                    Intent.createChooser(cameraIntent, getString(R.string.choose_send_method))
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
                startActivityForResult(chooser, REQUEST_IMAGE_CAPTURE)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), R.string.could_not_create_file, Toast.LENGTH_SHORT)
                    .show()
                println(e.message)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                val selectedImage: Uri? = data.data
                var bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    selectedImage
                )
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                requestBody = RequestBody.create(MediaType.parse("image/jpeg"), byteArray)
                multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                mUploadFragmentViewModel.onUploadPhotoClicked(multipart)
            } else {
                var array = file.readBytes()
                requestBody = RequestBody.create(MediaType.parse("image/jpeg"), array)
                multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                mUploadFragmentViewModel.onUploadPhotoClicked(multipart)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onImageClicked(upload: Upload) {
        super.onImageClicked(upload)
        CAT_ID = upload.id
        nextFragment(R.id.fragmentWindowId, UploadAnalysisFragment())
    }

    override fun onLongTap(upload: Upload) {
        mUploadFragmentViewModel.onImageLongClicked(upload)
    }
}
