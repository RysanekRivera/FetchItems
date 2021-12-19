package com.rysanek.fetchitemslist.domain.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.rysanek.fetchitemslist.data.local.db.ItemsDAO
import com.rysanek.fetchitemslist.data.local.db.ItemsDatabase
import com.rysanek.fetchitemslist.data.remote.apis.ListItemAPI
import com.rysanek.fetchitemslist.data.util.Constants.BASE_URL
import com.rysanek.fetchitemslist.data.util.Constants.ITEMS_DATABASE
import com.rysanek.fetchitemslist.domain.usecases.GetListItems
import com.rysanek.fetchitemslist.presentation.viewmodels.ListItemViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchItemsListModule {
    
    @Provides
    @Singleton
    fun provideItemsListDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        ItemsDatabase::class.java,
        ITEMS_DATABASE
    )
    .build()
    
    @Provides
    @Singleton
    fun provideFetchItemsListAPI(): ListItemAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .build()
        )
        .build()
        .create(ListItemAPI::class.java)
    
    @Singleton
    @Provides
    fun provideFetchItemsListDAO(
        db: ItemsDatabase
    ): ItemsDAO = db.itemsDao
    
    @Singleton
    @Provides
    fun provideContext(
        @ApplicationContext context: Context
    ) = context
    
    @Singleton
    @Provides
    fun provideListViewModel(getListItems: GetListItems) = ListItemViewModel(getListItems)
    
}