package com.aguna.app.presentation.home.category

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguna.app.R
import com.aguna.app.adapter.MainCategoryAdapter
import com.aguna.app.model.*
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), View.OnClickListener{
    var mainCategoryAdapter = MainCategoryAdapter()
    var items : ArrayList<ItemCategory> = ArrayList()
    var trashDetail : String = "-"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            items = CategoryFragmentArgs.fromBundle(it).listItem?.toCollection(ArrayList()) ?: ArrayList()
            updateBottom()
        }

        btn_sell_trash_category.setOnClickListener(this)
        back_btn_category.setOnClickListener(this)
        setRecyclerView()
        updateBottom()
    }
    fun setRecyclerView(){
        val list = ArrayList<MainCategory>()
        list.add(
                MainCategory("", "Organik", arrayListOf(
                        SubCategory("minyak_jelantah","Minyak Jelantah", 700),
                        SubCategory("sampah_dapur","Sampah Dapur", 1000))
                )
        )
        list.add(
                MainCategory("", "Anorganik", arrayListOf(
                        SubCategory("plastik","Plastik",0, arrayListOf(
                                SubCategory("botol_pet","Botol PET", 700),
                                SubCategory("gelas_platik","Gelas Plastik", 700),
                                SubCategory("plastik_warna","Platik Warna", 100))
                        ),
                        SubCategory("kertas","Kertas",800),
                        SubCategory("logam", "Logam",700),
                        SubCategory("botol_kaca","Botol Kaca",600),
                        SubCategory("botol_bekas","Botol Bekas", 400)
                )
                )
        )
        mainCategoryAdapter.setData(list)
        rv_category.setHasFixedSize(true)
        rv_category.layoutManager = LinearLayoutManager(context)
        rv_category.adapter = mainCategoryAdapter

        mainCategoryAdapter.setOnItemClickCallback(
                object : MainCategoryAdapter.OnItemClickCallback{
                    override fun onItemClicked(subCategory: SubCategory) {
                        val mItems = ItemCategory(subCategory.id, subCategory.title!!, subCategory.price!!)
                        if (!items.contains(mItems)){
                            items.add(mItems)
                        }
                        updateBottom()
                    }
                }
        )

    }

    @SuppressLint("SetTextI18n")
    private fun updateBottom() {
        txt_trash_other_detail.visibility = View.GONE
        if (items.isNotEmpty()){
            trashDetail = items[0].name
            if (items.size == 2) {
                trashDetail = "${items[0].name} \n${items[1].name}"
            }
            else if (items.size > 2) {
                trashDetail = "${items[0].name} \n${items[1].name}"
                txt_trash_other_detail.visibility = View.VISIBLE
                txt_trash_other_detail.text = "${items.size - 2} Lainnya"
            }
        }
        txt_trash_detail.text = trashDetail
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_sell_trash_category -> {
                val action =
                    CategoryFragmentDirections.actionCategoryFragmentToDetailsCategoryFragment(items.toTypedArray())
                findNavController().navigate(action)
            }
            R.id.back_btn_category -> findNavController().popBackStack()
        }
    }


}