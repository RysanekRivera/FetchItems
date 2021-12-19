package com.rysanek.fetchitemslist.data.remote.dtos

import com.rysanek.fetchitemslist.data.local.entities.ListItemEntity

data class ListItemDTO(
    val id: Int?,
    val listId: Int?,
    val name: String?
) {
    
    fun toListItemEntity() = ListItemEntity(itemId = id, listId = listId, name = name)
}