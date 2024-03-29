package com.zhu.cactus

import android.Manifest
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbsoft.typefacehelper.TypefaceHelper
import com.permissionx.guolindev.PermissionX
import com.zhu.cactus.ONE.getONEFor
import com.zhu.cactus.ONE.gethitokoto
import com.zhu.cactus.POJO.JsonHitokoto
import com.zhu.cactus.POJO.Newslist
import com.zhu.cactus.download.font.FontProgressBar
import com.zhu.cactus.filter.FiltersLayout
import com.zhu.cactus.filter.FiltersPagerAdapter
import com.zhu.cactus.method.MainListAdapter
import com.zhu.cactus.method.ToolbarBehavior
import com.zhu.cactus.services.OnlyAudioRecorder
import com.zhu.cactus.utils.Util.startToAutoStartSetting
import com.zhu.nav.Nav
//import kotlinx.android.synthetic.main.activity_main.* //kotlin的废弃
//import kotlinx.android.synthetic.main.nav_drawer.* //kotlin的废弃
//视图绑定：
import com.zhu.cactus.databinding.ActivityMainBinding
import com.zhu.cactus.databinding.NavDrawerBinding
//数据绑定：
//import androidx.databinding.Observable

var animationPlaybackSpeed: Double = 0.8  //动画播放速度

class MainActivity : AppCompatActivity() {
    companion object
    private    lateinit var mainListAdapter: MainListAdapter
    private lateinit var activityMainBinding: ActivityMainBinding //Migrate from Kotlin synthetics to Jetpack view binding
    private lateinit var navMergeBinding: NavDrawerBinding  //merge_layout.xml layout

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

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)//Migrate from Kotlin synthetics to Jetpack view binding
        val view = activityMainBinding.root //Migrate from Kotlin synthetics to Jetpack view binding
        //we need to bind the root layout with our binder for external layout
         navMergeBinding = NavDrawerBinding.bind(view)
        setContentView(view)//Migrate from Kotlin synthetics to Jetpack view binding

        if (App.typeface!=null)TypefaceHelper.typeface(this)//应用字体
//        supportActionBar?.hide()
        initData()
// splashactivity里使用了        permission()
    }


    /**
     * Callback for motionLayoutCheckbox
     * isChecked = true -> Use [FiltersMotionLayout]
     * isChecked = false -> Use [FiltersLayout]
     */
    private fun useFiltersMotionLayout(isChecked: Boolean) {
        activityMainBinding.filtersMotionLayout.isVisible = isChecked
    }

    private fun initData() {
        activityMainBinding.drawerIcon.setOnClickListener { activityMainBinding.drawerLayout.openDrawer(GravityCompat.START) } //左则菜单
        navMergeBinding.textButton.setOnClickListener { startToAutoStartSetting(this) }
        navMergeBinding.switchMaterial.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_TTF_Font_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("font", navMergeBinding.switchMaterial.isChecked.toString())
                commit()
            }

            /*传递数据*/
            val bundle = Bundle()
            bundle.putInt("key2",18)
            val intent = Intent(this, Nav::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }// 服务？
        navMergeBinding.GpsID.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_GPS_user_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("user", navMergeBinding.editTextNumber.text.toString())
                commit()
            }
        }
        //读取   并设置   editTextNumber  值
         val sharedPref: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_GPS_user_key),
            Context.MODE_PRIVATE
        )
        navMergeBinding.editTextNumber.setText(sharedPref.getString("user", "10088"))//显示的gpsid 文本
        navMergeBinding.recordAudio.setOnClickListener {
            Log.d("record_audio_button","录音按钮")
            val audio_record =OnlyAudioRecorder.instance
            if (audio_record.isRecord)audio_record.startRecord()//开始录音
            else audio_record.stopRecord()

        }

        activityMainBinding.textView.movementMethod = ScrollingMovementMethod.getInstance()
        // Appbar behavior init 一种跟随着行为， 顶栏 随着 列表 下拉 隐藏
        (activityMainBinding.appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolbarBehavior()

        // RecyclerView Init
        mainListAdapter = MainListAdapter(this)
        val data= ArrayList<MutableLiveData<JsonHitokoto>>()
        for (x in 0..19)  // 输出 0 到 10（含10）
            data.add(MutableLiveData(JsonHitokoto()))
        MainListAdapter.data=data
       gethitokoto(20 - 1)

        mainListAdapter.life = this
        activityMainBinding.recyclerView.adapter = mainListAdapter
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding.recyclerView.setHasFixedSize(true)

        /*fab 数据加载*/
        val OneData= ArrayList<MutableLiveData<Newslist>>()
        for (x in 0 until FiltersLayout.numTabs)  // 横滑条  until 是不取 到numTabs的
            OneData.add(MutableLiveData(Newslist()))
        FiltersPagerAdapter.OneData=OneData
        FiltersPagerAdapter.life=this
        getONEFor(FiltersLayout.numTabs - 1)

        useFiltersMotionLayout(true)
        navMergeBinding.save.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_user_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("sno", navMergeBinding.editTextTextPersonName.text.toString())
                putString("pass",  navMergeBinding.editTextTextPassword.text.toString())
                commit()
            }
        }

//        showNotification(this,"12","Wonderful World","HellWorld",45)//通知
//        App.log_Print.observe(this, {
//            //            textView.text = it
//            //            https://juejin.im/entry/6844903497033318408
//            //            loop  用findviewid 有loop 问题
//        }

//        )
        FontProgressBar.observe(this, {
            navMergeBinding. progressBar.setProgress(it, true)
            navMergeBinding.progressText.text = it.toString() + "%"
        })



    }

    private fun permission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.RECORD_AUDIO

        )
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
            }
    }
//    override fun onSupportNavigateUp() =findNavController(this, R.id.my_nav_host_fragment).navigateUp()

    /**
     * Called from FiltersLayout to get adapter scale down animator
     */
    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)
}
