package com.example.thegiftcherk.features.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.inflate


class HomeAdapter(
    private val items: MutableList<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<HomeHolder>() {
    override fun onBindViewHolder(
        filterHolder: HomeHolder,
        position: Int
    ) {
        items[position].apply { filterHolder.bind(this) { listener(it) } }
    }

    //region Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val inflatedView = parent.inflate(R.layout.item_home_row, false)

        return HomeHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size
    //endregion
}
