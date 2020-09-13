package com.zhu.cactus

import android.Manifest
import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbsoft.typefacehelper.TypefaceHelper
import com.permissionx.guolindev.PermissionX
import com.zhu.cactus.ONE.getONEFor
import com.zhu.cactus.ONE.gethitokoto
import com.zhu.cactus.POJO.JsonHitokoto
import com.zhu.cactus.POJO.Newslist
import com.zhu.cactus.download.font.FontProgressBar
import com.zhu.cactus.download.font.iniFont
import com.zhu.cactus.filter.FiltersLayout
import com.zhu.cactus.filter.FiltersPagerAdapter
import com.zhu.cactus.method.MainListAdapter
import com.zhu.cactus.method.ToolbarBehavior
import com.zhu.cactus.utils.Util.startToAutoStartSetting
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*


var animationPlaybackSpeed: Double = 0.8  //动画播放速度

class MainActivity : AppCompatActivity() {
    companion object
    private

    lateinit var mainListAdapter: MainListAdapter

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
        if (App.typeface!=null)TypefaceHelper.typeface(this)//应用字体
        initData()
        permission()
            }

    /**
     * Callback for motionLayoutCheckbox
     * isChecked = true -> Use [FiltersMotionLayout]
     * isChecked = false -> Use [FiltersLayout]
     */
    private fun useFiltersMotionLayout(isChecked: Boolean) {
        filters_motion_layout.isVisible = isChecked
    }

    private fun initData() {
        drawer_icon.setOnClickListener { drawer_layout.openDrawer(GravityCompat.START) } //左则菜单
        textButton.setOnClickListener { startToAutoStartSetting(this) }
        switchMaterial.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_TTF_Font_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("font", switchMaterial.isChecked.toString())
                commit()
            }
        }// 服务？
        GpsID.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_GPS_user_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("user", editTextNumber.text.toString())
                commit()
            }
        }
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        // Appbar behavior init 一种跟随着行为， 顶栏 随着 列表 下拉 隐藏
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolbarBehavior()

        // RecyclerView Init
        mainListAdapter = MainListAdapter(this)
        val data= ArrayList<MutableLiveData<JsonHitokoto>>()
        for (x in 0..19)  // 输出 0 到 10（含10）
            data.add( MutableLiveData(JsonHitokoto()))
        MainListAdapter.data=data
       gethitokoto(20 - 1)

        mainListAdapter.life = this
        recycler_view.adapter = mainListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        /*fab 数据加载*/
        val OneData= ArrayList<MutableLiveData<Newslist>>()
        for (x in 0 until FiltersLayout.numTabs)  // 横滑条  until 是不取 到numTabs的
            OneData.add( MutableLiveData(Newslist()))
        FiltersPagerAdapter.OneData=OneData
        FiltersPagerAdapter.life=this
        getONEFor(FiltersLayout.numTabs - 1)

        useFiltersMotionLayout(true)
        save.setOnClickListener {
            //储存当前用户
            val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_user_key),
                Context.MODE_PRIVATE
            )
            with(sharedPref.edit()) {
                putString("sno", editTextTextPersonName.text.toString())
                putString("pass", editTextTextPassword.text.toString())
                commit()
            }
        }

//        showNotification(this,"12","Wonderful World","HellWorld",45)//通知
        App.log_Print.observe(this, androidx.lifecycle.Observer<String> {
//            textView.text = it
//            https://juejin.im/entry/6844903497033318408
//            loop  用findviewid 有loop 问题
        })
        FontProgressBar.observe(this,androidx.lifecycle.Observer <Int> {
           progressBar.setProgress(it,true)
            progress_text.text=it.toString()+"%"
        } )


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
                Manifest.permission.ACCESS_WIFI_STATE
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
