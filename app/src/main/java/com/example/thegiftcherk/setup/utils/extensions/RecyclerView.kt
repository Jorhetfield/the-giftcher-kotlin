package com.example.thegiftcherk.setup.utils.extensions

import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.linearSnap() = LinearSnapHelper().attachToRecyclerView(this)
fun RecyclerView.pageSnap() = this.post {
    if (this.onFlingListener == null) PagerSnapHelper().attachToRecyclerView(this)
}

fun RecyclerView.snapCenterWithMargin(left: Int, top: Int, right: Int, bottom: Int) {
    val offsetItemDecoration = SnapCenterWithMargin(left, top, right, bottom)
    this.addItemDecoration(offsetItemDecoration)
}

fun RecyclerView.itemMarginVertical(left: Int, top: Int, right: Int, bottom: Int) =
    this.addItemDecoration(MarginVertical(left, top, right, bottom))

fun RecyclerView.itemMarginHorizontal(left: Int, top: Int, right: Int, bottom: Int) =
    this.addItemDecoration(MarginHorizontal(left, top, right, bottom))

private class SnapCenterWithMargin(
    private val left: Int,
    private val top: Int,
    private val right: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val position = parent.getChildAdapterPosition(view)
        val firstItem = 0
        val lastItem = state.itemCount - 1
        when (position) {
            firstItem -> {
                val firstPadding = (parent.width) / 2 - view.measuredWidth / 2
                outRect.set(firstPadding, 0, 0, 0)
            }
            lastItem -> {
                val lastPadding = (parent.width) / 2 - view.measuredWidth / 2
                outRect.set(0, 0, lastPadding, 0)
            }
            else -> {
                outRect.set(left, top, right, bottom)
            }
        }
    }

}

private class MarginVertical(
    private val left: Int,
    private val top: Int,
    private val right: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            left = this@MarginVertical.left
            if (parent.getChildAdapterPosition(view) == 0) top = this@MarginVertical.top
            right = this@MarginVertical.right
            bottom = this@MarginVertical.bottom

        }
    }
}

private class MarginHorizontal(
    private val left: Int,
    private val top: Int,
    private val right: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            left = this@MarginHorizontal.left
            top = this@MarginHorizontal.top
            right = this@MarginHorizontal.right
            bottom = this@MarginHorizontal.bottom

        }
    }
}