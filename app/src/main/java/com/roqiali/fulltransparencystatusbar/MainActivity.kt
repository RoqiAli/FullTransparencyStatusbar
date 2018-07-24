package com.roqiali.fulltransparencystatusbar

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifDrawable
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //clear up title
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
        }

        // show/hide title when scroll up/scroll down
        showTitle()

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
            initStatusBar(toolbar)

        //set gif animation
        try {
            gifImage.setImageDrawable(GifDrawable(resources, R.raw.dribbble))
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun showTitle() {
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = "Finn"
                    fab.hide()
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = null
                    fab.show()
                    isShow = false
                }
            }
        })
    }

    private fun initStatusBar(toolbar: View) {
        val contentParent = findViewById<ViewGroup>(android.R.id.content)
        val content = contentParent.getChildAt(0)
        setFitsSystemWindows(content)
        clipToStatusBar(toolbar)
    }

    private fun setFitsSystemWindows(view: View?) {
        if (view == null) return
        view.fitsSystemWindows = false
        if (view is ViewGroup) {
            val viewGroup = view as ViewGroup?
            var i = 0
            val n = viewGroup!!.childCount
            while (i < n) {
                viewGroup.getChildAt(i).fitsSystemWindows = false
                i++
            }
        }
    }

    private fun clipToStatusBar(view: View) {
        val statusBarHeight = getStatusBarHeight()
        view.layoutParams.height += statusBarHeight
        view.setPadding(0, statusBarHeight, 0, 0)
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
