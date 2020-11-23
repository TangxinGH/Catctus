package com.zhu.cactus.ui.dashboard

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chenantao.fabMenu.FabMenu
import com.norbsoft.typefacehelper.TypefaceHelper
import com.ramotion.circlemenu.CircleMenuView
import com.zhu.cactus.R
import com.zhu.daomengkj.App
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


        /*  root.join_to_act.setOnClickListener {
              if (edit_join_id2.text != null && edit_join_id2.text.isNotBlank()) {
                  val daomeng = App(com.zhu.cactus.App.context, dashboardViewModel.text).join(
                      edit_join_id2.text.toString(),
                      numberPicker.value
                  )//报名活动
              } else Toast.makeText(
                  context,
                  "id输入为空了或者 “”:`${edit_join_id2}`",
                  Toast.LENGTH_SHORT
              ).show()
          }
          root.only_me_join.setOnClickListener {
              val daomeng = App(com.zhu.cactus.App.context, dashboardViewModel.text)
              if (daomeng.is_login()) {
                  daomeng.can_join()
              }
          }
          root.act_info.setOnClickListener {
              val actInfo = App(com.zhu.cactus.App.context, dashboardViewModel.text)
              if (actInfo.is_login() && edit_join_id2.text.isNotBlank()) {
                  actInfo.chiken(edit_join_id2.text.toString())
                  Toast.makeText(com.zhu.cactus.App.context, "wait ", Toast.LENGTH_SHORT).show()
              } else Toast.makeText(com.zhu.cactus.App.context, "未登录 或者 id 为空 ", Toast.LENGTH_SHORT)
                  .show()
          }

          root.cancel_action.setOnClickListener {
              val act_cancel = App(com.zhu.cactus.App.context, dashboardViewModel.text)
              if (act_cancel.is_login() && edit_join_id2.text.isNotBlank()) {
                  act_cancel.concle(edit_join_id2.text.toString())
              }
          }
  */
        val menu = root.circle_menu
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


        root.fabMenu.setOnMenuItemClickListener(object : FabMenu.OnMenuClickListener() {
            override fun onMenuItemClick(view: View?, title: String?) {

                when (title) {
                    "报名" -> {
                        if (edit_join_id2.text != null && edit_join_id2.text.isNotBlank()) {
                            App(com.zhu.cactus.App.context, dashboardViewModel.text).join(
                                edit_join_id2.text.toString(),
                                sleep_SeekBar.progress
                            )//报名活动
                        } else Toast.makeText(
                            context,
                            "id输入为空了或者 “”:`${edit_join_id2}`",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    "详情" -> {
                        val actInfo = App(com.zhu.cactus.App.context, dashboardViewModel.text)
                        if (actInfo.is_login() && edit_join_id2.text.isNotBlank()) {
                            actInfo.chiken(edit_join_id2.text.toString())
                            Toast.makeText(com.zhu.cactus.App.context, "wait ", Toast.LENGTH_SHORT)
                                .show()
                        } else Toast.makeText(
                            com.zhu.cactus.App.context,
                            "未登录 或者 id 为空 ",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    "仅我" -> {
                        val daomeng = App(com.zhu.cactus.App.context, dashboardViewModel.text)
                        if (daomeng.is_login()) {
                            daomeng.can_join()
                        }
                    }
                    "取消" -> {
                        val act_cancel = App(com.zhu.cactus.App.context, dashboardViewModel.text)
                        if (act_cancel.is_login() && edit_join_id2.text.isNotBlank()) {
                            act_cancel.concle(edit_join_id2.text.toString())
                        }
                    }
                    else -> Log.d("fabmenu", " 什么都不是失败")
                }
            }
        })


       root. sleep_SeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textMin5.text= progress.toString()//设置值
           }

           override fun onStartTrackingTouch(seekBar: SeekBar?) {

           }

           override fun onStopTrackingTouch(seekBar: SeekBar?) {

           }

       })

//        root.numberPicker.maxValue = 900
//        root.numberPicker.minValue = 70
//        root.numberPicker.value = 200 //人的反应为0.2秒

        if (com.zhu.cactus.App.typeface != null) TypefaceHelper.typeface(root)//应用字体


        return root
    }

}