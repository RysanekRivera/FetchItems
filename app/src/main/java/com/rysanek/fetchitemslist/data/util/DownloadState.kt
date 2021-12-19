package com.rysanek.fetchitemslist.data.util

sealed interface DownloadState {
    
    object Idle : DownloadState
    object Checking: DownloadState
    object Downloading: DownloadState
    object Finished: DownloadState
    
    object Error: DownloadState {
        var message: String? = null
        
        fun message(message: String?): DownloadState {
            this.message = message
            return this
        }
    }
}