package com.zhu.nav

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
//import kotlinx.android.synthetic.main.activity_nav.*
import com.zhu.nav.databinding.ActivityNavBinding

class Nav : AppCompatActivity() {
    private lateinit var activityViewNav: ActivityNavBinding //Migrate from Kotlin synthetics to Jetpack view binding
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewNav = ActivityNavBinding.inflate(layoutInflater)//Migrate from Kotlin synthetics to Jetpack view binding
        val view = activityViewNav.root //Migrate from Kotlin synthetics to Jetpack view binding
        setContentView(view)//Migrate from Kotlin synthetics to Jetpack view binding

//        if (TypefaceHelper) TypefaceHelper.typeface(this)//应用字体
        val navView: BottomNavigationView =activityViewNav.navView
        val intent = intent //提取数据
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        Change theme to "Theme.AppCompat.Light.NoActionBar"
////        Remove codes below
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration) //我们没有这个东西
        navView.setupWithNavController(navController)

        /*沉浸式 */
        this.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.statusBarColor = Color.TRANSPARENT






    }


}