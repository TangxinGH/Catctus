package com.zhu.nav.ui.dashboard

import activities
import actsJSON
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.baianat.floadingcellanimationsample.utils.GridSpacingItemDecoration
import com.chenantao.fabMenu.FabMenu
import com.norbsoft.typefacehelper.TypefaceHelper
import com.ramotion.circlemenu.CircleMenuView
import com.zhu.daomengkj.App
import com.zhu.nav.BtnBottomDialog
import com.zhu.daomengkj.Gobal
import com.zhu.nav.R
import com.zhu.nav.RecyclerView.DemoAdapter
import com.zhu.nav.utils.ViewUtils
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


        //        )//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/kotlin/kotlin-mutablelist-mutablelistof.html
/*test 则 1 release 则size 0 */
        fun isApkInDebug(context: Context): Boolean {
            return try {
                val info = context.applicationInfo
                info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            } catch (e: Exception) {
                false
            }
        }

        val toList = arrayListOf(
            activities(
                123456,
                123456,
                "https://image.5idream.net/1231604646538085?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg",
                "第一 镇魂 在 夺灰 工鼓励国鞋子加工 啊载大福利 鞋子大小气鬼机加工灰尘荥鞋跟阿凡达革某家炒东跑西颠 春树暮云夺田浮点",
                1,
                "statusText",
                "activitytime",
                "catalog2name"
            ), activities(
                545421,
                5555,
                "https://image.5idream.net/1231604646538085?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg",
                "第二 镇魂 在 夺灰 工鼓励国鞋子加工 啊载ghjg hvg大福利 鞋子大小气鬼机加工灰尘荥鞋跟阿凡达革某家炒东跑西颠 春树暮云夺田浮点",
                1,
                "statusText",
                "activitytime",
                "catalog2name"
            )
        ).toList()
        val demoAdapter = if (context?.let { isApkInDebug(it) } == true) {
            DemoAdapter(
                MutableList(
                    2,
                    init = {if (it==0)Pair("创新创业", toList)
                         else Pair("思想成长", arrayListOf(activities(123456,123456,"https://image.5idream.net/1231604646538085?x-oss-process=image/resize,w_375,h_0/quality,Q_100/format,jpg",
                                    "name 镇魂 在 夺灰 工鼓励国鞋子加工 啊载大福利 鞋子大小气鬼机加工灰尘荥鞋跟阿凡达革某家炒东跑西颠 春树暮云夺田浮点",
                                    1,
                                    "statusText",
                                    "activitytime",
                                    "catalog2name"
                                )
                            )
                        )
                    })
            )
        } else DemoAdapter( emptyList<Pair<String,List<activities>>>().toMutableList())//空集合


        root.recyclerview.adapter = demoAdapter
        root.recyclerview.layoutManager = GridLayoutManager(context, 1)//网络布局，而 LinearLayout只能一行
//        root.recyclerview.layoutManager= LinearLayoutManager(
//            context,
//            LinearLayoutManager.VERTICAL,
//            false
//        ) //布局管理器：以垂直或者水平列表方式展示Item

        root.recyclerview.addItemDecoration(
            GridSpacingItemDecoration(1, ViewUtils.dpToPx(12f), true, 0)
        )//间距问题

//         Item 内子View的点击事件：
//        注意，请不要在convert方法里注册控件id

// 先注册需要点击的子控件id（注意，请不要写在convert方法里）
//        demoAdapter.addChildClickViewIds(R.id.folding_cell)
//        demoAdapter.setOnItemChildClickListener { adapter, view, position ->
//            if (view.id == R.id.folding_cell) {
////                Tips.show("onItemChildClick $position")
//                val fc = view as FoldingCell
//                fc.toggle(false)
//            }
//        }


        val acts_info = MutableLiveData<actsJSON>()
        acts_info.observe(viewLifecycleOwner, { itJson ->

            /*      val acts = StringBuilder("所有活动：\n")
                  for (index in it.data.list) {
                      acts.append(
                          index.activityId
                      ).append(" ")

                          .append(
                              index.catalog2name
                          ).append(" ")
                          .append(
                              index.statusText
                          ).append(" ") .append(index.activitytime).append("\n")
                          .append(
                              index.name
                          ).append("\n\n")
                  }*/
            val pairList = itJson.data.list.groupBy { it.catalog2name }.toList() //分组
            demoAdapter.setNewInstance(pairList.toMutableList())//设置新数据
        })
/*被点击的时候，oncreate了,所以不需要 监听 */
        val daomeng = App(Gobal.context, acts_info)
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


        root.fabMenu.setOnMenuItemClickListener(object : FabMenu.OnMenuClickListener() {
            override fun onMenuItemClick(view: View?, title: String?) {

                when (title) {
                    "报名" -> {
                        if (edit_join_id2.text != null && edit_join_id2.text.isNotBlank()) {
                            if (App.sleep_seekBar.value != null) App(
                                Gobal.context,
                                acts_info
                            ).join(
                                edit_join_id2.text.toString(),
                                App.sleep_seekBar.value!!
                            )//报名活动
                            else App(Gobal.context, acts_info).join(
                                edit_join_id2.text.toString(),
                                200
                            )
                        } else Toast.makeText(
                            context,
                            "id输入为空了或者 “”:`${edit_join_id2}`",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    "详情" -> {
                        val actInfo = App(Gobal.context, acts_info)
                        if (actInfo.is_login() && edit_join_id2.text.isNotBlank()) {
                            actInfo.chiken(edit_join_id2.text.toString())
                            Toast.makeText(Gobal.context, "wait ", Toast.LENGTH_SHORT)
                                .show()
                        } else Toast.makeText(
                            Gobal.context,
                            "未登录 或者 id 为空 ",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    "仅我" -> {
                        val daomeng = App(Gobal.context, acts_info)
                        if (daomeng.is_login()) {
                            daomeng.can_join()
                        }
                    }
                    "取消" -> {
                        val act_cancel = App(Gobal.context, acts_info)
                        if (act_cancel.is_login() && edit_join_id2.text.isNotBlank()) {
                            act_cancel.concle(edit_join_id2.text.toString())
                        }
                    }
                    else -> Log.d("fabmenu", " 什么都不是失败")
                }
            }
        })




        App.sleep_seekBar.observe(viewLifecycleOwner, {
            textMin5.text = it.toString()//设置值
        }) //还可以这样啊

        if (Gobal.typeface) TypefaceHelper.typeface(root)//应用字体

        root.textMin5.setOnClickListener {
            if (activity != null) {
                BtnBottomDialog().show(requireActivity().supportFragmentManager, "tag")
            } else {
                Log.d("bottomsheet", "没有activity")
            }

        }




        return root
    }

}