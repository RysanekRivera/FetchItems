package com.rysanek.fetchitemslist.presentation.viewmodels

import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rysanek.fetchitemslist.R
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
    
    fun setSortedList(menuItem: MenuItem): Boolean {
        val listItem = when (menuItem.itemId) {
            R.id.miGroup1 -> 1
            R.id.miGroup2 -> 2
            R.id.miGroup3 -> 3
            R.id.miGroup4 -> 4
            else -> null
        }
        
        when(listItem) {
            null -> _sortedList.postValue(getListOfItemsFromDbLiveData().asLiveData())
            else -> _sortedList.postValue(getListOfItemsSortedFromDb(listItem).asLiveData())
        }
        
        return true
    }
    
    private fun getListOfItemsFromDbLiveData() = flow { emit(getListItems.getAllListItemsFromDbLiveData()) }
    
    private fun getListOfItemsSortedFromDb(listItem: Int) = flow { emit(getListItems.getAllListItemsSortedFromDb(listItem)) }
    
    fun fetchData() = viewModelScope.launch(Dispatchers.IO) { getListItems.remoteFetch() }
    
    fun hasInternetConnection() = getListItems.hasInternetConnection()
}
