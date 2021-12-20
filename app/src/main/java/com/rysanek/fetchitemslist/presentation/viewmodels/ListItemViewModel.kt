package com.rysanek.fetchitemslist.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rysanek.fetchitemslist.domain.usecases.GetListItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(
    private val getListItems: GetListItems
): ViewModel() {
    
    val downloadState = getListItems.downloadState
    
    private val _sortedList = MutableLiveData(getListOfItemsFromDbLiveData().asLiveData())
    val sortedList get() = _sortedList
    
    fun setSortedList(listItem: Int?) {
        when(listItem) {
            null -> _sortedList.postValue(getListOfItemsFromDbLiveData().asLiveData())
            else -> _sortedList.postValue(getListOfItemsSortedFromDb(listItem).asLiveData())
        }
    }
    
    private fun getListOfItemsFromDbLiveData() = flow { emit(getListItems.getAllListItemsFromDbLiveData()) }
    
    private fun getListOfItemsSortedFromDb(listItem: Int) = flow { emit(getListItems.getAllListItemsSortedFromDb(listItem)) }
    
    fun fetchData() = viewModelScope.launch(Dispatchers.IO) { getListItems.remoteFetch() }
    
    fun hasInternetConnection() = getListItems.hasInternetConnection()
}
