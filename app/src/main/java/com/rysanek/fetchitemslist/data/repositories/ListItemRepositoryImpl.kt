package com.rysanek.fetchitemslist.data.repositories

import android.content.Context
import com.rysanek.fetchitemslist.data.local.db.ItemsDAO
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.data.remote.apis.ListItemAPI
import com.rysanek.fetchitemslist.data.util.Constants.GET_HEADER_URL
import com.rysanek.fetchitemslist.domain.repositories.ListItemRepository
import com.rysanek.fetchitemslist.presentation.utils.fetchContentLength
import com.rysanek.fetchitemslist.presentation.utils.hasInternetConnection
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListItemRepositoryImpl @Inject constructor(
    private val api: ListItemAPI,
    private val dao: ItemsDAO
): ListItemRepository {
    
    override suspend fun fetchRemoteListItems() = flow { emit(api.getListItems()) }
    
    override suspend fun insertItemsListIntoDb(list: List<ListItemEntity>) = dao.insertItems(list)
    
    override suspend fun deleteAllItemsFromDb() = dao.deleteAllItems()
    
    override fun getAllListItemsFromDb() = dao.getAllListItems()
    
    override fun getRemoteContentLength() = fetchContentLength(GET_HEADER_URL)?.toLong() ?: -1L
    
    override fun hasInternetConnection(context: Context) = context.hasInternetConnection()
}