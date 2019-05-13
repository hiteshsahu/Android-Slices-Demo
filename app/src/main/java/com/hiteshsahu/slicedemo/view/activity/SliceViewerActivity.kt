package com.hiteshsahu.slicedemo.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hiteshsahu.slicedemo.R
import com.hiteshsahu.slicedemo.view.fragment.SliceViewerFragment

/**
 * Show A single SLice using CMD Like:
 *
 * adb shell am start -a android.intent.action.VIEW -d slice-content://com.hiteshsahu.slicedemo.advance/combine
 */
class SliceViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slice_viewer_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SliceViewerFragment.newInstance())
                    .commitNow()
        }
    }

    companion object {
        const val TAG = "SliceViewer"
    }

}
