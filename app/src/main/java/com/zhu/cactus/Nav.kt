package com.zhu.cactus

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.daomengkj.App.Companion.toast

import kotlinx.android.synthetic.main.activity_nav.*
class Nav : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        if (App.typeface != null) TypefaceHelper.typeface(this)//应用字体
        val navView: BottomNavigationView = nav_view

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        Change theme to "Theme.AppCompat.Light.NoActionBar"
////        Remove codes below
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration) //我们没有这个东西
        navView.setupWithNavController(navController)


        toast.observe(this, Observer {

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        com.zhu.daomengkj.App.Dialog.observe(this, {
            AlertDialog.Builder(this)
                /* .apply {
                      setPositiveButton(R.string.ok,
                          DialogInterface.OnClickListener { dialog, id ->
                              // User clicked OK button
                          })
                      setNegativeButton(R.string.cancel,
                          DialogInterface.OnClickListener { dialog, id ->
                              // User cancelled the dialog
                          })
                  }*/
                .setTitle(it[0]) //标题
                .setMessage(it[1]) //内容
                .setIcon(R.mipmap.ic_launcher) //图标
                .create()
                .show()


        })
//        TODO("重构moudle nav ")

    }
}