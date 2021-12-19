package com.rysanek.fetchitemslist.presentation.utils

import com.rysanek.fetchitemslist.data.util.Constants.REMOTE_CONTENT_LENGTH
import okhttp3.OkHttpClient
import okhttp3.Request


/**
 * Gets the Content Length of the data from the server.
 * @param url [String] to make the request.
 */
fun fetchContentLength(
    url: String
): String? {
    return OkHttpClient().newCall(Request.Builder().url(url).head().build()).execute().headers[REMOTE_CONTENT_LENGTH]
}