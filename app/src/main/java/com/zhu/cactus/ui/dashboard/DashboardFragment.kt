package com.zhu.cactus.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zhu.cactus.R
import com.zhu.daomengkj.App
import com.zhu.daomengkj.App.Companion.activities
import com.zhu.daomengkj.Py_invoke_Java
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
//      当点击切换到这时
            textView.text = it
            val daomeng = App()
            daomeng._init_()//初始化
            if (daomeng.is_login()) {
                textView.text = daomeng.login()
                println("dashboardviewmodel 被执行了")
//          activities=dashboardViewModel.text
            }


        })

        root.join_to_act.setOnClickListener {
            if (edit_join_id2.text != null && edit_join_id2.text.isNotBlank()) {
                Py_invoke_Java.id2 = edit_join_id2.text.toString()
                App().join()//报名活动
            } else Toast.makeText(
                App.dao_meng_context,
                "id输入为空了或者 “”:`${edit_join_id2}`",
                Toast.LENGTH_SHORT
            ).show()
        }
        root.only_me_join.setOnClickListener {
            val daomeng = App()
            if (daomeng.is_login()){
                textView.text= daomeng.can_join()
            }
        }

        return root
    }

}