package com.demo.popcornapp.shared

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerSpaceItemDivider(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (shouldDecorate(parent.getChildAdapterPosition(view), parent)) {
            outRect.bottom = space
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }

    private fun shouldDecorate(childPosition: Int, recyclerView: RecyclerView): Boolean = childPosition < (recyclerView.adapter?.itemCount ?: 0) - 1
}