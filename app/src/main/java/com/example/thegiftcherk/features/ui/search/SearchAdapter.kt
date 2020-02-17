package com.example.thegiftcherk.features.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.inflate


class SearchAdapter(
    private val items: MutableList<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<SearchHolder>() {
    override fun onBindViewHolder(
        filterHolder: SearchHolder,
        position: Int
    ) {
        items[position].apply { filterHolder.bind(this) { listener(it) } }
    }

    //region Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val inflatedView = parent.inflate(R.layout.item_row, false)

        return SearchHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size
    //endregion
}
