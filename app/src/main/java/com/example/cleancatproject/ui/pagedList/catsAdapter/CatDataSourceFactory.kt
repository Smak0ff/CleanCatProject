package com.example.cleancatproject.ui.pagedList.catsAdapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.cleancatproject.model.cat.Cat
import com.example.cleancatproject.network.ImagesApiService
import javax.inject.Inject


class CatDataSourceFactory @Inject constructor(private val apiService: ImagesApiService) :
    DataSource.Factory<Int, Cat>() {
    //Создаём LiveData для полученного с фабрики объекта(данные для списка)
    val catDataSourceLiveData = MutableLiveData<CatDataSource>()

    //Создаём объект данных, назначаем его в созданную LiveData
    override fun create(): DataSource<Int, Cat> {
        val catDataSource = CatDataSource(apiService)
        catDataSourceLiveData.postValue(catDataSource)
        return catDataSource
    }
}