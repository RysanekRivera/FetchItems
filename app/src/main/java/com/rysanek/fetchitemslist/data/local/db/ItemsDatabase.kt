package com.rysanek.fetchitemslist.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity

@Database(
    entities = [ListItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ItemsDatabase: RoomDatabase(){
    
    abstract val itemsDao: ItemsDAO
}