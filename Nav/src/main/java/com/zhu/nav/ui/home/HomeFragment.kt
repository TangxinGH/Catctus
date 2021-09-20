package com.zhu.nav.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zhu.nav.R
import com.zhu.nav.databinding.FragmentDashboardBinding
//import kotlinx.android.synthetic.main.fragment_dashboard.*  //kotlin的废弃
//import kotlinx.android.synthetic.main.fragment_dashboard.view.*  //kotlin的废弃
import com.zhu.nav.databinding.FragmentHomeBinding
class HomeFragment : androidx.fragment.app.Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var fragmentHomeBinding: FragmentHomeBinding  //Migrate from Kotlin synthetics to Jetpack view binding
    private val binding get() = fragmentHomeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)  //Migrate from Kotlin synthetics to Jetpack view binding
        val root = binding.root //Migrate from Kotlin synthetics to Jetpack view binding


        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
/*使用root 获得 不然null */
        fragmentHomeBinding.daomengLogin.setOnClickListener {
            fragmentHomeBinding.editAccount.text?.let {
                fragmentHomeBinding.daomengPass?.let {
                    if (fragmentHomeBinding.editAccount.text.isNotBlank() && fragmentHomeBinding.daomengPass.text.isNotBlank()) {
                        val sharedPreference =
                            context?.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
                        val editor = sharedPreference?.edit()
                        editor?.putString("username", fragmentHomeBinding.editAccount.text.toString())
                        editor?.putString("password",  fragmentHomeBinding.daomengPass.text.toString())
                        editor?.apply()
                        Toast.makeText(context, "保存成功！用户名${ fragmentHomeBinding.editAccount.text}和密码${ fragmentHomeBinding.daomengPass.text}", Toast.LENGTH_SHORT).show()
                        Toast.makeText(context,"登录中！  保存中",Toast.LENGTH_SHORT).show()
                         //登录保存token
                    } else Toast.makeText(
                   context,
                        "输入为blank:`${fragmentHomeBinding.daomengLogin}+${ fragmentHomeBinding.daomengPass}`",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }

        val sharedPreference =
            context?.getSharedPreferences("daomengKJ", Context.MODE_PRIVATE)
        sharedPreference?.let {
            fragmentHomeBinding.editAccount.setText(it.getString("username",""))
            fragmentHomeBinding.daomengPass.setText(it.getString("password",""))
        }



        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) .transform(RoundedCorners(20))
        Glide.with(this).load("https://static01.imgkr.com/temp/41d10e429f14425d8f6cad0d1de33c81.png").placeholder(R.color.gray).apply(options).into(fragmentHomeBinding.imgvw)
//        if (  .typeface) TypefaceHelper.typeface(root)//应用字体


        return root
    }
}