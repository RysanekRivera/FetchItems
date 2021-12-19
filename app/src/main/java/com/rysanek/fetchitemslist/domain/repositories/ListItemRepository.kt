package com.rysanek.fetchitemslist.domain.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.data.remote.dtos.ListItemDTO
import com.rysanek.fetchitemslist.data.util.DownloadState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ListItemRepository {
    
    suspend fun fetchRemoteListItems(): Flow<Response<List<ListItemDTO>>>
    
    suspend fun insertItemsListIntoDb(list: List<ListItemEntity>)
    
    suspend fun deleteAllItemsFromDb()
    
    fun getAllListItemsFromDb(): LiveData<List<ListItemEntity>>
    
    fun getRemoteContentLength(): Long
    
    fun hasInternetConnection(context: Context): Boolean
}