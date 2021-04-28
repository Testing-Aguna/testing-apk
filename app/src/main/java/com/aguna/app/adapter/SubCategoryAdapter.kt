package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.SubCategory


class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.ListViewHolder>() {
    private val list = ArrayList<SubCategory>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<SubCategory>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.sub_category_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        holder.txtSubCategory.text = data.title
        holder.txtPriceSubCategory.text = "Rp ${data.price}/kg"

        var isItem = false
        var drop = false

        val adapter = SubCategoryAdapter()
        adapter.setData(data.items)
        holder.rvSubCategory.setHasFixedSize(true)
        holder.rvSubCategory.layoutManager = LinearLayoutManager(holder.rvSubCategory.context)
        holder.rvSubCategory.adapter = adapter

        if (data.items.isEmpty()){
            holder.btnDropDown.setBackgroundResource(R.drawable.ic_add_item)
            isItem = true
        }
        else if (data.items.size > 1){
            val min : SubCategory? = data.items.minByOrNull {
                it.price!!
            }
            val max : SubCategory? = data.items.maxByOrNull {
                it.price!!
            }
            holder.txtPriceSubCategory.text = "Rp${min?.price}-Rp${max?.price}/kg"
        }
        val fontMontserratSemiBold = ResourcesCompat.getFont(holder.txtSubCategory.context, R.font.montserrat_semibold)
        val fontMontserratMedium = ResourcesCompat.getFont(holder.txtSubCategory.context, R.font.montserrat_medium)

        holder.txtSubCategory.typeface = if (isItem) fontMontserratMedium else fontMontserratSemiBold


        adapter.setOnItemClickCallback(
                object : SubCategoryAdapter.OnItemClickCallback {
                    override fun onItemClicked(subCategory: SubCategory) {
                        onItemClickCallback?.onItemClicked(subCategory)
                    }

                })

        holder.btnDropDown.setOnClickListener {
            if (isItem) onItemClickCallback?.onItemClicked(data)
            else {
                drop = !drop
                if (drop){
                    holder.rvSubCategory.visibility = View.VISIBLE
                    holder.btnDropDown.setBackgroundResource(R.drawable.ic_expand_less_32)
                }
                else {
                    holder.rvSubCategory.visibility = View.GONE
                    holder.btnDropDown.setBackgroundResource(R.drawable.ic_expand_more_32)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtSubCategory: TextView = itemView.findViewById(R.id.txt_sub_category)
        val txtPriceSubCategory : TextView = itemView.findViewById(R.id.txt_sub_category_price)
        val btnDropDown: ImageButton = itemView.findViewById(R.id.btn_drop_down_sub)
        val rvSubCategory : RecyclerView = itemView.findViewById(R.id.rv_sub_category)


    }

    interface OnItemClickCallback {
        fun onItemClicked(subCategory: SubCategory)
    }


}