package com.rysanek.fetchitemslist.data.remote.apis

import com.rysanek.fetchitemslist.data.remote.dtos.ListItemDTO
import retrofit2.Response
import retrofit2.http.GET

interface ListItemAPI {
    
    @GET("/hiring.json")
    suspend fun getListItems(): Response<List<ListItemDTO>>
}