package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.MainCategory
import com.aguna.app.model.Offer
import com.aguna.app.model.SubCategory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.main_category_item.*
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.ListViewHolder>() {
    private val list = ArrayList<MainCategory>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<MainCategory>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.main_category_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        var drop = false

        val adapter = SubCategoryAdapter()
        adapter.setData(data.subCategory)
        holder.rvMainCategory.setHasFixedSize(true)
        holder.rvMainCategory.layoutManager = LinearLayoutManager(holder.rvMainCategory.context)
        holder.rvMainCategory.adapter = adapter

        adapter.setOnItemClickCallback(
                object : SubCategoryAdapter.OnItemClickCallback{
                    override fun onItemClicked(subCategory: SubCategory) {
                        onItemClickCallback?.onItemClicked(subCategory)
                    }

                })

        holder.txtMainCategory.text = data.title



        Glide.with(holder.itemView.context)
                .load(data.image)
                .apply(RequestOptions().override(300, 125))
                .into(holder.imgMainCategory)

        holder.btnDropDown.setOnClickListener {
            drop = !drop
            if (drop){
                holder.rvMainCategory.visibility = View.VISIBLE
                holder.btnDropDown.setBackgroundResource(R.drawable.ic_expand_less_32)
            }
            else {
                holder.rvMainCategory.visibility = View.GONE
                holder.btnDropDown.setBackgroundResource(R.drawable.ic_expand_more_32)
            }
        }
    }



    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMainCategory: TextView = itemView.findViewById(R.id.txt_main_category)
        val imgMainCategory: ImageView = itemView.findViewById(R.id.img_main_category)
        val btnDropDown : ImageButton = itemView.findViewById(R.id.btn_drop_down_main)
        val rvMainCategory : RecyclerView = itemView.findViewById(R.id.rv_main_category)

    }

    interface OnItemClickCallback {
        fun onItemClicked(subCategory: SubCategory)
    }

}