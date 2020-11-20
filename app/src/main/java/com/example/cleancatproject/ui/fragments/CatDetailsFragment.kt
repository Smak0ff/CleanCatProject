package com.example.cleancatproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.FragmentCatDetailBinding
import com.example.cleancatproject.viewmodel.CatDetailsFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CatDetailsFragment : Fragment(R.layout.fragment_cat_detail) {
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var mBinding: FragmentCatDetailBinding
    lateinit var mImageView: ImageView
    lateinit var mTextView: TextView
    lateinit var mButton: Button
    private val mCatDetailsFragmentViewModel: CatDetailsFragmentViewModel by viewModels { vmFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCatDetailBinding.inflate(layoutInflater)
        mImageView = mBinding.detailsImageId
        mTextView = mBinding.informationId
        mButton = mBinding.voteForTheCatId
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mCatDetailsFragmentViewModel.apiLiveData.observe(this, Observer {
            Glide.with(this)
                .load(it?.url)
                .into(mBinding.detailsImageId)
            mBinding.informationId.text = it.toString()
        })

        mButton.setOnClickListener {
            mCatDetailsFragmentViewModel.onVoteForTheKittyClicked()
            mButton.isEnabled=false
            mButton.text="Спасибо за голос"
            //проверка
        }
    }
}