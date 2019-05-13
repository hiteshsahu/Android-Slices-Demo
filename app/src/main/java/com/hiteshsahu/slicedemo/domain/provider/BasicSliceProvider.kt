package com.hiteshsahu.slicedemo.domain.provider

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import com.hiteshsahu.slicedemo.R
import com.hiteshsahu.slicedemo.model.ScreenNameEnum
import com.hiteshsahu.slicedemo.view.activity.SlicesDemoActivity

/**
 *
 * Simple Example to create Slices based on uri
 */
class BasicSliceProvider : SliceProvider() {

    companion object Factory {
        const val PAGE_KEY = "SHOW_PAGE" // passing screen name
        const val PAGE_ACTION = "PERFORM_ACTION" // passing action name
    }

    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    /**
     * Converts URL to content URI (i.e. content://com.hiteshsahu.slicedemo...)
     */
    override fun onMapIntentToUri(intent: Intent?): Uri {

        var uriBuilder: Uri.Builder = Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT)

        if (intent == null) {
            return uriBuilder.build()
        } else {
            // get data from intent
            val data = intent.data

            // get path from intent
            val dataPath = data?.path

            if (data != null && dataPath != null) {
                //create path from data and path
                val path = dataPath.replace("/", "")
                // create uri from path
                uriBuilder = uriBuilder.path(path)
            }

            // Return URI
            val context = context
            if (context != null) {
                uriBuilder = uriBuilder.authority(context.packageName)
            }
            return uriBuilder.build()
        }


    }

    /**
     *  Show appropriate Slice with pending action based on URI path
     *
     *   content://com.hiteshsahu.slicedemo/ : home
     *   content://com.hiteshsahu.slicedemo/about : about
     *   content://com.hiteshsahu.slicedemo/contact : contact
     *   content://com.hiteshsahu.slicedemo/etc : 404
     */
    override fun onBindSlice(sliceUri: Uri): Slice? {
        // small-builders-ktx for a nicer interface in Kotlin.

        val context = context ?: return null

        // get path from Uri
        val sliceUriPath = sliceUri.path

        // Create custom actions based on URI path eg. showing contact or home
        val activityActionForPath = crateActionForPath(sliceUriPath) ?: return null

        // return a small based on path and action
        when (sliceUriPath) {
            "/" -> {
                // HOME URI
                return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(
                                ListBuilder.RowBuilder()
                                        .setTitle("Go to  Home \uD83C\uDFE0")
                                        .setPrimaryAction(activityActionForPath))
                        .build()
            }

            "/about" -> {

                // ABOUT URI
                return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(
                                ListBuilder.RowBuilder()
                                        .setTitle("Go to About  \uD83D\uDE00 ")
                                        .setPrimaryAction(activityActionForPath))
                        .build()
            }

            "/contact" -> {
                // CONTACT URI
                return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(
                                ListBuilder.RowBuilder()
                                        .setTitle("Go to Contact \uD83D\uDCDE")
                                        .setPrimaryAction(activityActionForPath))
                        .build()
            }
            else -> {
                // ANYTHING ELSE
                return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(
                                ListBuilder.RowBuilder()
                                        .setTitle("Error 404 You are long way from home. \uD83D\uDC31")
                                        .setPrimaryAction(activityActionForPath))
                        .build()
            }
        }

    }

    /**
     * Craete actions based on path
     */
    private fun crateActionForPath(slicePath: String?): SliceAction? {

        val pendingIntent = Intent(context, SlicesDemoActivity::class.java)

        // add payload based on path
        when (slicePath) {
            "/" -> { // HOME URI

                // set data in intent
                pendingIntent.putExtra(PAGE_KEY, ScreenNameEnum.HOME.name)

                // Create pending intent with ICON_IMAGE size
                return SliceAction.create(
                        PendingIntent.getActivity(
                                context,
                                System.currentTimeMillis().toInt(),
                                pendingIntent,
                                PendingIntent.FLAG_ONE_SHOT),
                        IconCompat.createWithResource(context,
                                R.drawable.icon),
                        ListBuilder.ICON_IMAGE,
                        "Open App")


            }
            "/about" -> { // ABOUT URI

                // set data in intent
                pendingIntent.putExtra(PAGE_KEY, ScreenNameEnum.ABOUT.name)

                // Create pending intent with SMALL_IMAGE size
                return SliceAction.create(
                        PendingIntent.getActivity(
                                context,
                                System.currentTimeMillis().toInt(),
                                pendingIntent,
                                PendingIntent.FLAG_ONE_SHOT),
                        IconCompat.createWithResource(context,
                                R.drawable.small),
                        ListBuilder.SMALL_IMAGE,
                        "Open App")

            }
            "/contact" -> {

                // set data in intent
                pendingIntent.putExtra(PAGE_KEY, ScreenNameEnum.CONTACT.name)

                // Create pending intent with LARGE image size
                return SliceAction.create(
                        PendingIntent.getActivity(
                                context,
                                System.currentTimeMillis().toInt(),
                                pendingIntent,
                                PendingIntent.FLAG_ONE_SHOT),
                        IconCompat.createWithResource(context,
                                R.drawable.large),
                        ListBuilder.LARGE_IMAGE,
                        "Open App")


            }
            else -> { // ANYTHING ELSE
                pendingIntent.putExtra(PAGE_KEY, ScreenNameEnum.OTHER.name)


                // Default with app icon
                return SliceAction.create(
                        PendingIntent.getActivity(
                                context,
                                System.currentTimeMillis().toInt(),
                                pendingIntent,
                                PendingIntent.FLAG_ONE_SHOT),
                        IconCompat.createWithResource(context,
                                R.mipmap.ic_launcher),
                        ListBuilder.LARGE_IMAGE,
                        "Open App")
            }
        }
    }

    override fun onSlicePinned(sliceUri: Uri?) {
        // When data is received, call context.contentResolver.notifyChange(sliceUri, null) to
        // trigger BasicSliceProvider#onBindSlice(Uri) again.
    }


    override fun onSliceUnpinned(sliceUri: Uri?) {
        // Remove any observers if necessary to avoid memory leaks.
    }
}
