package com.example.cleancatproject.ui.pagedList.uploadAdapter


import android.view.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.ItemUploadBinding
import com.example.cleancatproject.model.upload.Upload

class UploadAdapter(private val listener: Listener) :
    PagedListAdapter<Upload, UploadAdapter.UploadViewHolder>(UPLOAD_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_upload, parent, false)
        return UploadViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        val upload = getItem(position)
        if (upload != null) {
            holder.bind(upload)
        }
    }

    class UploadViewHolder(private val listener: Listener, view: View) :
        RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener {


        var mBinding = ItemUploadBinding.bind(view)
        var mImageView = mBinding.imageForRecyclerUploadId

        private var upload: Upload? = null

        init {
            mImageView.setOnClickListener {
                upload?.let {
                    listener.onImageClicked(it)
                }
            }
            mImageView.setOnCreateContextMenuListener(this)
        }

        fun bind(upload: Upload) {
            this.upload = upload
            Glide.with(mImageView.context)
                .load(upload.url)
                .into(mImageView)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            var menuItem = menu?.add("Удалить")
            menuItem?.setOnMenuItemClickListener(this)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            upload?.let { listener.onLongTap(it) }
            return true
        }
    }

    companion object {
        private val UPLOAD_COMPARATOR = object : DiffUtil.ItemCallback<Upload>() {
            override fun areItemsTheSame(oldItem: Upload, newItem: Upload): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Upload, newItem: Upload): Boolean =
                newItem == oldItem
        }
    }

    interface Listener {
        fun onImageClicked(upload: Upload) {
        }

        fun onLongTap(upload: Upload)
    }

}