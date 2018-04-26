package com.bestsellers.util.ext

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bestsellers.R
import com.bestsellers.util.BounceInterpolator
import com.bestsellers.util.SHARED_ITEM_ID


/**
 * Created by Rafaela Araujo
 * on 03/11/2017.
 */


/**
 * show toast message
 * @param text message that will be displayed
 */
fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Open new activity
 * @param sharedView item shared into activitys
 * @param init activity intent params
 */
inline fun <reified T : Activity> Activity.launchActivity(sharedView: View? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    if (sharedView != null) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, SHARED_ITEM_ID)
        startActivity(intent, options.toBundle())
        overridePendingTransition(0, 0)
    } else {
        startActivity(intent)
    }
}


/**
 * method to create a new intent to start activitys
 * @param context context will start a new activity
 */
inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

fun View.startBounceAnimation() {
    val myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce)
    val interpolator = BounceInterpolator(0.2, 20.0)
    myAnim.interpolator = interpolator

    this.startAnimation(myAnim)
}

/**
 * Retrieve property from intent
 */
fun <T : Any> Activity.argument(key: String) = lazy { intent.extras?.get(key) as T }


/**
 * open url out of app in a browser
 */
fun Activity.openUrlInBrowser(url: String?) {
    if (url != null) startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}



