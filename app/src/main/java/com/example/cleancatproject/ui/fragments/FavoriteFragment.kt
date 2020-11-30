package com.example.cleancatproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleancatproject.R
import com.example.cleancatproject.databinding.FragmentFavoriteBinding
import com.example.cleancatproject.model.favorite.Favorite
import com.example.cleancatproject.ui.pagedList.favoriteAdapter.FavoriteAdapter
import com.example.cleancatproject.utils.CAT_ID
import com.example.cleancatproject.utils.nextFragment
import com.example.cleancatproject.viewmodel.FavoriteFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteAdapter.Listener {
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private lateinit var mBinding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var mRecyclerView: RecyclerView
    private val mFavoriteFragmentViewModel: FavoriteFragmentViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFavoriteBinding.inflate(layoutInflater)
        adapter = FavoriteAdapter(this)
        mRecyclerView = mBinding.recyclerViewFavorite
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mFavoriteFragmentViewModel.favoritePagedListLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onImageClicked(favorite: Favorite) {
        CAT_ID = favorite.image_id
        nextFragment(R.id.fragmentWindowId, CatDetailsFragment())
    }

    override fun onDeleteFromFavoriteClicked(favorite: Favorite) {
        //прокидываем обработку клика во вьюмодель, чтобы не использовать apiService из фрагмента
        mFavoriteFragmentViewModel.onDeleteFromFavoriteClicked(favorite)
    }
}