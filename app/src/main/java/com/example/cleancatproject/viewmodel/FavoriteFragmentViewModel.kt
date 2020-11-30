package com.example.cleancatproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cleancatproject.di.ViewModelFactory
import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.model.favorite.Favorite
import com.example.cleancatproject.network.FavoriteApiService
import com.example.cleancatproject.ui.pagedList.favoriteAdapter.FavoriteDataSource
import com.example.cleancatproject.ui.pagedList.favoriteAdapter.FavoriteDataSourceFactory
import com.example.cleancatproject.utils.PAGE_SIZE
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class FavoriteFragmentViewModel @Inject constructor(
    dataSourceFactory: FavoriteDataSourceFactory,
    private val favoriteApiService: FavoriteApiService
) : ViewModel() {
    //Нам необходимо передать данные из CatDataSource, создадим для них хранилище LiveData
    private val liveDataFavoriteDataSource: LiveData<FavoriteDataSource>

    //Создаём LiveData для обновления PagedList
    val favoritePagedListLiveData: LiveData<PagedList<Favorite>>

    //Блок инициализации срабатывает после создания экземпляра ViewModel
    init {
        //Создаём объект фабрики CatDataSource и передаём из него данные в хранилище, которое мы инициализируем
        liveDataFavoriteDataSource = dataSourceFactory.favoriteLiveDataSource
        //Создаём билдер для PagedList
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        //Инициализируем  PagedList в LiveData
        favoritePagedListLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }

    fun onDeleteFromFavoriteClicked(favorite: Favorite) {
        val call = favoriteApiService.deleteFromFavorite(favorite.id.toString())
        call.enqueue(object : retrofit2.Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                liveDataFavoriteDataSource.value?.invalidate()
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println(t.message)
            }
        })
    }

    class Factory(viewModel: FavoriteFragmentViewModel) :
        ViewModelFactory<FavoriteFragmentViewModel>(viewModel)
}