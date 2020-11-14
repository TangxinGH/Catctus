package com.zhu.cactus.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.App
import com.zhu.cactus.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        /** 遍历所有children*/
        root.chip_group.children.iterator().forEachRemaining {
            it as Chip
            it.setOnCheckedChangeListener { chipView, isChecked ->
                if (isChecked && chipView.text != "") {
                    val sharedPreference =
                        App.context.getSharedPreferences(
                            "daomengKJNotifications",
                            Context.MODE_PRIVATE
                        )
                    val editor = sharedPreference.edit()
                    editor.putBoolean(chipView.text.toString(), true)
                    editor.apply()
                    Toast.makeText(root.context,"增加${chipView.text}成功！",Toast.LENGTH_SHORT).show()
                } else {
                    val sharedPreference =
                        App.context.getSharedPreferences(
                            "daomengKJNotifications",
                            Context.MODE_PRIVATE
                        )
                    val editor = sharedPreference.edit()
                    editor.remove(chipView.text.toString()).apply()//删除一个
                    Toast.makeText(root.context,"删除${chipView.text}成功！",Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (App.typeface != null) TypefaceHelper.typeface(root)//应用字体
        return root
    }
}