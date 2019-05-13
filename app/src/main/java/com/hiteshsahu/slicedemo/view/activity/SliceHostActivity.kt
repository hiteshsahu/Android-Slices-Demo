package com.hiteshsahu.slicedemo.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hiteshsahu.slicedemo.view.adapter.ViewPagerFragmentAdapter
import com.hiteshsahu.slicedemo.view.fragment.SliceHostFragment
import kotlinx.android.synthetic.main.slice_host_activity.*

/**
 *
 * Host Slices in View Pager 2
 */
class SliceHostActivity : AppCompatActivity() {

    var myAdapter: ViewPagerFragmentAdapter? = null

    var urlList = listOf(
            "slice-content://com.hiteshsahu.slicedemo/",
            "slice-content://com.hiteshsahu.slicedemo/about",
            "slice-content://com.hiteshsahu.slicedemo/contact",
            "slice-content://com.hiteshsahu.slicedemo/302",
            "slice-content://com.hiteshsahu.slicedemo.advance/",
            "slice-content://com.hiteshsahu.slicedemo.advance/header",
            "slice-content://com.hiteshsahu.slicedemo.advance/toggle",
            "slice-content://com.hiteshsahu.slicedemo.advance/slider",
            "slice-content://com.hiteshsahu.slicedemo.advance/grid",
            "slice-content://com.hiteshsahu.slicedemo.advance/list",
            "slice-content://com.hiteshsahu.slicedemo.advance/combine")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.hiteshsahu.slicedemo.R.layout.slice_host_activity)


        myAdapter = ViewPagerFragmentAdapter(supportFragmentManager)

        // add Fragments in your ViewPagerFragmentAdapter class
        with(myAdapter) {

            for (url in urlList) {
                this!!.addFragment(SliceHostFragment.newInstance(url))
            }
        }
        // set Orientation in your ViewPager2
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = myAdapter

    }

    companion object {
        const val TAG = "SliceViewer"
    }

}
