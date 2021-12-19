package com.rysanek.fetchitemslist.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rysanek.fetchitemslist.presentation.viewmodels.ListItemViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class FetchWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val viewModel: ListItemViewModel
): CoroutineWorker(context, workerParameters)  {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        try {
            viewModel.fetchData()
            Result.success()
        }catch (e: Exception){
            Log.e("FetchWorker", "Error: ${e.message}")
            Result.retry()
        }
    }
}