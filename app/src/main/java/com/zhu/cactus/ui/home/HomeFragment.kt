package com.zhu.cactus.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.zhu.cactus.App
import com.zhu.cactus.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : androidx.fragment.app.Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
/*使用root 获得 不然null */
        root.daomeng_login.setOnClickListener {
            edit_account.text?.let {
                daomeng_pass?.let {
                    if (edit_account.text.isNotBlank() && daomeng_pass.text.isNotBlank()) {
                        val sharedPreference =
                            App.context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("username", edit_account.text.toString())
                        editor.putString("password", daomeng_pass.text.toString())
                        editor.apply()
                        Toast.makeText(App.context, "保存成功！用户名${edit_account.text}和密码${daomeng_pass.text}", Toast.LENGTH_SHORT).show()
                        Toast.makeText(App.context,"登录中！  保存中",Toast.LENGTH_SHORT).show()
                        com.zhu.daomengkj.App(App.context,homeViewModel.text).login()//登录保存token
                    } else Toast.makeText(
                        App.context,
                        "输入为blank:`${daomeng_login}+${daomeng_pass}`",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }

        val sharedPreference =
            App.context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        sharedPreference?.let {
            root.edit_account.setText(it.getString("username",""))
            root.daomeng_pass.setText(it.getString("password",""))
        }


        return root
    }
}