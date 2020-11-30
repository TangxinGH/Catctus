package com.zhu.nav.ui.swiperefresh

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import com.zhu.nav.R

/**
 * Created by zhanglei on 15/7/20.
 */
class CircleRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mHeaderBackColor = -0x746f51
    private var mHeaderForeColor = -0x1
    private var mHeaderCircleSmaller = 6
    private var mPullHeight = 0f
    private var mHeaderHeight = 0f
    private var mChildView: View? = null
    protected var mHeader: AnimationView? = null
    fun getmHeader(): AnimationView? {
        return mHeader
    }

    private var mIsRefreshing = false
    private var mTouchStartY = 0f
    private var mTouchCurY = 0f
    private var mUpBackAnimator: ValueAnimator? = null
    private var mUpTopAnimator: ValueAnimator? = null
    private val decelerateInterpolator = DecelerateInterpolator(10F)
    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (childCount > 1) {
            throw RuntimeException("you can only attach one child")
        }
        setAttrs(attrs)
        mPullHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            150f,
            context.resources.displayMetrics
        )
        mHeaderHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            context.resources.displayMetrics
        )
        post {
            mChildView = getChildAt(0)
            addHeaderView()
        }
    }

    private fun setAttrs(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CirCleRefreshLayout)
        mHeaderBackColor =
            a.getColor(R.styleable.CirCleRefreshLayout_AniBackColor, mHeaderBackColor)
        mHeaderForeColor =
            a.getColor(R.styleable.CirCleRefreshLayout_AniForeColor, mHeaderForeColor)
        mHeaderCircleSmaller =
            a.getInt(R.styleable.CirCleRefreshLayout_CircleSmaller, mHeaderCircleSmaller)
        a.recycle()
    }

    private fun addHeaderView() {
        mHeader = AnimationView(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        params.gravity = Gravity.TOP
        mHeader!!.layoutParams = params
        addViewInternal(mHeader!!)
        mHeader!!.setAniBackColor(mHeaderBackColor)
        mHeader!!.setAniForeColor(mHeaderForeColor)
        mHeader!!.setRadius(mHeaderCircleSmaller)
        setUpChildAnimation()
    }

    private fun setUpChildAnimation() {
        if (mChildView == null) {
            return
        }
        val mUpBackAnimator = ValueAnimator.ofFloat(mPullHeight, mHeaderHeight)
        mUpBackAnimator.addUpdateListener { animation ->
            val `val` = animation.animatedValue as Float
            if (mChildView != null) {
                mChildView!!.translationY = `val`
            }
        }
        mUpBackAnimator.duration = REL_DRAG_DUR
        this.mUpBackAnimator =mUpBackAnimator

        val mUpTopAnimator = ValueAnimator.ofFloat(mHeaderHeight, 0f)
        mUpTopAnimator.addUpdateListener { animation ->
            var `val` = animation.animatedValue as Float
            `val` *= decelerateInterpolator.getInterpolation(`val` / mHeaderHeight)
            if (mChildView != null) {
                mChildView!!.translationY = `val`
            }
            mHeader!!.layoutParams.height = `val`.toInt()
            mHeader!!.requestLayout()
        }
        mUpTopAnimator.duration = BACK_TOP_DUR
        mHeader!!.setOnViewAniDone(object : AnimationView.OnViewAniDone{
            override fun viewAniDone() {
                mUpTopAnimator.start()
                //                Log.i(TAG, "should invoke");
            }
        })
        this.mUpTopAnimator =mUpTopAnimator
    }

    private fun addViewInternal(child: View) {
        super.addView(child)
    }

    override fun addView(child: View) {
        if (childCount >= 1) {
            throw RuntimeException("you can only attach one child")
        }
        mChildView = child
        super.addView(child)
        setUpChildAnimation()
    }

    private fun canChildScrollUp(): Boolean {
        return if (mChildView == null) {
            false
        } else ViewCompat.canScrollVertically(mChildView, -1)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (mIsRefreshing) {
            return true
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchStartY = ev.y
                mTouchCurY = mTouchStartY
            }
            MotionEvent.ACTION_MOVE -> {
                val curY = ev.y
                val dy = curY - mTouchStartY
                if (dy > 0 && !canChildScrollUp()) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mIsRefreshing) {
            super.onTouchEvent(event)
        } else when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mTouchCurY = event.y
                var dy = mTouchCurY - mTouchStartY
                dy = Math.min(mPullHeight * 2, dy)
                dy = Math.max(0f, dy)
                if (mChildView != null) {
                    val offsetY =
                        decelerateInterpolator.getInterpolation(dy / 2 / mPullHeight) * dy / 2
                    mChildView!!.translationY = offsetY
                    mHeader!!.layoutParams.height = offsetY.toInt()
                    mHeader!!.requestLayout()
                }
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mChildView != null) {
                    if (mChildView!!.translationY >= mHeaderHeight) {
                        mUpBackAnimator!!.start()
                        mHeader!!.releaseDrag()
                        mIsRefreshing = true
                        if (onCircleRefreshListener != null) {
                            onCircleRefreshListener!!.refreshing()
                        }
                    } else {
                        val height = mChildView!!.translationY
                        val backTopAni = ValueAnimator.ofFloat(height, 0f)
                        backTopAni.addUpdateListener { animation ->
                            var `val` = animation.animatedValue as Float
                            `val` =
                                decelerateInterpolator.getInterpolation(`val` / mHeaderHeight) * `val`
                            if (mChildView != null) {
                                mChildView!!.translationY = `val`
                            }
                            mHeader!!.layoutParams.height = `val`.toInt()
                            mHeader!!.requestLayout()
                        }
                        backTopAni.duration = (height * BACK_TOP_DUR / mHeaderHeight).toLong()
                        backTopAni.start()
                    }
                }
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    fun finishRefreshing() {
        if (onCircleRefreshListener != null) {
            onCircleRefreshListener!!.completeRefresh()
        }
        mIsRefreshing = false
        mHeader!!.setRefreshing(false)
    }

    private var onCircleRefreshListener: OnCircleRefreshListener? = null
    fun setOnRefreshListener(onCircleRefreshListener: OnCircleRefreshListener?) {
        this.onCircleRefreshListener = onCircleRefreshListener
    }

    interface OnCircleRefreshListener {
        fun completeRefresh()
        fun refreshing()
    }

    companion object {
        private const val TAG = "pullToRefresh"
        private const val BACK_TOP_DUR: Long = 600
        private const val REL_DRAG_DUR: Long = 200
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}