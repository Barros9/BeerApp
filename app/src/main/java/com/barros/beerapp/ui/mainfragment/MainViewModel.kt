package com.barros.beerapp.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barros.beerapp.model.BeerItem
import com.barros.beerapp.model.Result
import com.barros.beerapp.repository.BeerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val beerRepository: BeerRepository
) : ViewModel() {

    var page = 1
    var search = MutableLiveData("")

    private val _navigateToDetail = MutableLiveData<BeerItem>()
    val navigateToDetail: LiveData<BeerItem> = _navigateToDetail

    private var _uiState = MutableLiveData<Result<List<BeerItem>>>()
    val uiState: LiveData<Result<List<BeerItem>>> = _uiState

    init {
        updateUiState()
    }

    fun updateUiState() {
        viewModelScope.launch {
            beerRepository.getBeers(search.value!!, page).collect {
                _uiState.value = it
            }
        }
    }

    fun displayPropertyDetails(item: BeerItem) {
        _navigateToDetail.postValue(item)
    }
}
