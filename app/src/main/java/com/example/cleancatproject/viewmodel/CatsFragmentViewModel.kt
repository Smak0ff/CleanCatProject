package com.example.cleancatproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.cleancatproject.di.ViewModelFactory
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.model.cat.ResponseMessage
import com.example.cleancatproject.model.favorite.FavoriteVote
import com.example.cleancatproject.network.FavoriteApiService
import com.example.cleancatproject.ui.pagedList.catsAdapter.CatDataSource
import com.example.cleancatproject.ui.pagedList.catsAdapter.CatDataSourceFactory
import com.example.cleancatproject.utils.PAGE_SIZE
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

//Зависимости приходят во вьюмодель через конструктор
//CatDataSourceFactory провайдится в CatsFragmentModule
//FavoriteApiService провайдится из NetworkModule
class CatsFragmentViewModel @Inject constructor(
    dataSourceFactory: CatDataSourceFactory,
    private val favoriteApiService: FavoriteApiService
) : ViewModel() {

    //Нам необходимо передать данные из CatDataSource, создадим для них хранилище LiveData
    private val liveDataCatDataSource: LiveData<CatDataSource>

    //Создаём LiveData для обновления PagedList
    val catPagedListLiveData: LiveData<PagedList<Cat>>


    //Блок инициализации срабатывает после создания экземпляра ViewModel
    init {
        //Создаём объект фабрики CatDataSource и передаём из него данные в хранилище, которое мы инициализируем
        liveDataCatDataSource = dataSourceFactory.catDataSourceLiveData

        //Создаём билдер для PagedList
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        //Инициализируем  PagedList в LiveData
        catPagedListLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }

    fun onAddToFavoritesClicked(cat: Cat) {
        var favoriteVote = FavoriteVote(cat.id)
        val call = favoriteApiService.postFavorite(favoriteVote)
        call.enqueue(object : retrofit2.Callback<ResponseMessage> {

            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println(t.message)
            }
        })
    }

    class Factory(viewModel: CatsFragmentViewModel) :
        ViewModelFactory<CatsFragmentViewModel>(viewModel)
}