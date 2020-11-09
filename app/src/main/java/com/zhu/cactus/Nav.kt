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
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_drawer.*


class Nav : AppCompatActivity() {

    @SuppressLint("ShowToast")
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

        daomeng_login.setOnClickListener {
            if (daomeng_login.text != null && daomeng_pass.text != null) {
                val sharedPreference = getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("username", daomeng_login.text.toString())
                editor.putString("password", daomeng_pass.text.toString())
                editor.apply()
                Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT)
            } else Toast.makeText(this, "输入为空:`${daomeng_login}+${daomeng_pass}`", Toast.LENGTH_SHORT)

        }
    }
}