package com.zhu.nav.RecyclerView

import activities
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.daomengkj.Gobal.typeface
import com.zhu.nav.R
import kotlinx.android.synthetic.main.cell.view.*
import kotlinx.android.synthetic.main.expanding_item.view.*
import kotlinx.android.synthetic.main.expanding_sub_item.view.*


class DemoAdapter
/**
 * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
 * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
 */(list: MutableList<activities>) :BaseQuickAdapter< activities, BaseViewHolder>(R.layout.cell, list) {
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



    override fun convert(helper: BaseViewHolder, item:  activities) {
        val expandingItem =helper.itemView.expanding_list_main.createNewItem(R.layout.expanding_layout)
        expandingItem.title.text=item.catalog2name
        expandingItem.act_name.text=item.name
        if (typeface) TypefaceHelper.typeface(helper.itemView)//字体
        expandingItem.setIndicatorColorRes( indicatorsColor.random())
        expandingItem.setIndicatorIconRes(R.drawable.ic_activity )//随机

//This will create 5 items
        expandingItem.createSubItems(1)

        val subItemView = expandingItem.getSubItemView(0)
        if (typeface) TypefaceHelper.typeface(subItemView)
        subItemView.sub_title.text="Id：${item.activityId}"
        subItemView.activity_info_statusText.text="状态：${item.statusText}"
        subItemView.activity_info_Id.text= item.aid.toString()
        subItemView.activity_info_activityTime.text="活动时间：${item.activitytime}"
        Glide.with(subItemView).load(item.imageUrl).into(subItemView.imagurl)

//        helper.setText(R.id.tweetName, "This is an Item, pos: " + (helper.getAdapterPosition() - getHeaderLayoutCount()));

        /* private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });
    }*/
    }
}