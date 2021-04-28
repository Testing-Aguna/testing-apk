package com.aguna.app.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.firebase.Firebase
import com.aguna.app.model.Offer
import com.aguna.app.utils.GlideApp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.ListViewHolder>() {
    private val list = ArrayList<Offer>()

    fun setData(items: ArrayList<Offer>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.offer_item, viewGroup, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]

        var firebaseStorage: FirebaseStorage? = null
        var storageReference: StorageReference? = null

        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage.reference
        val refPhotoOffer = storageReference.child("${Firebase.OFFERS}/${data.id}.png")

        holder.txtDesc.text = data.desc
        Log.d("tes", data.imgUrl!!)

        GlideApp.with(holder.itemView.context)
            .load(refPhotoOffer)
            .transform(RoundedCorners(10))
            .into(holder.imgUser)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDesc: TextView = itemView.findViewById(R.id.desc_offer)
        val imgUser: ImageView = itemView.findViewById(R.id.img_offer)

    }



}