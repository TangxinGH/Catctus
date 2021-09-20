package com.zhu.cactus.method

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.MediaStoreSignature
import com.norbsoft.typefacehelper.TypefaceHelper
import com.zhu.cactus.App
import com.zhu.cactus.POJO.JsonHitokoto
import com.zhu.cactus.R
import com.zhu.cactus.animationPlaybackSpeed
import com.zhu.cactus.utils.*
//import kotlinx.android.synthetic.main.item_list.view.* //kotlin的废弃
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.zhu.cactus.databinding.ItemListBinding
//import kotlinx.android.synthetic.main.item_list.view.*

/*展开动画由本类实现，以及内容？？*/
data class MainListModel(val id: Int)

class MainListAdapter(context: Context) : RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {
    private lateinit var itemListBinding: ItemListBinding  //Migrate from Kotlin synthetics to Jetpack view binding

    private val originalBg: Int by bindColor(context, R.color.list_item_bg_collapsed)
    private val expandedBg: Int by bindColor(context, R.color.list_item_bg_expanded)

    private val listItemHorizontalPadding: Float by bindDimen(context, R.dimen.list_item_horizontal_padding)
    private val listItemVerticalPadding: Float by bindDimen(context, R.dimen.list_item_vertical_padding)
    private val originalWidth = context.screenWidth - 48.dp // 列表 距离屏幕 有边距  从-48 到 -24 一种变大的效果
    private val expandedWidth = context.screenWidth - 24.dp
    private var originalHeight = -1 // will be calculated dynamically
    private var expandedHeight = -1 // will be calculated dynamically

    // filteredItems is a static field to simulate filtering of random items
    private val filteredItems = intArrayOf(2, 5, 6, 8, 12)
    private val modelList = List(20) { MainListModel(it) }
    private val modelListFiltered = modelList.filter { it.id !in filteredItems }
    private val adapterList: List<MainListModel> get() = if (isFiltered) modelListFiltered else modelList
companion object{
    var data=ArrayList<MutableLiveData<JsonHitokoto>>()
}
    lateinit var life: LifecycleOwner


    /** Variable used to filter adapter items. 'true' if filtered and 'false' if not */
    var isFiltered = false
//        set(value) {
//            field = value
//            val diff = MainListDiffUtil(
//                if (field) modelList else modelListFiltered,
//                if (field) modelListFiltered else modelList
//            )
//            DiffUtil.calculateDiff(diff).dispatchUpdatesTo(this)
//        }

    private val listItemExpandDuration: Long get() = (300L / animationPlaybackSpeed).toLong()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private lateinit var recyclerView: RecyclerView
    private var expandedModel: MainListModel? = null
    private var isScaledDown = false

    ///////////////////////////////////////////////////////////////////////////
    // Methods
    ///////////////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int = adapterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(inflater.inflate(R.layout.item_list, parent, false))

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val model = adapterList[position]
//        val url = "https://i.niupic.com/images/2020/09/09/8EkH.jpg"
        if (App.typeface!=null) TypefaceHelper.typeface(holder.cardContainer)//应用字体
        //设置成true才会启动缓存，默认是false
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(
            MediaStoreSignature("jpg", SimpleDateFormat("YYYYMMdd", Locale.CHINA).format(Date()).toLong(), 0)
        ).transform(RoundedCorners(20))
            data[position]//注意越界问题
                .observe(life,androidx.lifecycle.Observer<JsonHitokoto>{
                    holder.binding.title.text= it.id.toString() // //Migrate from Kotlin synthetics to Jetpack view binding
                    holder.binding.OneWord.text= it.hitokoto.toString()  //Migrate from Kotlin synthetics to Jetpack view binding
                    holder.binding.wordFrom.text="————   "+ it.from.toString()  //Migrate from Kotlin synthetics to Jetpack view binding
                    holder.binding.imgAuthor.text= "作者：  "+it.creator.toString() //Migrate from Kotlin synthetics to Jetpack view binding
                    if(position<=8)
                    Glide.with(App.context).load("https://bing.biturl.top/?resolution=1366&format=image&index=$position&mkt=zh-CN").apply(options) .into(holder.binding.myImageView)
                    else
                        Glide.with(App.context).load("https://api.paugram.com/wallpaper/?f=$position").apply(options) .into(holder.binding.myImageView)

//                       .fallback( ColorDrawable(Color.GRAY))

                } )
        expandItem(holder, model == expandedModel, animate = false)
        scaleDownItem(holder, position, isScaledDown)
        //展开监听
        holder.cardContainer.setOnClickListener {
            when (expandedModel) {
                null -> {

                    // expand clicked view
                    expandItem(holder, expand = true, animate = true)
                    expandedModel = model

//                    Glide.with(App.context).asBitmap() 背景
//                        .load("https://api.paugram.com/wallpaper/?f=$position")
//                        .apply(options)
//                        .into(object : CustomTarget<Bitmap>(){
//                            override fun onResourceReady(
//                                resource: Bitmap,
//                                transition: Transition<in Bitmap>?
//                            ) {
//                                val bitmap=   Bitmap.createBitmap( resource,resource.width/3,0,resource.width*2/3,resource.height)
//                                if (bitmap!=null)
//                                    holder.scaleContainer.background=BitmapDrawable(App.context.resources, bitmap)
//                            }
//
//                            override fun onLoadCleared(placeholder: Drawable?) {
//
//                            }
//                        })


                }
                model -> {

                    // collapse clicked view
                    expandItem(holder, expand = false, animate = true)
                    expandedModel = null

//                   if(isApkInDebug(App.context)) holder.scaleContainer.background= ColorDrawable(Color.GRAY)
//                    else holder.scaleContainer.background= ColorDrawable(Color.WHITE)

                }
                else -> {

                    // collapse previously expanded view
                    val expandedModelPosition = adapterList.indexOf(expandedModel!!)
                    val oldViewHolder =
                        recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? ListViewHolder
                    if (oldViewHolder != null) expandItem(oldViewHolder, expand = false, animate = true)

                    // expand clicked view
                    expandItem(holder, expand = true, animate = true)
                    expandedModel = model

                }
            }
        }
    }
//显示隐藏动画
    private fun expandItem(holder: ListViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(holder, progress) }

            if (expand) animator.doOnStart { holder.expandView.isVisible = true }
            else animator.doOnEnd { holder.expandView.isVisible = false }

            animator.start()
        } else {

            // show expandView only if we have expandedHeight (onViewAttached)
            holder.expandView.isVisible = expand && expandedHeight >= 0
            setExpandProgress(holder, if (expand) 1f else 0f)
        }
    }

    override fun onViewAttachedToWindow(holder: ListViewHolder) {
        super.onViewAttachedToWindow(holder)

        // get originalHeight & expandedHeight if not gotten before
        if (expandedHeight < 0) {
            expandedHeight = 0 // so that this block is only called once

            holder.cardContainer.doOnLayout { view ->
                originalHeight = view.height

                // show expandView and record expandedHeight in next layout pass
                // (doOnPreDraw) and hide it immediately. We use onPreDraw because
                // it's called after layout is done. doOnNextLayout is called during
                // layout phase which causes issues with hiding expandView.
                holder.expandView.isVisible = true
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.expandView.isVisible = false
                }
            }
        }
    }

    private fun setExpandProgress(holder: ListViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.cardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.cardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        holder.cardContainer.setBackgroundColor(blendColors(originalBg, expandedBg, progress))
        holder.cardContainer.requestLayout()

        holder.chevron.rotation = 90 * progress
    }

    ///////////////////////////////////////////////////////////////////////////
    // Scale Down Animation
    ///////////////////////////////////////////////////////////////////////////

    private inline val LinearLayoutManager.visibleItemsRange: IntRange
        get() = findFirstVisibleItemPosition()..findLastVisibleItemPosition()

    fun getScaleDownAnimator(isScaledDown: Boolean): ValueAnimator {
        val lm = recyclerView.layoutManager as LinearLayoutManager

        val animator = getValueAnimator(isScaledDown,
            duration = 300L, interpolator = AccelerateDecelerateInterpolator()
        ) { progress ->

            // Get viewHolder for all visible items and animate attributes
            for (i in lm.visibleItemsRange) {
                val holder = recyclerView.findViewHolderForLayoutPosition(i) as ListViewHolder
                setScaleDownProgress(holder, i, progress)
            }
        }

        // Set adapter variable when animation starts so that newly binded views in
        // onBindViewHolder will respect the new size when they come into the screen
        animator.doOnStart { this.isScaledDown = isScaledDown }

        // For all the non visible items in the layout manager, notify them to adjust the
        // view to the new size
        animator.doOnEnd {
            repeat(lm.itemCount) { if (it !in lm.visibleItemsRange) notifyItemChanged(it) }
        }
        return animator
    }

    private fun setScaleDownProgress(holder: ListViewHolder, position: Int, progress: Float) {
        val itemExpanded = position >= 0 && adapterList[position] == expandedModel
        holder.cardContainer.layoutParams.apply {
            width = ((if (itemExpanded) expandedWidth else originalWidth) * (1 - 0.1f * progress)).toInt()
            height = ((if (itemExpanded) expandedHeight else originalHeight) * (1 - 0.1f * progress)).toInt()
//            log("width=$width, height=$height [${"%.2f".format(progress)}]")
        }
        holder.cardContainer.requestLayout()

        holder.scaleContainer.scaleX = 1 - 0.05f * progress
        holder.scaleContainer.scaleY = 1 - 0.05f * progress

        holder.scaleContainer.setPadding(
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt()
        )

        holder.listItemFg.alpha = progress
    }

    /** Convenience method for calling from onBindViewHolder */
    private fun scaleDownItem(holder: ListViewHolder, position: Int, isScaleDown: Boolean) {
        setScaleDownProgress(holder, position, if (isScaleDown) 1f else 0f)
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewHolder
    ///////////////////////////////////////////////////////////////////////////

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemListBinding.bind(itemView)//Migrate from Kotlin synthetics to Jetpack view binding  // add by xf.zhu because of view binding

        val expandView: View by bindView(R.id.expand_view)

        val chevron: View by bindView(R.id.chevron)

        val cardContainer: View by bindView(R.id.card_container)

        val scaleContainer: View by bindView(R.id.scale_container)

        val listItemFg: View by bindView(R.id.list_item_fg)

    }
}