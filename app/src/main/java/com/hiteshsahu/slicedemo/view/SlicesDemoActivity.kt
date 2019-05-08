package com.hiteshsahu.slicedemo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.hiteshsahu.slicedemo.ActionsEnum
import com.hiteshsahu.slicedemo.ScreenNameEnum
import com.hiteshsahu.slicedemo.provider.BasicSliceProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class SlicesDemoActivity : EasyPermissionActivity() {

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

        showInGit.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    override fun setUpViewWithoutPermissions() {
        // No permission no party exit
        finish()
    }

    override fun getActivityLayout(): Int {
        return com.hiteshsahu.slicedemo.R.layout.activity_main
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
