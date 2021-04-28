package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.ItemCategory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.io.File

import java.io.IOException


class PhotosCategoryAdapter : RecyclerView.Adapter<PhotosCategoryAdapter.ListViewHolder>() {
    private val list = ArrayList<ItemCategory>()
    private var onItemAddPhotoClickCallback: OnItemAddPhotoClickCallback? = null
    private var context: Context? = null

    fun MyAdapter(context: Context?) {
        this.context = context
    }
    fun setOnItemClickCallback(onItemAddPhotoClickCallback: OnItemAddPhotoClickCallback) {
        this.onItemAddPhotoClickCallback = onItemAddPhotoClickCallback
    }

    fun setData(items: ArrayList<ItemCategory>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.photo_category_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        holder.txtName.text = data.name
        holder.btnAddPhoto.setOnClickListener {
            onItemAddPhotoClickCallback?.onItemClicked(data)
        }

        if (data.imgNumber >= 1){
            Log.d("tes", File(data.img1?.path!!).toString())
            Glide.with(holder.img1.context)
                .load(data.img1)
                .override(100, 100)
                .transform(RoundedCorners(10))
                .into(holder.img1)


        }
        if (data.imgNumber >= 2){

            Glide.with(holder.img2.context)
                .load(data.img2)
                .override(100, 100)
                .transform(RoundedCorners(10))
                .into(holder.img2)
        }

        if (data.imgNumber >= 3){

            Glide.with(holder.img3.context)
                .load(data.img3)
                .override(100, 100)
                .transform(RoundedCorners(10))
                .into(holder.img3)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name_item_photo)
        val btnAddPhoto : Button = itemView.findViewById(R.id.btn_add_photo)
        val img1 : ImageView = itemView.findViewById(R.id.img1_item)
        val img2 : ImageView = itemView.findViewById(R.id.img2_item)
        val img3 : ImageView = itemView.findViewById(R.id.img3_item)

    }

    interface OnItemAddPhotoClickCallback {
        fun onItemClicked(itemCategory: ItemCategory)
    }


}