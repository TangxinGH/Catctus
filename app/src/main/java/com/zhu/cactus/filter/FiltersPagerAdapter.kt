package com.zhu.cactus.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.App
import com.zhu.cactus.POJO.Newslist
import com.zhu.cactus.R
import com.zhu.cactus.utils.bindColor
import com.zhu.cactus.utils.bindOptionalViews
import com.zhu.cactus.utils.blendColors
import com.zhu.cactus.utils.getValueAnimator
import com.zhu.cactus.views.FilterSeekbar

/**
 * ViewPager adapter to display all the filters
 */
class FiltersPagerAdapter(context: Context, private val listener: (updatedPosition: Int, selectedMap: Map<Int, List<Int>>) -> Unit)
    : RecyclerView.Adapter<FiltersPagerAdapter.FiltersPagerViewHolder>() {

    private val unselectedColor: Int by bindColor(context, R.color.filter_pill_color)
    private val selectedColor: Int by bindColor(context, R.color.filter_pill_selected_color)
    private val unselectedBarColor: Int by bindColor(context, R.color.filter_seek_bar_color)
    private val selectedBarColor: Int by bindColor(context, R.color.filter_seek_bar_selected_color)

    private val toggleAnimDuration = context.resources.getInteger(R.integer.toggleAnimDuration).toLong()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var selectedMap = mutableMapOf<Int, MutableList<Int>>()

    companion object{
        var OneData=ArrayList<MutableLiveData<Newslist>>()
        lateinit var life: LifecycleOwner

    }
    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int = FiltersLayout.numTabs

    override fun getItemViewType(position: Int): Int = when {
        position == 2 -> R.layout.filter_layout_3
        position % 2 == 0 -> R.layout.filter_layout_1
        else -> R.layout.filter_layout_2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersPagerViewHolder =
            FiltersPagerViewHolder(inflater.inflate(viewType, parent, false))

    override fun onBindViewHolder(holder: FiltersPagerViewHolder, position: Int) {
        val selectedList = selectedMap.getOrPut(position) { mutableListOf() }

        /**
         * Bind all the filter buttons (if any). Clicking the filter button toggles state
         * which is shown by a short toggle animation
         */
        if (App.typeface!=null) TypefaceHelper.typeface(holder.itemView) //应用字体

        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).transform(RoundedCorners(20))
        OneData[position].observe(life, Observer {
             holder.filterViewText.forEachIndexed{index, materialTextView -> materialTextView.text=
            OneData[position].value?.word ?: "世界，您好啊！"   } //fab 很早之前就初始化了 所以要 LiveData
            val imgurl= OneData[position].value?.imgurl?:"https://static01.imgkr.com/temp/026208c3f36b4511a9d6f2b9f21d9679.jpg"
            Glide.with(App.context).load(imgurl).apply(options) .into(holder.filterViews[0])
        })

        holder.filterViews.forEachIndexed { index: Int, filterView:ImageView ->

            filterView.setOnClickListener {
                val isToggled = selectedList.contains(index)

                if (isToggled) selectedList -= index
                else selectedList += index

                val toggleAnimator = getValueAnimator(!isToggled,
                        toggleAnimDuration, DecelerateInterpolator()) { progress ->

                    filterView.setColorFilter(blendColors(unselectedColor, selectedColor, progress))
                }
                toggleAnimator.start()

                listener(position, selectedMap)
            }
        }

        /**
         * Bind the Seekbars (if any). Sliding the seekbar between 1f..99f toggles it on.
         * 1f and 99f are chosen just to make the toggling seem more smooth
         */
        holder.seekBars.forEachIndexed { index: Int, seekBar: FilterSeekbar ->
            seekBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
                if (!selectedList.contains(index) && !(minValue.toFloat() < 1f && maxValue.toFloat() > 99f)) {
                    selectedList += index
                    listener(position, selectedMap)
                    seekBar.setLeftThumbHighlightColor(selectedColor)
                    seekBar.setRightThumbHighlightColor(selectedColor)
                    seekBar.setLeftThumbColor(selectedColor)
                    seekBar.setRightThumbColor(selectedColor)
                    seekBar.setBarHighlightColor(selectedBarColor)
                } else if (selectedList.contains(index) && minValue.toFloat() < 1f && maxValue.toFloat() > 99f) {
                    selectedList -= index
                    listener(position, selectedMap)
                    seekBar.setLeftThumbHighlightColor(unselectedColor)
                    seekBar.setRightThumbHighlightColor(unselectedColor)
                    seekBar.setLeftThumbColor(unselectedColor)
                    seekBar.setRightThumbColor(unselectedColor)
                    seekBar.setBarHighlightColor(unselectedBarColor)
                }
            }
        }
    }

    class FiltersPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterViews: List<ImageView> by bindOptionalViews(R.id.filter_pill_1, R.id.filter_pill_2,
                 R.id.filter_pill_4, R.id.filter_pill_5,R.id.filter_pill_6)
val filterViewText:List<com.google.android.material.textview.MaterialTextView> by bindOptionalViews(R.id.filter_pill_3Word)//要对应类型
        val seekBars: List<FilterSeekbar> by bindOptionalViews(R.id.rangeSeekbar1, R.id.rangeSeekbar2)
    }
}
