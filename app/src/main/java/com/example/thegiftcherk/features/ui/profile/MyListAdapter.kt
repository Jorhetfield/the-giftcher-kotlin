package com.example.thegiftcherk.features.ui.profile

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.example.thegiftcherk.setup.utils.extensions.inflate

class MyListAdapter(
    private val items: MutableList<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<MyListHolder>() {
    override fun onBindViewHolder(
        filterHolder: MyListHolder,
        position: Int
    ) {
        items[position].apply { filterHolder.bind(this) { listener(it) } }
    }

    //region Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
        val inflatedView = parent.inflate(R.layout.item_my_list_row, false)

        return MyListHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size
    //endregion
}
