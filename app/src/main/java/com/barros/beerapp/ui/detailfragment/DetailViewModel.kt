package com.barros.beerapp.ui.detailfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.barros.beerapp.model.BeerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    handle: SavedStateHandle
) : ViewModel() {

    private val _beerItem = MutableLiveData(handle.get<BeerItem>("beerItem")!!)
    val beerItem: LiveData<BeerItem> = _beerItem
}
