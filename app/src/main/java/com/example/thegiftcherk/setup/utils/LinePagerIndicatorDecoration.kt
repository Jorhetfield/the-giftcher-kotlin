package com.example.thegiftcherk.setup.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LinePagerIndicatorDecoration(private val colorActive: Int = -0x1, private val colorInactive: Int = 0x66FFFFFF) : RecyclerView.ItemDecoration() {

    private val dp = Resources.getSystem().displayMetrics.density
    private val mIndicatorHeight = (dp * 16).toInt()
    private val mIndicatorStrokeWidth = dp * 2
    private val mIndicatorItemLength = dp * 16
    private val mIndicatorItemPadding = dp * 4
    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter?.itemCount

        if (itemCount != null && itemCount > 1) {
            // center horizontally, calculate width and subtract half from center
            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems
            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

            // center vertically in the allotted space
            val indicatorPosY = parent.height - mIndicatorHeight / 2f

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)


            // find active page (which should be highlighted)
            val layoutManager = parent.layoutManager as LinearLayoutManager?
            val activePosition = layoutManager?.findFirstVisibleItemPosition()?: RecyclerView.NO_POSITION
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }

            // find offset of active page (if the user is scrolling)
            val activeChild = layoutManager?.findViewByPosition(activePosition)
            val left = activeChild?.left
            val width = activeChild?.width

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            if (left != null && width!= null) {
                val progress = mInterpolator.getInterpolation(left.times(-1) / width.toFloat())
                drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
            }
        }
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        mPaint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int
    ) {
        mPaint.color = colorActive

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawLine(
                highlightStart, indicatorPosY,
                highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
            )
        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorItemLength * progress

            // draw the cut off highlight
            c.drawLine(
                highlightStart + partialLength, indicatorPosY,
                highlightStart + mIndicatorItemLength, indicatorPosY, mPaint
            )

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawLine(
                    highlightStart, indicatorPosY,
                    highlightStart + partialLength, indicatorPosY, mPaint
                )
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }
}