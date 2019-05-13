package com.hiteshsahu.slicedemo.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.hiteshsahu.slicedemo.domain.provider.BasicSliceProvider
import com.hiteshsahu.slicedemo.model.ActionsEnum
import com.hiteshsahu.slicedemo.model.ScreenNameEnum
import com.hiteshsahu.slicedemo.view.adapter.ViewPagerFragmentAdapter
import com.hiteshsahu.slicedemo.view.fragment.SliceHostFragment
import kotlinx.android.synthetic.main.activity_demo.*
import kotlinx.android.synthetic.main.content_main.*


/**
 *
 * Demo activity to communicate with SLices suisng in tents
 */
class SlicesDemoActivity : EasyPermissionActivity() {


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
        doCircularReveal(appRoot)
    }

    override fun setUpView() {

        setSupportActionBar(toolbar)

        if (intent != null) {

            // Basic USAGE
            val pageValue: String? = intent.getStringExtra(BasicSliceProvider.PAGE_KEY)
            pageValue?.let {

                when (ScreenNameEnum.valueOf(it)) {
                    ScreenNameEnum.HOME -> { // HOME URI
                        pageName.text = "I am at HOME \uD83C\uDFE0"
                    }

                    ScreenNameEnum.ABOUT -> { // ABOUT URI
                        pageName.text = "I am  in ABOUT \uD83D\uDE00"
                    }

                    ScreenNameEnum.CONTACT -> { // ABOUT URI
                        pageName.text = "I am in CONATCT \uD83D\uDCDE"
                    }

                    else -> { // ANYTHING ELSE
                        pageName.text = "I am In OTHER"
                    }
                }
            }

            // ADVANCE USAGE
            val actionValue: String? = intent.getStringExtra(BasicSliceProvider.PAGE_ACTION)
            actionValue?.let {

                when (ActionsEnum.valueOf(it)) {
                    ActionsEnum.TEXT_NOTE -> {
                        // HOME URI
                        actionName.text = actionValue
                    }

                    ActionsEnum.VOICE_NOTE -> {
                        // ABOUT URI
                        actionName.text = actionValue
                    }

                    ActionsEnum.IMAGE_NOTE -> {
                        // ABOUT URI
                        actionName.text = actionValue
                    }
                    else -> {
                        // ANYTHING ELSE
                        //"
                    }
                }
            }
        }

        // Launch Slice Host Activity
        showInGit.setOnClickListener { view ->
            startActivity(Intent(this, SliceHostActivity::class.java))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        // Show All Slices

        myAdapter = ViewPagerFragmentAdapter(supportFragmentManager)


        // Disable clip to padding
        viewPagerhost.clipToPadding = false;
        // set padding manually, the more you set the padding the more you see of prev & next page
        viewPagerhost.setPadding(40, 0, 40, 0);


        // add Fragments in your ViewPagerFragmentAdapter class
        with(myAdapter) {

            for (url in urlList) {
                this!!.addFragment(SliceHostFragment.newInstance(url))
            }
        }
        // set Orientation in your ViewPager2
        viewPagerhost.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPagerhost.adapter = myAdapter

    }


    override fun setUpViewWithoutPermissions() {
        // No permission no party exit
        finish()
    }

    override fun getActivityLayout(): Int {
        return com.hiteshsahu.slicedemo.R.layout.activity_demo
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.hiteshsahu.slicedemo.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.hiteshsahu.slicedemo.R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        animateExitScreen(appRoot)
    }
}
