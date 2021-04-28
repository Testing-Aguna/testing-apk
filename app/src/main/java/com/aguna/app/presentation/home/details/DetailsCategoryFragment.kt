package com.aguna.app.presentation.home.details

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguna.app.R
import com.aguna.app.adapter.ItemsCategoryAdapter
import com.aguna.app.adapter.PhotosCategoryAdapter
import com.aguna.app.adapter.SpinnerAdapter
import com.aguna.app.firebase.Firebase
import com.aguna.app.model.ItemCategory
import com.aguna.app.model.Transaction
import kotlinx.android.synthetic.main.fragment_details_category.*

class DetailsCategoryFragment : Fragment(), View.OnClickListener {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var currentItem : ItemCategory? = null
    private var bitmap : Bitmap? = null
    private var total : Int = 0
    private var transaction : Transaction = Transaction()
    var listPayMethod = arrayListOf(
        "Cash", "Go pay"
    )
    private var itemsCategoryAdapter : ItemsCategoryAdapter = ItemsCategoryAdapter()
    private var photosCategoryAdapter : PhotosCategoryAdapter = PhotosCategoryAdapter()
    private var spinnerAdapter : SpinnerAdapter = SpinnerAdapter()
    private var items : ArrayList<ItemCategory>? = ArrayList()

    private val detailsCategoryViewModel by lazy {
        ViewModelProvider(this).get(DetailsCategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = DetailsCategoryFragmentArgs.fromBundle(it).listItem?.toCollection(ArrayList())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_spinner.setOnClickListener(this)
        btn_add_trash.setOnClickListener(this)
        back_btn_details_category.setOnClickListener(this)
        btn_jemput.setOnClickListener(this)
        btn_datang.setOnClickListener(this)
        setRecyclerView()
        updateTotal()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data

            items?.forEach {
                if (it.name == currentItem?.name){
                    if (it.imgNumber == 0){
                        it.img1 = filePath
                        it.imgNumber++
                    }
                    else if (it.imgNumber == 1){
                        it.img2 = filePath
                        it.imgNumber++
                    }
                    else if (it.imgNumber == 2){
                        it.img3 = filePath
                        it.imgNumber++

                    }
                    else if (it.imgNumber == 3){
                        Toast.makeText(context, "Full", Toast.LENGTH_SHORT).show()
                    }
                    setRecyclerView()
                }
            }


        }
    }

    private fun setRecyclerView(){
        itemsCategoryAdapter.setData(items!!)
        rv_edit_detail.setHasFixedSize(true)
        rv_edit_detail.layoutManager = LinearLayoutManager(context)
        rv_edit_detail.adapter = itemsCategoryAdapter

        itemsCategoryAdapter.setOnItemChangeCallback(
            object : ItemsCategoryAdapter.OnItemChangeCallback{
                override fun onItemChange(itemCategory: ItemCategory) {
                    items?.forEach {
                        if (it.name == itemCategory.name) {
                            it.number = itemCategory.number
                            it.priceTotal = itemCategory.priceTotal
                        }
                    }
                    updateTotal()
                }
            }
        )

        photosCategoryAdapter.setData(items!!)
        rv_photo_detail.setHasFixedSize(true)
        rv_photo_detail.layoutManager = LinearLayoutManager(context)
        rv_photo_detail.adapter = photosCategoryAdapter

        photosCategoryAdapter.setOnItemClickCallback(
            object : PhotosCategoryAdapter.OnItemAddPhotoClickCallback{
                override fun onItemClicked(itemCategory: ItemCategory) {
                    currentItem = itemCategory
                    chooseImage()
                }
            })

        spinnerAdapter.setData(listPayMethod)
        rv_spinner.setHasFixedSize(true)
        rv_spinner.layoutManager = LinearLayoutManager(context)
        rv_spinner.adapter = spinnerAdapter

        spinnerAdapter.setOnItemClickCallback(
            object : SpinnerAdapter.OnItemClickCallback{
                override fun onItemClicked(string: String) {
                    rv_spinner.visibility = View.GONE
                    spinner_pay_method.visibility = View.VISIBLE
                    txt_spinner.text = string
                }

            }
        )


    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."
                ),
                PICK_IMAGE_REQUEST
        )
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_spinner -> {
                rv_spinner.bringToFront()
                rv_spinner.visibility = View.VISIBLE
            }
            R.id.btn_add_trash -> {
                val action =
                    DetailsCategoryFragmentDirections.actionDetailsCategoryFragmentToCategoryFragment(
                        items?.toTypedArray()
                    )
                findNavController().navigate(action)
            }
            R.id.back_btn_details_category -> findNavController().popBackStack()
            R.id.btn_jemput -> uploadData("jemput")
            R.id.btn_datang -> uploadData("datang")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTotal(){
        total = 0
        items?.forEach {
            total += it.priceTotal
        }
        txt_total_details.text = "Rp $total"
    }

    private fun uploadData (type : String){
        transaction.userId = Firebase.userId
        transaction.items = items ?: ArrayList()
        transaction.totalPrice = total
        transaction.type = type
        detailsCategoryViewModel.addTransaction(transaction)
        uploadPhoto()
        findNavController().navigate(R.id.action_detailsCategoryFragment_to_mainFragment)
    }

    private fun uploadPhoto(){
        detailsCategoryViewModel.uploadPhoto(transaction)
    }
}