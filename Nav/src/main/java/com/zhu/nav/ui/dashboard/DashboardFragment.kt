package com.zhu.nav.ui.dashboard

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.chenantao.fabMenu.FabMenu
import com.ramotion.circlemenu.CircleMenuView
import com.zhu.nav.R
//import kotlinx.android.synthetic.main.fragment_dashboard.*  //kotlin的废弃
//import kotlinx.android.synthetic.main.fragment_dashboard.view.*  //kotlin的废弃
import com.zhu.nav.databinding.FragmentDashboardBinding

class DashboardFragment : androidx.fragment.app.Fragment() {
    private lateinit var fragmentDashboardBinding: FragmentDashboardBinding  //Migrate from Kotlin synthetics to Jetpack view binding
    private val binding get() = fragmentDashboardBinding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    val indicatorsColor = listOf(
        R.color.pink,
        R.color.blue,
        R.color.purple,
        R.color.yellow,
        R.color.orange,
        R.color.green,
        R.color.blue,
        R.color.yellow,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)  //Migrate from Kotlin synthetics to Jetpack view binding
        val root = binding.root //Migrate from Kotlin synthetics to Jetpack view binding

//        val root = inflater.inflate(R.layout.fragment_dashboard, container, false) //mark

        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        dashboardViewModel.text.observe(viewLifecycleOwner, {
//      当提交切换到这时
            textView.text = it

        })


            /**先移除 原先的 */
            fragmentDashboardBinding.expandingListMain.removeAllViews()
            /**保存一下 圆形菜单用*/
            /**分组*/
//            itJson.data.list.groupBy { it.catalog2name }.forEach { itemCata, subItemList ->



            /*停止刷新*/
        fragmentDashboardBinding.refreshLayout.getmHeader()?.let {
            fragmentDashboardBinding.refreshLayout.finishRefreshing()// 第一次 没有刷新所以是空的
            }

        val menu = fragmentDashboardBinding.circleMenu
        menu.eventListener = object : CircleMenuView.EventListener() {
            /*    override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                    Log.d("D", "onMenuOpenAnimationStart")
                }

                override fun onMenuOpenAnimationEnd(view: CircleMenuView) {
                    Log.d("D", "onMenuOpenAnimationEnd")
                }

                override fun onMenuCloseAnimationStart(view: CircleMenuView) {
                    Log.d("D", "onMenuCloseAnimationStart")
                }

                override fun onMenuCloseAnimationEnd(view: CircleMenuView) {
                    Log.d("D", "onMenuCloseAnimationEnd")
                }*/

            override fun onButtonClickAnimationStart(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationStart| index: $index")

            }

            override fun onButtonClickAnimationEnd(view: CircleMenuView, index: Int) {
                Log.d("D", "onButtonClickAnimationEnd| index: $index")
            }
        }






        fragmentDashboardBinding.fabMenu.setOnMenuItemClickListener(object : FabMenu.OnMenuClickListener() {
            override fun onMenuItemClick(view: View?, title: String?) {

                when (title) {
                    "报名" -> {

                    }
                    "详情" -> {

                    }
                    "仅我" -> {

                    }
                    "取消" -> {

                }
            }
        }})





        return root
    }


}