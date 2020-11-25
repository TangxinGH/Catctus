package com.zhu.nav.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhu.nav.R


class DemoAdapter
/**
 * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
 * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
 */
    (list: MutableList<String>?) :BaseQuickAdapter<String, BaseViewHolder>(R.layout.foldingcell, list) {
    /**
     * 在此方法中设置item数据
     */
    override fun convert(helper: BaseViewHolder, item: String) {
//        helper.setText(R.id.tweetName, "This is an Item, pos: " + (helper.getAdapterPosition() - getHeaderLayoutCount()));
    }
}