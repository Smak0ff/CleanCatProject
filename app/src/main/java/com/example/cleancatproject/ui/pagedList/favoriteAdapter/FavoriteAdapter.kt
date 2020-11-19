package com.example.cleancatproject.ui.pagedList.favoriteAdapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.ItemFavoriteBinding
import com.example.cleancatproject.model.favorite.Favorite

class FavoriteAdapter(private val listener: Listener) :
    PagedListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(FAVORITE_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        if (favorite != null) {
            holder.bind(favorite)
        }

    }

    class FavoriteViewHolder(private val listener: Listener, view: View) :
        RecyclerView.ViewHolder(view) {
        var mBinding = ItemFavoriteBinding.bind(view)
        var mImageView = mBinding.imageForRecyclerFavoriteId
        var mButton = mBinding.deleteFromFavoriteBtnId

        private var favorite: Favorite? = null

        init {
            //сетим листенеры
            mImageView.setOnClickListener {
                favorite?.let {
                    listener.onImageClicked(it)
                }
            }

            mButton.setOnClickListener {
                favorite?.let {
                    listener.onDeleteFromFavoriteClicked(it)
                }
            }
        }

        fun bind(favorite: Favorite) {
            this.favorite = favorite
            Glide.with(mImageView.context)
                .load(favorite.image.url)
                .into(mImageView)
        }

    }

    companion object {
        private val FAVORITE_COMPARATOR = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean =
                newItem == oldItem
        }
    }

    interface Listener {
        fun onImageClicked(favorite: Favorite) {

        }

        fun onDeleteFromFavoriteClicked(favorite: Favorite) {

        }

    }

}