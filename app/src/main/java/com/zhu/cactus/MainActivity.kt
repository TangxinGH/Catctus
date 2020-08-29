package com.zhu.cactus
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    companion object var CHANNEL_ID = "12"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, Intent().apply {
                setClass(this@MainActivity, MainActivity::class.java)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }, PendingIntent.FLAG_UPDATE_CURRENT)

//        请注意，NotificationCompat.Builder 构造函数要求您提供渠道 ID。这是兼容 Android 8.0（API 级别 26）及更高版本所必需的，但会被较旧版本忽略。
        var builder = NotificationCompat.Builder(this,CHANNEL_ID )
            .setSmallIcon(R.drawable.icon_cactus_small)
            // Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            // Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.icon_cactus_small, "Previous", pendingIntent) // #0
            .addAction(R.drawable.icon_cactus_small, "Pause", pendingIntent) // #1
//            .addAction(R.drawable.ic_next, "Next", nextPendingIntent) // #2
            // Apply the media style template
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1 /* #1: pause button \*/)
//                .setMediaSession(mediaSession.getSessionToken())
            )
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.raw.d))

        createNotificationChannel()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(45, builder.build())
        }

//        //储存当前用户
//        var sharedPref = getSharedPreferences(
//            getString(R.string.preference_user_key), Context.MODE_PRIVATE
//        )
//        val userID: String? = sharedPref.getString("user", "")
//        if (sharedPref == null || userID.equals("") || userID == null) {
//            sharedPref = getSharedPreferences(
//                getString(R.string.preference_user_key),
//                Context.MODE_PRIVATE
//            )
//            with(sharedPref.edit()) {
//                putString("user", text.toString())
//                commit()
//            }
//        }

        App.log_Print.observe(this, Observer<String> {
          textView.text = it
//            https://juejin.im/entry/6844903497033318408
//            loop  用findviewid 有loop 问题
        })
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



    private fun getRemoteViews(): RemoteViews? {
        val remoteViews =
            RemoteViews(this.packageName, R.layout.notification_mobile_play)
//        val bitmap=BitmapFactory.decodeFile()
        val bitmap = BitmapFactory.decodeResource(this.resources, R.raw.d)
    remoteViews.setImageViewBitmap(
        R.mipmap.ic_launcher,
        BitmapFactory.decodeResource(this.resources, R.raw.d));
        return remoteViews
    }
}
