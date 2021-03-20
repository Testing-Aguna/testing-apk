package com.aguna.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aguna.app.R
import com.aguna.app.model.introslide

class introslider_adapter (private val introslide: List<introslide>)
    : RecyclerView.Adapter<introslider_adapter.IntroSlideViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.splash_slider_container,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introslide[position])

    }

    override fun getItemCount(): Int {
        return introslide.size

    }

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageslideIcon)

        fun bind(introslide: introslide){
            textTitle.text = introslide.title
            textDescription.text = introslide.description
            imageIcon.setImageResource(introslide.icon)
        }
    }
}