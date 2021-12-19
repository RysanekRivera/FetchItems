package com.rysanek.fetchitemslist.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity
import com.rysanek.fetchitemslist.data.util.Constants.ITEMS_TABLE
import com.rysanek.fetchitemslist.data.util.Constants.ITEM_NAME
import com.rysanek.fetchitemslist.data.util.Constants.LIST_ID

@Dao
interface ItemsDAO {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ListItemEntity>)
    
    @Query("DELETE FROM $ITEMS_TABLE")
    suspend fun deleteAllItems()
    
    @Query("SELECT * FROM $ITEMS_TABLE ORDER BY $LIST_ID ASC, $ITEM_NAME ASC")
    fun getAllListItems(): LiveData<List<ListItemEntity>>
}