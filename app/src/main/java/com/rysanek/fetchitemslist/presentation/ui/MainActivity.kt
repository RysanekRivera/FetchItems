package com.rysanek.fetchitemslist.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import androidx.work.WorkRequest.MIN_BACKOFF_MILLIS
import com.rysanek.fetchitemslist.data.util.Constants.CACHE_INTERVAL
import com.rysanek.fetchitemslist.data.util.Constants.WORK_NAME
import com.rysanek.fetchitemslist.databinding.ActivityMainBinding
import com.rysanek.fetchitemslist.domain.workers.FetchWorker
import com.rysanek.fetchitemslist.presentation.utils.appInitialization
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        
        initializeFetchWork()
        
        super.onCreate(savedInstanceState)
        
        _binding = ActivityMainBinding.inflate(layoutInflater)
        
        appInitialization()
        
        setContentView(binding.root)
    }
    
    /**
     * Starts the work of the [FetchWorker]. In this case it will start downloading
     * from the server and then it will check periodically after every [CACHE_INTERVAL]
     * if the data has changed from the server, if so it will perform the work once again.
     */
    private fun initializeFetchWork(){
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest = PeriodicWorkRequestBuilder<FetchWorker>(Duration.ofMillis(CACHE_INTERVAL))
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, Duration.ofMillis(MIN_BACKOFF_MILLIS))
            .build()
        
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}