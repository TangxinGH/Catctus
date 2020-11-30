package com.zhu.nav

import act_info
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.daomengkj.App
import com.zhu.daomengkj.App.Companion.toast
import com.zhu.daomengkj.Global
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.detail_dialog.*


class Nav : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        if (Global.typeface  ) TypefaceHelper.typeface(this)//应用字体
        val navView: BottomNavigationView = nav_view
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
        this.window.statusBarColor= Color.TRANSPARENT

        toast.observe(this, Observer {

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        App.Dialog.observe(this, { actInfo ->

            val actDetailView = act_detail_info_dialog

            val array = actInfo.getArray()
            actDetailView.children.forEachIndexed { index, view ->
                    if (array.size>index) {
                        (view as TextView ).text=array[index].first+":"+array[index].second
                    }
            }

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
                .setView(R.layout.detail_dialog)
                .setTitle(actInfo.code) //标题
                .setMessage(actInfo.data.toString()) //内容
                .setIcon(R.mipmap.ic_launcher) //图标
                .create()
                .show()


        })



    }
}