package com.rysanek.fetchitemslist.presentation.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rysanek.fetchitemslist.R

/**Makes the application fullscreen, handles displays with cutouts and hides the status bar.**/
fun Window.fullScreenMode() {
    
    WindowCompat.setDecorFitsSystemWindows(this, true)

    // handling devices with cutouts
    attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    
    WindowInsetsControllerCompat(this, this.decorView).apply {
        
        // hide the status bar and make transient by swipe
        hide(WindowInsetsCompat.Type.statusBars())
        
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/**Makes the application fullscreen, sets the the theme and background for the activity.**/
fun Activity.appInitialization(){
    window.fullScreenMode()
    
    setTheme(R.style.Theme_FetchItemsList)
    
    window.setBackgroundDrawableResource(R.drawable.fetch_items_bg)
}

/**Checks the device has internet connection.
 * @return [Boolean] - Whether or not there is an active connection.**/
fun Context.hasInternetConnection(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val network = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    
    return when {
        network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

/**Shows a [Snackbar] from a fragment
 * @param message [String]: message to be displayed
 * @param length [Int]: the duration of the pop up. Short by default.**/
fun Fragment.showSnackBar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this.requireView(), message, length)
        .setAction("Ok") {}
        .show()
}

/**Hides a [View]**/
fun View.gone() {
    this.visibility = View.GONE
}

/**Shows a [View]**/
fun View.show() {
    this.visibility = View.VISIBLE
}