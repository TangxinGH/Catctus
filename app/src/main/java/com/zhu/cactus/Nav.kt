package com.zhu.cactus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhu.daomengkj.App
import com.zhu.daomengkj.Py_invoke_Java
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_drawer.*


class Nav : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
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


    }
}