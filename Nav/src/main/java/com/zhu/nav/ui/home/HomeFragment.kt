package com.zhu.nav.ui.home

import  actsJSON
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.MediaStoreSignature
import com.norbsoft.typefacehelper.TypefaceHelper
import com.ramotion.circlemenu.CircleMenuView
import com.zhu.daomengkj.Global
import com.zhu.nav.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*

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
                          Global.context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
                        val editor = sharedPreference.edit()
                        editor.putString("username", edit_account.text.toString())
                        editor.putString("password", daomeng_pass.text.toString())
                        editor.apply()
                        Toast.makeText(Global.context, "保存成功！用户名${edit_account.text}和密码${daomeng_pass.text}", Toast.LENGTH_SHORT).show()
                        Toast.makeText(Global.context,"登录中！  保存中",Toast.LENGTH_SHORT).show()
                        com.zhu.daomengkj.App(Global.context, MutableLiveData<actsJSON>()).login()//登录保存token
                    } else Toast.makeText(
                      Global.context,
                        "输入为blank:`${daomeng_login}+${daomeng_pass}`",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }

        val sharedPreference =
          Global.context.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        sharedPreference?.let {
            root.edit_account.setText(it.getString("username",""))
            root.daomeng_pass.setText(it.getString("password",""))
        }



        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) .transform(RoundedCorners(20))
        Glide.with(this).load("https://static01.imgkr.com/temp/41d10e429f14425d8f6cad0d1de33c81.png").placeholder( R.color.colorPrimary).apply(options).into(root.imgvw)
        if (Global.typeface) TypefaceHelper.typeface(root)//应用字体


        return root
    }
}