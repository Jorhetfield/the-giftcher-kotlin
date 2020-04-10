package com.example.thegiftcherk.setup.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

data class BaseModel<T>(val type:Int, val model:T)

//region Adapter Cell Types
enum class AdapterCellType(val typeCell: Int) {
    ADAPTER_TYPE_OFFER(0),
    ADAPTER_TYPE_OTHER_SERVICE(1),
    ADAPTER_TYPE_INVOICE(2),
    ADAPTER_TYPE_BOOKING(3)
}
//endregion

class BaseAdapter(
    private val items: MutableList<BaseModel<Any>>,
    private val listener: OnItemListDelegate? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //region Vars
    //endregion

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
//            AdapterCellType.ADAPTER_TYPE_OFFER.typeCell -> {
//                parent.inflate(R.layout.cell_available_car, false).apply {
//                    return OfferHolder(this)
//                }
//            }
//            AdapterCellType.ADAPTER_TYPE_OTHER_SERVICE.typeCell -> {
//                parent.inflate(R.layout.cell_other_service, false).apply {
//                    return OtherServiceHolder(this)
//                }
//            }
//            AdapterCellType.ADAPTER_TYPE_INVOICE.typeCell -> {
//                parent.inflate(R.layout.cell_invoice, false).apply {
//                    return InvoiceHolder(this)
//                }
//            }
//            AdapterCellType.ADAPTER_TYPE_BOOKING.typeCell -> {
//                parent.inflate(R.layout.cell_booking, false).apply {
//                    return BookingHolder(this)
//                }
//            }
        }

        return BaseHolder(parent) ///"Default"
    }

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when(holder) {
//            is OfferHolder -> holder.bind(lista_personal[position].model as OfferResponse) {
//                //holder.onClick(position, lista_personal)
//                listener?.onItemClicked(it)
//            }
//            is OtherServiceHolder -> holder.bind(lista_personal[position].model as ServiceResponse) {
//                holder.onClick()
//                listener?.onItemClicked(it)
//            }
//            is InvoiceHolder -> holder.bind(lista_personal[position].model as InvoiceResponse) {
//                listener?.onItemClicked(it)
//            }
//            is BookingHolder -> holder.bind(lista_personal[position].model as BookingResponse) {
//                listener?.onItemClicked(it)
//            }
//            //is ...
//        }
    }
    //endregion

    //region Methods
    //endregion

    interface OnItemListDelegate {
        fun onItemClicked(item: Any)
    }

    open class BaseHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bind() {
            Log.d(LOGTAG, "BaseAdapter")
        }
    }

    companion object {
        private val LOGTAG: String = BaseAdapter::class.java.simpleName
    }
}