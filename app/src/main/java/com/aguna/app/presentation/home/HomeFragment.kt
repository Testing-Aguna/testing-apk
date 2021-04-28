package com.aguna.app.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguna.app.R
import com.aguna.app.adapter.NewsAdapter
import com.aguna.app.adapter.OfferAdapter
import com.aguna.app.model.News
import com.aguna.app.model.Offer
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {
    var newsAdapter= NewsAdapter()
    var offersAdapter= OfferAdapter()

    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sell_trash.setOnClickListener(this)
        showLoading(true)
        setRecyclerView()
    }

    fun setRecyclerView(){

        rv_news.setHasFixedSize(true)
        rv_news.layoutManager = LinearLayoutManager(context)
        rv_news.adapter = newsAdapter
        homeViewModel.getNews().observe(viewLifecycleOwner, Observer { list ->
            var arr = ArrayList<News>()
            arr.clear()

            list.forEach {
                arr.add(it)
            }
            showLoading(false)
            newsAdapter.setData(arr)
        })

        rv_offer.setHasFixedSize(true)
        rv_offer.layoutManager = LinearLayoutManager(context)
        rv_offer.adapter = offersAdapter
        homeViewModel.getOffers().observe(viewLifecycleOwner, Observer { list ->
            var arr = ArrayList<Offer>()
            arr.clear()

            list.forEach {
                arr.add(it)
            }

            showLoading(false)
            offersAdapter.setData(arr)
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_sell_trash -> findNavController().navigate(R.id.action_mainFragment_to_categoryFragment)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state)
            home_progress_bar.visibility = View.VISIBLE
        else
            home_progress_bar.visibility = View.GONE
    }

}