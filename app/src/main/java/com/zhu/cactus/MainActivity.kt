package com.zhu.cactus

import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.method.MainListAdapter
import com.zhu.cactus.method.ToolbarBehavior
import com.zhu.cactus.utils.showNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_drawer.*
import org.json.JSONObject

var animationPlaybackSpeed: Double = 0.8  //动画播放速度

class MainActivity : AppCompatActivity() {
    companion object
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
        switchMaterial.setOnClickListener{ textView.isClickable=switchMaterial.isChecked}//滚动

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

//        showNotification(this,"12","Wonderful World","HellWorld",45)//通知
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
    
    /**
     * Called from FiltersLayout to get adapter scale down animator
     */
    fun getAdapterScaleDownAnimator(isScaledDown: Boolean): ValueAnimator =
        mainListAdapter.getScaleDownAnimator(isScaledDown)
}
