package com.rysanek.fetchitemslist.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.data.util.Constants.ITEMS_TABLE
import com.rysanek.fetchitemslist.data.util.Constants.ITEM_ID
import com.rysanek.fetchitemslist.data.util.Constants.ITEM_NAME
import com.rysanek.fetchitemslist.data.util.Constants.LIST_ID

@Dao
interface ItemsDAO {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ListItemEntity>)
    
    @Query("DELETE FROM $ITEMS_TABLE")
    suspend fun deleteAllItems()
    
    @Query("SELECT * FROM $ITEMS_TABLE ORDER BY $LIST_ID ASC, $ITEM_ID ASC, $ITEM_NAME ASC")
    fun getAllListItemsLiveData(): LiveData<List<ListItemEntity>>
    
    @Query("SELECT * FROM $ITEMS_TABLE WHERE list_id = :listId ORDER BY $LIST_ID ASC, $ITEM_ID ASC, $ITEM_NAME ASC")
    fun getAllItemsSortedByListId(listId: Int): LiveData<List<ListItemEntity>>
}