package com.zhu.nav

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.daomengkj.App
import com.zhu.daomengkj.App.Companion.toast
import com.zhu.daomengkj.Global
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.detail_dialog.view.*


class Nav : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        if (Global.typeface) TypefaceHelper.typeface(this)//应用字体
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
        this.window.statusBarColor = Color.TRANSPARENT

        toast.observe(this, Observer {

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        App.Dialog.observe(this, { actInfo ->
            val inflate = layoutInflater.inflate(R.layout.detail_dialog, null)
            val actDetailView = inflate.act_detail_info_dialog

            val metrics = resources.displayMetrics
            val width: Int = metrics.widthPixels //获取到的是px，像素，绝对像素，需要转化为dpi
            val height: Int = metrics.heightPixels
            val scale = resources.displayMetrics.density
            actInfo.data.activityImgSet?.let {
                if (it.isNotEmpty()) {
                    Log.d("Glide activityImgSet ", it[0])
                    Glide.with(this).load(it[0]).diskCacheStrategy(
                        DiskCacheStrategy.AUTOMATIC
                    ).into(inflate.activityImgSet)//图片}

                }
            }

            val array = actInfo.getArray()
            actDetailView.children.forEachIndexed { index, view ->
                if (array.size > index) {
                    (view as TextView).text = array[index].first + ":" + array[index].second
                }
            }

            val dialog = AlertDialog.Builder(this).create()
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
            dialog.setView(inflate)


//                .setTitle(actInfo.code) //标题
//                .setMessage(actInfo.data.toString()) //内容
//            dialog  .setIcon(R.mipmap.ic_launcher) //图标
            dialog.show()

            val params = dialog.window?.attributes
            params?.let {

                params.width =   (width * 0.85).toInt()
                params.height =( height * 0.87).toInt()
                Log.d("设置with height params is not null ", "width:${ pxToDip(scale, (width * 0.5).toInt())}  +  ${pxToDip(scale, (height * 0.7).toInt())}")
                dialog.window!!.attributes = params
            }
        })


    }

    private fun pxToDip(scale: Float, px: Int): Int {

        return (px / scale + 0.5f).toInt()

    }

    fun dipToPx(scale: Float, dpi: Int): Int {

        return (dpi * scale + 0.5f).toInt()

    }
}