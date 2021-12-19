package com.rysanek.fetchitemslist.domain.usecases

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.data.remote.dtos.ListItemDTO
import com.rysanek.fetchitemslist.data.repositories.ListItemRepositoryImpl
import com.rysanek.fetchitemslist.data.util.Constants.LOCAL_CONTENT_LENGTH
import com.rysanek.fetchitemslist.data.util.Constants.REMOTE_CONTENT_LENGTH
import com.rysanek.fetchitemslist.data.util.DownloadState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListItems @Inject constructor(
    private val context: Context, private val repository: ListItemRepositoryImpl
) {
    private val _downloadState = MutableLiveData<DownloadState>(DownloadState.Idle)
    val downloadState: LiveData<DownloadState> = _downloadState
    
    private fun postDownloadState(state: DownloadState) {
        _downloadState.postValue(state)
    }
    
    private val contentLength: SharedPreferences = context.getSharedPreferences(LOCAL_CONTENT_LENGTH, MODE_PRIVATE)
    
    /**
     * Will check if the remote data has changed by comparing the [REMOTE_CONTENT_LENGTH] with
     * the [LOCAL_CONTENT_LENGTH]. If they are different then it will delete the database and
     * download the new data. Different [DownloadState] will be called during this process.
     */
    suspend fun remoteFetch() {
        
        postDownloadState(DownloadState.Checking)
        
        val remoteContentLength = repository.getRemoteContentLength()
        
        val localContentLength = contentLength.getLong(LOCAL_CONTENT_LENGTH, 0)
        
        if (remoteContentLength != localContentLength) {
            postDownloadState(DownloadState.Downloading)
            
            repository.deleteAllItemsFromDb()
            
            fetchData()
            
            postDownloadState(DownloadState.Finished)
        } else {
            postDownloadState(DownloadState.Idle)
        }
    }
    
    fun hasInternetConnection() = repository.hasInternetConnection(context)
    
    private suspend fun fetchData() = repository.fetchRemoteListItems().catch { e ->
            Log.e("GetListItems", "Error: ${e.message}")
            postDownloadState(DownloadState.Error.message(e.message))
        }.filter { response ->
            response.isSuccessful
        }.map { response ->
            
            contentLength.edit { putLong(LOCAL_CONTENT_LENGTH, response.raw().headers[REMOTE_CONTENT_LENGTH]?.toLong() ?: -1) }
            
            val filteredList = mutableListOf<ListItemEntity>()
            
            response.body()?.filter { !it.name.isNullOrEmpty() }?.sortedWith(compareBy<ListItemDTO> { it.listId }.thenBy { it.name })?.forEach { filteredList.add(it.toListItemEntity()) }
            
            repository.insertItemsListIntoDb(filteredList)
            
        }.collect()
    
    fun getAllListItemsFromDb() = repository.getAllListItemsFromDb()
}