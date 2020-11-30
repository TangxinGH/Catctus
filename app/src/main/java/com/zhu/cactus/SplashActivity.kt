package com.zhu.cactus

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.MediaStoreSignature
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.splash_layout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        /*  view.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
              override fun onPreDraw(): Boolean {
                  view.viewTreeObserver.removeOnPreDrawListener(this)
                  // set the margin to the image
                  val imageView = view.findViewById<ImageView>(R.id.splash_image)
                  val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams
                  val statusBarHeight = resources.displayMetrics.heightPixels - view.measuredHeight
                  layoutParams.setMargins(0, statusBarHeight, 0, 0)
                  imageView.layoutParams = layoutParams
                  return true
              }
          })*/
//
        /* Handler(Looper.getMainLooper()).postDelayed({
             startActivity(Intent(this, MainActivity::class.java))
             finish()
         }, 2000)*/

        /*缓存 */
//        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).signature(
//            MediaStoreSignature("jpg", SimpleDateFormat("YYYYMMdd", Locale.CHINA).format(Date()).toLong(), 0)//一天更新一次签名
//        ).transform(RoundedCorners(20))

Glide.with(this).load("https://static01.imgkr.com/temp/d86e4b6de6804765af7d8b9981b5aa4d.png").diskCacheStrategy(
    DiskCacheStrategy.AUTOMATIC).into(splash_screen)
        permission()
    }

    private fun permission() {
        val permissionList = ArrayList<String>()
        permissionList.addAll(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE
            )
        )

        if (Build.VERSION.SDK_INT >= 29) {
            permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        PermissionX.init(this)
            .permissions(permissionList).onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "即将重新申请的权限是程序必须依赖的权限", "我已明白", "取消")

            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白", "取消")
            }


            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {

                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 250)
            }
    }


}