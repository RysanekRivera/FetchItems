package com.rysanek.fetchitemslist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.fetchitemslist.data.util.DownloadState
import com.rysanek.fetchitemslist.domain.usecases.GetListItems
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(
    private val getListItems: GetListItems
): ViewModel() {
    
    val downloadState = getListItems.downloadState
    
    fun getListOfItemsFromDb() = getListItems.getAllListItemsFromDb()
    
    fun fetchData() = viewModelScope.launch(Dispatchers.IO) { getListItems.remoteFetch() }
    
    fun hasInternetConnection() = getListItems.hasInternetConnection()
}
