package com.aguna.app.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.aguna.app.R
import com.aguna.app.model.introslide
import com.aguna.app.adapter.introslider_adapter
import kotlinx.android.synthetic.main.activity_splash_slider.*

class SliderActivity : AppCompatActivity() {

    lateinit var preference : SharedPreferences
    val pref_show_intro ="Intro"
    private val introsliderAdapter = introslider_adapter(
            listOf(
                    introslide(
                            "Pilah Sampah",
                            "Kumpulkan dan pilah sampah yang ada di sekitarmu untuk dijual",
                            R.drawable.slide1
                    ),
                    introslide(
                            "Posting Sampah",
                            "Posting sampah yang telah kamu pilah ke aplikasi Aguna",
                            R.drawable.slide2
                    ),
                    introslide(
                            "Tunggu Kolektor",
                            "Tunggu kolektor datang untuk mengambil sampahmu",
                            R.drawable.slide3
                    ),
                    introslide(
                            "Dapatkan Uang",
                            "Tukar sampahmu yang telah kamu kumpulkan dan pilah menjadi uang",
                            R.drawable.slide4
                    )
            )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_slider)
        slideview.adapter = introsliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        preference = getSharedPreferences("IntroSlider", Context.MODE_PRIVATE)
        if (!preference.getBoolean(pref_show_intro,true)){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        slideview.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        skipbutton.setOnClickListener{
            if(slideview.currentItem + 1 < introsliderAdapter.itemCount){
                slideview.currentItem +=1
            } else {
                Intent(applicationContext, MainActivity::class.java).also{
                    startActivity(it)
                    val editor = preference.edit()
                    editor.putBoolean(pref_show_intro, false)
                    editor.apply()
                }
            }
        }
        textSkip.setOnClickListener{
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
                val editor = preference.edit()
                editor.putBoolean(pref_show_intro, false)
                editor.apply()
            }
        }
    }


    private fun setupIndicators(){
        val indicators= arrayOfNulls<ImageView>(introsliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(8, 0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_inactive
                        )
                )
                this?.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }
    private fun setCurrentIndicator(index: Int){
        val childCount = indicatorContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorContainer[i] as ImageView
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_active
                        )
                )
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_inactive)
                )
            }
        }
    }
}