package com.barros.beerapp.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.repository.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val beerRepository: BeerRepository
) : ViewModel() {

    var search = MutableLiveData("")

    private val _navigateToDetail = MutableLiveData<BeerItem>()
    val navigateToDetail: LiveData<BeerItem> = _navigateToDetail

    fun getBeers(): Flow<PagingData<BeerItem>> {
        return beerRepository.getBeers(search.value!!)
            .cachedIn(viewModelScope)
    }

    fun displayPropertyDetails(item: BeerItem) {
        _navigateToDetail.postValue(item)
    }

    fun displayPropertyDetailsComplete() {
        _navigateToDetail.postValue(null)
    }
}
