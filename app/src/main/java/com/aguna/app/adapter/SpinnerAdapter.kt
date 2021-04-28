package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.Offer
import com.aguna.app.model.SubCategory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.collections.ArrayList

class SpinnerAdapter : RecyclerView.Adapter<SpinnerAdapter.ListViewHolder>() {
    private val list = ArrayList<String>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<String>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.spinner_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]

        holder.txtDesc.text = data

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(data)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDesc: TextView = itemView.findViewById(R.id.desc_offer)

    }

    interface OnItemClickCallback {
        fun onItemClicked(string: String)
    }


}