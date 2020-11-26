package com.zhu.nav.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhu.nav.R
import kotlinx.android.synthetic.main.cell.view.*
import kotlinx.android.synthetic.main.expanding_item.view.*
import kotlinx.android.synthetic.main.expanding_sub_item.view.*


class DemoAdapter
/**
 * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
 * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
 */
    (list: MutableList<String>?) :BaseQuickAdapter<String, BaseViewHolder>(R.layout.cell, list) {
    /**
     * 在此方法中设置item数据
     */
    val indicatorsColor = listOf( R.color.pink,
        R.color.blue,
        R.color.purple,
        R.color.yellow,
        R.color.orange,
        R.color.green,
        R.color.blue,
        R.color.yellow,
        R.color.purple,)
    override fun convert(helper: BaseViewHolder, item: String) {
        val expandingItem =helper.itemView.expanding_list_main.createNewItem(R.layout.expanding_layout)
        expandingItem.title.text="it works"
        expandingItem.act_name.text="fjdsklfsdfdsfdsfdsfsdfdsactivity_info_name "

        expandingItem.setIndicatorColorRes( indicatorsColor.random())
        expandingItem.setIndicatorIconRes(R.drawable.ic_activity )//随机

//This will create 5 items
        expandingItem.createSubItems(4)

        expandingItem.getSubItemView(0).sub_title.text="activity_info_Id"
        expandingItem.getSubItemView(1).sub_title.text="activity_info_catalog2name"
        expandingItem.getSubItemView(2).sub_title.text="activity_info_statusText"
        expandingItem.getSubItemView(3).sub_title.text="activity_info_activityTime"
//        helper.setText(R.id.tweetName, "This is an Item, pos: " + (helper.getAdapterPosition() - getHeaderLayoutCount()));
    }
}