package com.wk.test.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.wk.projects.common.constant.WkSuppressConstants
import com.wk.projects.common.constant.WkSuppressConstants.CLICKABLE_VIEW_ACCESSIBILITY
import com.wk.projects.common.helper.MotionEventUtil
import com.wk.projects.common.helper.PhysicalUtil
import com.wk.projects.common.log.WkLogUtil

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/8/25<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
@Suppress(WkSuppressConstants.UNUSED)
class TestTouchViewGroup : FrameLayout {

    companion object {
        private const val DISPATCH_TOUCH_EVENT = "dispatch :  "
        private const val ON_TOUCH_EVENT = "ON_TOUCH_EVENT: "
    }
    private val MOVE_RANG by lazy {
        width/4.0f
    }
    private val paint by lazy {
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textSize = 40f
        paint
    }

    private val columnString by lazy {
        listOf(true, false, "super")
    }

    private val columnBg by lazy {
        listOf(
                Color.WHITE,
                Color.GREEN,
                Color.RED
        )
    }
    private val textPaint by lazy {
        val textPaint = TextPaint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 40f
        textPaint
    }

    private var downPositionX = 0f
    private var downPositionY = 0f
    private var canMoveView = false

    private val columnCount by lazy { columnString.size }

    private val barHeight by lazy { PhysicalUtil.getStatusBarHeight(context) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val heightUnit = height / (columnCount * 1.0f)
        val widthUnit = width / (columnCount * 1.0f)
        for (i in 0 until columnCount) {
            paint.color = columnBg[i]
            canvas?.drawRect(0f, heightUnit * i, width.toFloat(), heightUnit * (i + 1), paint)
            drawText(ON_TOUCH_EVENT + columnString[i].toString(), canvas, width / 2.0f, heightUnit * (i + 1 / 2.0f))
        }
        for (i in 0 until columnCount) {
            drawText(columnString[i].toString(), canvas, widthUnit * i + widthUnit / 2.0f, 0f)
            canvas?.drawLine(widthUnit * i, 0f, widthUnit * i, height.toFloat(), textPaint)
        }
        canvas?.drawRect(right-MOVE_RANG, bottom-MOVE_RANG*2,  right.toFloat(), bottom-MOVE_RANG , textPaint)
    }

    private fun drawText(target: String, canvas: Canvas?, x: Float, y: Float) {
        val targetWidth = textPaint.measureText(target)
        val pf = textPaint.fontMetrics
        canvas?.drawText(target, x - targetWidth / 2.0f, y + Math.abs(pf.ascent - pf.descent) / 2.0f, textPaint)
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //屏幕的x坐标
        val screenX = ev?.rawX ?: -1f
        //屏幕的y坐标
        val screenY = ev?.rawY ?: -1f

        WkLogUtil.d("touchEvent", "$tag ：dispatchTouchEvent  ${MotionEventUtil.toString(ev)}")
        val widthUnit = width / columnCount*1.0
        if (screenX in 0f .. widthUnit.toFloat()) {
            return true
        }
        if (screenX in widthUnit .. widthUnit * 2) {
            return false
        }
        return super.dispatchTouchEvent(ev)
    }

    @Suppress(CLICKABLE_VIEW_ACCESSIBILITY)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //屏幕的x坐标
        val screenX = event?.rawX ?: -1f
        //屏幕的y坐标
        val screenY = event?.rawY ?: -1f
        if (screenX in (right-MOVE_RANG)..right.toFloat() &&
                screenY in bottom-MOVE_RANG*2+barHeight ..bottom-MOVE_RANG+barHeight &&
                event?.action == MotionEvent.ACTION_DOWN) {
            canMoveView = true
            downPositionX = screenX
            downPositionY = screenY
            return true
        }
        if (canMoveView) {
            when (event?.action) {
                MotionEvent.ACTION_MOVE -> {
                    val offsetX = screenX - downPositionX
                    val offsetY = screenY - downPositionY
                    layout(left + offsetX.toInt(), top + offsetY.toInt(),
                            right + offsetX.toInt(), bottom + offsetY.toInt())
                    downPositionX = screenX
                    downPositionY = screenY
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    downPositionX = 0f
                    downPositionY = 0f
                    canMoveView = false
                }
                MotionEvent.ACTION_DOWN -> {
                    downPositionX = screenX
                    downPositionY = screenY
                }
            }
            return true
        }


        WkLogUtil.d("touchEvent", "$tag ：onTouchEvent  ${MotionEventUtil.toString(event)}")
        val eventY = event?.y ?: -1
        val heightUnit = height / columnCount
        if (eventY in 0 until heightUnit) {
            return true
        }
        if (eventY in heightUnit until heightUnit * 2) {
            return false
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }
}