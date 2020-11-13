package com.zhu.cactus.ui.dashboard

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhu.cactus.R
import com.zhu.daomengkj.App
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//      当提交切换到这时
            textView.text = it

        })

/*被点击的时候，oncreate了,所以不需要 监听 */
            val daomeng = App(com.zhu.cactus.App.context, dashboardViewModel.text)
            if (daomeng.is_login()) {
                daomeng.getids()//得到数据
                println("getItem 被执行了")
            }



        root.join_to_act.setOnClickListener {
            if (edit_join_id2.text != null && edit_join_id2.text.isNotBlank()) {
                val daomeng = App(com.zhu.cactus.App.context,dashboardViewModel.text).join(edit_join_id2.text.toString(),numberPicker.value)//报名活动
            } else Toast.makeText(
                context,
                "id输入为空了或者 “”:`${edit_join_id2}`",
                Toast.LENGTH_SHORT
            ).show()
        }
        root.only_me_join.setOnClickListener {
            val daomeng = App(com.zhu.cactus.App.context,dashboardViewModel.text)
            if (daomeng.is_login()){
                  daomeng.can_join()
            }
        }

        root.numberPicker.maxValue=300
        root.numberPicker.minValue=10
        root.numberPicker.value = 80


        return root
    }

}