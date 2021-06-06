package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.ItemCategory
import com.aguna.app.model.SubCategory


class ItemsCategoryAdapter : RecyclerView.Adapter<ItemsCategoryAdapter.ListViewHolder>() {
    private val list = ArrayList<ItemCategory>()
    private var onItemChangeCallback: OnItemChangeCallback? = null

    fun setOnItemChangeCallback(onItemChangeCallback: OnItemChangeCallback) {
        this.onItemChangeCallback = onItemChangeCallback
    }

    fun setData(items: ArrayList<ItemCategory>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.detail_category_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        val itemCategory = ItemCategory()
        holder.txtName.text = data.name
        holder.txtPrice.text = "Rp ${data.price}/kg"
        holder.txtTotalPrice.text = "Rp ${data.price*data.number}"
        holder.editNumber.setText(data.number.toString())

        holder.editNumber.addTextChangedListener {
            var number = data.number
            if (it.toString().isNotEmpty()) number = it.toString().toInt()
            holder.txtTotalPrice.text = "Rp ${data.price*number}"
            itemCategory.name = data.name
            itemCategory.number = number
            itemCategory.price = data.price
            itemCategory.priceTotal = data.price*number
            onItemChangeCallback?.onItemChange(itemCategory)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name_item)
        val txtPrice : TextView = itemView.findViewById(R.id.txt_price_item)
        val txtTotalPrice: TextView = itemView.findViewById(R.id.txt_total_price_item)
        val editNumber : EditText = itemView.findViewById(R.id.edit_number_item)

    }

    interface OnItemChangeCallback {
        fun onItemChange(itemCategory: ItemCategory)
    }

}