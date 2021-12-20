package com.rysanek.fetchitemslist.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rysanek.fetchitemslist.data.util.Constants.ITEMS_TABLE
import com.rysanek.fetchitemslist.data.util.Constants.ITEM_ID
import com.rysanek.fetchitemslist.data.util.Constants.ITEM_NAME
import com.rysanek.fetchitemslist.data.util.Constants.LIST_ID

@Entity(tableName = ITEMS_TABLE)
data class ListItemEntity(
    @ColumnInfo(name = ITEM_ID)val itemId: Int?,
    @ColumnInfo(name = LIST_ID) val listId: Int?,
    @ColumnInfo(name = ITEM_NAME) val name: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)