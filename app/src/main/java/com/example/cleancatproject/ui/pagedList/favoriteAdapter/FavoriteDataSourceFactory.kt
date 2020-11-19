package com.example.cleancatproject.ui.pagedList.favoriteAdapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.cleancatproject.model.favorite.Favorite
import com.example.cleancatproject.network.FavoriteApiService
import javax.inject.Inject

class FavoriteDataSourceFactory @Inject constructor(private val apiService: FavoriteApiService) :
    DataSource.Factory<Int, Favorite>() {

    val favoriteLiveDataSource = MutableLiveData<FavoriteDataSource>()

    override fun create(): androidx.paging.DataSource<Int, Favorite> {
        val favoriteDataSource = FavoriteDataSource(apiService)
        favoriteLiveDataSource.postValue(favoriteDataSource)
        return favoriteDataSource
    }
}