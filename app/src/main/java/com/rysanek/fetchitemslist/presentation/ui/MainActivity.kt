package com.rysanek.fetchitemslist.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rysanek.fetchitemslist.databinding.ActivityMainBinding
import com.rysanek.fetchitemslist.domain.workers.initializeFetchWork
import com.rysanek.fetchitemslist.presentation.utils.appInitialization
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
    
        initializeFetchWork()
        
        appInitialization()
    
        super.onCreate(savedInstanceState)
        
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    
    }
}