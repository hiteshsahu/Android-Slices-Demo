package com.hiteshsahu.slicedemo.domain.provider

import android.app.PendingIntent
import android.content.*
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.GridRowBuilder
import androidx.slice.builders.GridRowBuilder.CellBuilder
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.ListBuilder.HeaderBuilder
import androidx.slice.builders.ListBuilder.InputRangeBuilder
import androidx.slice.builders.SliceAction
import com.hiteshsahu.slicedemo.domain.provider.BasicSliceProvider.Factory.PAGE_ACTION
import com.hiteshsahu.slicedemo.model.ActionsEnum
import com.hiteshsahu.slicedemo.model.ScreenNameEnum
import com.hiteshsahu.slicedemo.view.activity.SlicesDemoActivity


// List to keep scan result
private var wifiList: MutableList<ScanResult> = ArrayList()

/**
 *
 * Advance Example to create custom Slices based on uri
 */
class AdvanceSliceProvider : SliceProvider() {

    companion object Factory {
        const val PAGE_KEY = "SHOW_PAGE"
    }

    // URI paths after content://com.hiteshsahu.slicedemo/advance
    private val BASE_URL = "/"          //path for showing slices with multiple actions
    private val WITH_TOGGLE = "/toggle" //path for showing slices with SWITCH toggle
    private val WITH_SLIDER = "/slider" //path for showing slices with range SLIDER and progress bar
    private val WITH_GRID = "/grid"     //path for showing slices with image GRID
    private val WITH_LIST = "/list"     //path for showing slices with LIST of connected  Wifi
    private val WITH_COMBINE = "/combine"    //path for showing media rich slices

    // broadcast for wifi scanning
    private lateinit var wifiReceiver: WifiReceiver


    override fun onCreateSliceProvider(): Boolean {


        context?.let {
            // Register a listener to start listening to WIFI
            wifiReceiver = WifiReceiver()
            it.registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            (it.getSystemService(Context.WIFI_SERVICE) as WifiManager).startScan()
        }

        return true
    }

    /**
     * Converts URL to content URI (i.e. content://com.hiteshsahu.slicedemo/advance...)
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
                val path = dataPath.replace(BASE_URL, "")
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
     *   content://com.hiteshsahu.slicedemo.advance/ : BASE_URL
     *   content://com.hiteshsahu.slicedemo/header : WITH_HEADER
     *   content://com.hiteshsahu.slicedemo/toggle : WITH_TOGGLE
     *   content://com.hiteshsahu.slicedemo/slider : WITH_SLIDER
     *   content://com.hiteshsahu.slicedemo/grid : WITH_GRID
     *   content://com.hiteshsahu.slicedemo/list : WITH_LIST
     *   content://com.hiteshsahu.slicedemo/combine : WITH_COMBINE
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
            BASE_URL -> { //Advance HOME with multiple actions

                // DEMONSTRATION OF HOW TO CREATE SLICES WITH MULTIPLE ACTIONS

                val noteAction = SliceAction.create(createPendingIntentForAction(ActionsEnum.TEXT_NOTE.name),
                        IconCompat.createWithResource(getContext()!!, com.hiteshsahu.slicedemo.R.drawable.vector_pencil),
                        ListBuilder.ICON_IMAGE, "Take note")

                val voiceNoteAction = SliceAction.create(createPendingIntentForAction(ActionsEnum.VOICE_NOTE.name),
                        IconCompat.createWithResource(getContext()!!, com.hiteshsahu.slicedemo.R.drawable.vector_mic),
                        ListBuilder.ICON_IMAGE,
                        "Take voice note")

                val cameraNoteAction = SliceAction.create(createPendingIntentForAction(ActionsEnum.IMAGE_NOTE.name),
                        IconCompat.createWithResource(getContext()!!, com.hiteshsahu.slicedemo.R.drawable.vector_cam),
                        ListBuilder.ICON_IMAGE,
                        "Create photo note")


                // Construct the list.
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .setAccentColor(-0xb4c00) // Specify color for tinting icons
                        .setHeader(HeaderBuilder() // Construct the header.
                                .setTitle("Slice with header")
                                .setSubtitle("And 3 Actions ")
                        )
                        .addRow(ListBuilder.RowBuilder()
                                .setTitle("Click on Actions to Launch app with custom Actions")
                                .setPrimaryAction(activityActionForPath)
                        )
                        // Add the actions to the ListBuilder.
                        .addAction(noteAction)
                        .addAction(voiceNoteAction)
                        .addAction(cameraNoteAction)
                return listBuilder.build()


            }

            WITH_SLIDER -> {


                // Example of using RANGE SLIDER to change Media Volume
                // Example of showing curent Media Volume as Progress Bar

                val pendingIntent = Intent(android.provider.Settings.ACTION_SOUND_SETTINGS)
                val volumeSettingsPendingIntent = PendingIntent.getActivity(
                        context,
                        System.currentTimeMillis().toInt(),
                        pendingIntent,
                        PendingIntent.FLAG_ONE_SHOT)

                // set real values
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                val progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

                // add volume change listener
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(ListBuilder.RowBuilder() // Every slice needs a row.
                                .setTitle("Use Slider to Change Volume")
                                .setPrimaryAction(activityActionForPath)
                        )
                        .addRange(ListBuilder.RangeBuilder() // Add Range
                                .setTitle("Media Volume as Progress")
                                .setMax(max)
                                .setValue(progress)
                        )
                        .addInputRange(InputRangeBuilder() // Add Slider
                                .setTitle("Media Volume level as range")
                                .setInputAction(volumeSettingsPendingIntent)
                                .setMax(max)
                                .setValue(progress)

                        )
                return listBuilder.build()

            }


            WITH_TOGGLE -> {

                // Example of using SWITCH TOGGLE  to turn wifi On/Off

                val pendingIntent = Intent(Intent.ACTION_MAIN)
                pendingIntent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings")

                val wifiSettingsPendingIntent = PendingIntent.getActivity(
                        context,
                        System.currentTimeMillis().toInt(),
                        pendingIntent,
                        PendingIntent.FLAG_ONE_SHOT)


                // Toggle action - toggle wifi.
                val toggleAction = SliceAction.createToggle(wifiSettingsPendingIntent,
                        "Toggle Wi-Fi", haveNetworkConnection() /* isChecked */)

                // Create the parent builder.
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        // Specify color for tinting icons / controls.
                        .setAccentColor(-0xbd7a0c)
                        // Create and add a row.
                        .addRow(ListBuilder.RowBuilder()
                                .setTitle(" Toggle WiFi Setting")
                                .setPrimaryAction(activityActionForPath)
                                .addEndItem(toggleAction))
                // Build the slice.
                return listBuilder.build()

            }

            WITH_LIST -> {

                // Example of How to show  list of available  WIFIs with see more option

                val pendingIntent = Intent(Intent.ACTION_MAIN)
                pendingIntent.setClassName("com.android.settings",
                        "com.android.settings.wifi.WifiSettings")

                val wifiSettingsPendingIntent = PendingIntent.getActivity(
                        context,
                        System.currentTimeMillis().toInt(),
                        pendingIntent,
                        PendingIntent.FLAG_ONE_SHOT)


                // Toggle action - toggle wifi.
                val toggleAction = SliceAction.createToggle(wifiSettingsPendingIntent,
                        "Toggle Wi-Fi", haveNetworkConnection() /* isChecked */)


                //Create List
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .setAccentColor(0xff0F9D58.toInt())
                        .addRow(ListBuilder.RowBuilder()
                                .setTitle("Wi-Fi")
                                .setSubtitle("All Scanned  Wifi list")
                                .setPrimaryAction(activityActionForPath)
                                .addEndItem(toggleAction))

                // Add all Wifi Scan result into the List
                for (wifi in wifiList) {
                    listBuilder.addRow(
                            ListBuilder.RowBuilder()
                                    .setTitle(wifi.SSID)
                                    .setSubtitle("Strength" + wifi.level)
                                    .setPrimaryAction(activityActionForPath))
                }


                // Add See more
                listBuilder.setSeeMoreRow(ListBuilder.RowBuilder()
                        .setTitle("See all available networks")
                        .addEndItem(IconCompat
                                .createWithResource(getContext(), com.hiteshsahu.slicedemo.R.drawable
                                        .vector_more),
                                ListBuilder.ICON_IMAGE)
                        .setPrimaryAction(SliceAction.create(wifiSettingsPendingIntent,
                                IconCompat.createWithResource(getContext(), com.hiteshsahu.slicedemo.R.drawable.vector_wifi),
                                ListBuilder.ICON_IMAGE,
                                "Wi-Fi Networks")
                        )
                )

                return listBuilder.build()

            }

            WITH_GRID -> {

                // Example of how to show Grid of Images

                val url = "http://www.hiteshsahu.com"
                val launchChromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                launchChromeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launchChromeIntent.setPackage("com.android.chrome")

                val pendingIntent = PendingIntent.getActivity(
                        context,
                        System.currentTimeMillis().toInt(),
                        launchChromeIntent,
                        PendingIntent.FLAG_ONE_SHOT)


                // Create the parent builder.
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .setHeader(
                                // Create the header.
                                HeaderBuilder()
                                        .setTitle("Using Grid to display contents")
                                        .setPrimaryAction(SliceAction
                                                .create(pendingIntent,
                                                        IconCompat.createWithResource(getContext()!!,
                                                                com.hiteshsahu.slicedemo.R.drawable.vector_cam),
                                                        ListBuilder.ICON_IMAGE,
                                                        "Famous restaurants")))
                        // Add a grid row to the list.
                        .addGridRow(GridRowBuilder()
                                // Add cells to the grid row.
                                .addCell(CellBuilder()
                                        .addImage(IconCompat.createWithResource(context,
                                                com.hiteshsahu.slicedemo.R.drawable.small), ListBuilder.LARGE_IMAGE)
                                        .addTitleText("Top Restaurant")
                                        .addText("0.3 mil")
                                        .setContentIntent(pendingIntent)
                                ).addCell(CellBuilder()
                                        .addImage(IconCompat.createWithResource(context,
                                                com.hiteshsahu.slicedemo.R.drawable.big), ListBuilder.LARGE_IMAGE)
                                        .addTitleText("Fast and Casual")
                                        .addText("0.5 mil")
                                        .setContentIntent(pendingIntent)
                                )
                                .addCell(CellBuilder()
                                        .addImage(IconCompat.createWithResource(context,
                                                com.hiteshsahu.slicedemo.R.drawable.large), ListBuilder.LARGE_IMAGE)
                                        .addTitleText("Casual Diner")
                                        .addText("0.9 mi")
                                        .setContentIntent(pendingIntent))
                                .addCell(CellBuilder()
                                        .addImage(IconCompat.createWithResource(context,
                                                com.hiteshsahu.slicedemo.R.drawable.icon), ListBuilder.LARGE_IMAGE)
                                        .addTitleText("Ramen Spot")
                                        .addText("1.2 mi")
                                        .setContentIntent(pendingIntent))
                                // Every slice needs a primary action.
                                .setPrimaryAction(activityActionForPath))

                return listBuilder.build()

            }


            WITH_COMBINE -> {

                // Example of how to show Grid of Images

                val url = "http://www.hiteshsahu.com"
                val launchChromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                launchChromeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launchChromeIntent.setPackage("com.android.chrome")

                val pendingIntent = PendingIntent.getActivity(
                        context,
                        System.currentTimeMillis().toInt(),
                        launchChromeIntent,
                        PendingIntent.FLAG_ONE_SHOT)


                // Create the parent builder.
                val listBuilder = ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .setHeader(
                                // Create the header.
                                HeaderBuilder()
                                        .setTitle("Using Combination")
                                        .setPrimaryAction(SliceAction
                                                .create(pendingIntent,
                                                        IconCompat.createWithResource(getContext()!!,
                                                                com.hiteshsahu.slicedemo.R.drawable.vector_cam),
                                                        ListBuilder.ICON_IMAGE,
                                                        "Famous restaurants")))
                        // Add a grid row to the list.
                        .addGridRow(GridRowBuilder()
                                // Add cells to the grid row.
                                .addCell(CellBuilder()
                                        .addImage(IconCompat.createWithResource(context,
                                                com.hiteshsahu.slicedemo.R.drawable.hotel), ListBuilder.LARGE_IMAGE)
                                )).addGridRow(GridRowBuilder()

                                .addCell(CellBuilder()

                                        .addTitleText("Check In")
                                        .addText("12 PM, Jun 12")
                                        .setContentIntent(pendingIntent)
                                )
                                .addCell(CellBuilder()

                                        .addTitleText("Check Out")
                                        .addText("11 AM, Jun 19")
                                        .setContentIntent(pendingIntent))
                                // Every slice needs a primary action.
                                .setPrimaryAction(activityActionForPath))

                return listBuilder.build()

            }
            else -> {

                // Example of handling 404 error
                return ListBuilder(context, sliceUri, ListBuilder.INFINITY)
                        .addRow(
                                ListBuilder.RowBuilder()
                                        .setTitle("Error 404 You are long way from Advance home. \uD83D\uDC31")
                                        .setPrimaryAction(activityActionForPath))
                        .build()
            }
        }
    }

    /**
     * Create SliceAction based on path of slice
     */
    private fun crateActionForPath(slicePath: String?): SliceAction? {

        val pendingIntent = Intent(context, SlicesDemoActivity::class.java)

               // add payload based on path
                pendingIntent.putExtra(PAGE_KEY, ScreenNameEnum.HOME.name)

                // WITH ICON_IMAGE size
                return SliceAction.create(
                        PendingIntent.getActivity(
                                context,
                                System.currentTimeMillis().toInt(),
                                pendingIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT),
                        IconCompat.createWithResource(context,
                                com.hiteshsahu.slicedemo.R.drawable.icon),
                        ListBuilder.ICON_IMAGE,
                        "Open App")

    }

    /**
     * Craete Pending Intent with custom data based on action
     */
    private fun createPendingIntentForAction(action: String?): PendingIntent {

        val pendingIntent = Intent(context, SlicesDemoActivity::class.java)
        pendingIntent.putExtra(PAGE_ACTION, action)

        return PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                pendingIntent,
                PendingIntent.FLAG_ONE_SHOT)
    }

    override fun onSlicePinned(sliceUri: Uri?) {
        // When data is received, call context.contentResolver.notifyChange(sliceUri, null) to
        // trigger BasicSliceProvider#onBindSlice(Uri) again.
    }


    override fun onSliceUnpinned(sliceUri: Uri?) {
        // Remove any observers if necessary to avoid memory leaks.
    }

    /**
     * Helper method to check If wifi availbale or not
     */
    private fun haveNetworkConnection(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals("WIFI", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedWifi = true
            if (ni.typeName.equals("MOBILE", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }


    /**
     * Local Broadcast receiver
     */
    internal class WifiReceiver : BroadcastReceiver() {
        private var WifiManager: WifiManager? = null

        // This method call when number of wifi connections changed
        override fun onReceive(context: Context, intent: Intent) {
            WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            wifiList = WifiManager!!.scanResults

        }
    }
}
