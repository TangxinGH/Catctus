package com.zhu.cactus

import android.animation.ValueAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.method.MainListAdapter
import com.zhu.cactus.method.ToolbarBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*

var animationPlaybackSpeed: Double = 0.8  //动画播放速度

class MainActivity : AppCompatActivity() {
    companion object

     var CHANNEL_ID = "12"
    private lateinit var mainListAdapter: MainListAdapter

    /**
     * Used by FiltersLayout since we don't want to expose mainListAdapter (why?)
     * (Option: Combine everything into one activity if & when necessary)
     */
    var isAdapterFiltered: Boolean
        get() = mainListAdapter.isFiltered
        set(value) {
            mainListAdapter.isFiltered = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TypefaceHelper.typeface(this)//应用字体

        drawer_icon.setOnClickListener { drawer_layout.openDrawer(GravityCompat.START) } //左则菜单


        // Appbar behavior init 一种跟随着行为， 顶栏 随着 列表 下拉 隐藏
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolbarBehavior()

        // RecyclerView Init
        mainListAdapter = MainListAdapter(this)
        recycler_view.adapter = mainListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        useFiltersMotionLayout(true)
        save.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_user_key),
                    Context.MODE_PRIVATE
                )
                with(sharedPref.edit()) {
                    putString("sno",  editTextTextPersonName.text.toString())
                    putString("pass", editTextTextPassword.text.toString())
                    commit()
                }
            }

        showNotifcation()//通知

        App.log_Print.observe(this, Observer<String> {
            textView.text = it
//            https://juejin.im/entry/6844903497033318408
//            loop  用findviewid 有loop 问题
        })
    }
    /**
     * Callback for motionLayoutCheckbox
     * isChecked = true -> Use [FiltersMotionLayout]
     * isChecked = false -> Use [FiltersLayout]
     */
    private fun useFiltersMotionLayout(isChecked: Boolean) {
        filters_motion_layout.isVisible = isChecked
    }

    fun showNotifcation() {
        val pendingIntent =
            PendingIntent.getActivity(this, 0, Intent().apply {
                setClass(this@MainActivity, MainActivity::class.java)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }, PendingIntent.FLAG_UPDATE_CURRENT)

//        请注意，NotificationCompat.Builder 构造函数要求您提供渠道 ID。这是兼容 Android 8.0（API 级别 26）及更高版本所必需的，但会被较旧版本忽略。
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_cactus_small)
            // Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            // Add media control buttons that invoke intents in your media service
//            .addAction(R.drawable.icon_cactus_small, "Previous", pendingIntent) // #0
//            .addAction(R.drawable.icon_cactus_small, "Pause", pendingIntent) // #1
//            .addAction(R.drawable.ic_next, "Next", nextPendingIntent) // #2
            // Apply the media style template
            .setStyle( //见官网  https://developer.android.com/training/notify-user/expanded
                androidx.media.app.NotificationCompat.MediaStyle()
//                    .setShowActionsInCompactView(1 /* #1: pause button \*/)
//                .setMediaSession(mediaSession.getSessionToken())
            )
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.raw.d))
            .setContentIntent(pendingIntent)
        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(45, builder.build())
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
//        由于您必须先创建通知渠道，然后才能在 Android 8.0 及更高版本上发布任何通知，因此应在应用启动时立即执行这段代码。反复调用这段代码是安全的，因为创建现有通知渠道不会执行任何操作。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    /**
     * Called from FiltersLayout to get adapter scale down animator
     */
    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)
}
