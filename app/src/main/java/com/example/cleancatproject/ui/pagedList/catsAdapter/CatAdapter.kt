package com.example.cleancatproject.ui.pagedList.catsAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.ItemCatBinding
import com.example.cleancatproject.model.cat.Cat


class CatAdapter(private val listener: Listener) :
    PagedListAdapter<Cat, CatAdapter.CatViewHolder>(CAT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        if (cat != null) {
            holder.bind(cat)
        }
    }

    //Логика по UI - во ViewHolder
    class CatViewHolder(private val listener: Listener, view: View) :
        RecyclerView.ViewHolder(view) {
        private var mBinding = ItemCatBinding.bind(view)
        private var mImageView = mBinding.imageForRecyclerId
        private var mButton = mBinding.addToFavoriteBtnId
        private var cat: Cat? = null
        init {
            //сетим листенеры
            mImageView.setOnClickListener {
                cat?.let {
                    listener.onImageClicked(it)
                }
            }
            mButton.setOnClickListener {
                cat?.let {
                    listener.onAddToFavoritesClicked(it)
                }
            }
        }
        fun bind(cat: Cat) {
            this.cat = cat
            Glide.with(mImageView.context)
                .load(cat.url)
                .into(mImageView)
        }
    }

    companion object {
        private val CAT_COMPARATOR = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
                newItem == oldItem
        }
    }

    interface Listener {
        //взаимодействие между айтемами списка и фрагментом через этот интерфейс
        fun onImageClicked(cat: Cat) {
        }

        fun onAddToFavoritesClicked(cat: Cat) {
        }
    }
}