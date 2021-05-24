package com.aguna.app.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguna.app.firebase.Firebase
import com.aguna.app.model.News
import com.aguna.app.model.Offer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val allNews = MutableLiveData<List<News>>()
    private val allOffers = MutableLiveData<List<Offer>>()

    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var firebaseFirestore : FirebaseFirestore? = null

    init {
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage!!.reference
    }

    init {
        allNews.value = emptyList()
        allOffers.value = emptyList()
        this.refreshNotif()
    }

    private fun refreshNotif(){
        getAllNews()
        getAllOffers()
    }

    fun getNews() : LiveData<List<News>> {
        return allNews
    }

    fun getOffers() : LiveData<List<Offer>> {
        return allOffers
    }

    private fun getAllNews(){
        viewModelScope.launch {
            firebaseFirestore?.collection(Firebase.NEWS)
                ?.get()
                ?.addOnSuccessListener {
                    val list = mutableListOf<News>()
                    it.forEach{ document ->
                        val news = News(
                                document[Firebase.ID]?.toString() ?: "",
                                document[Firebase.IMAGE]?.toString() ?: "",
                                getImage(Firebase.NEWS, document[Firebase.IMAGE]?.toString() ?: ""),
                                document[Firebase.DESC]?.toString() ?: ""
                        )
                        list.add(news)
                    }
                    allNews.postValue(list)
                    Log.e("news", "Succeed")
                }
                ?.addOnFailureListener{
                    Log.e("news", "Failed")
                }
        }
    }

    private fun getAllOffers(){
        viewModelScope.launch {
            firebaseFirestore?.collection(Firebase.OFFERS)
                ?.get()
                ?.addOnSuccessListener {
                    val list = mutableListOf<Offer>()
                    it.forEach{ document ->
                        val offer = Offer(
                                document[Firebase.ID]?.toString() ?: "",
                                document[Firebase.IMAGE]?.toString() ?: "",
                                getImage(Firebase.OFFERS, document[Firebase.ID]?.toString() ?: ""),
                                document[Firebase.DESC]?.toString() ?: ""
                        )
                        list.add(offer)
                    }
                    allOffers.postValue(list)
                    Log.e("offers", "Succeed")
                }
                ?.addOnFailureListener{
                    Log.e("offers", "Failed")
                }
        }
    }

    private fun getImage(type: String?, id: String?) : String {
        Log.d("tes", "$type/$id")
        var imgUrl =  ""
        storageReference?.child("$type/$id.png")?.downloadUrl?.addOnSuccessListener {
            imgUrl = it.toString()
            Log.d("image", "Succeed")

        }?.addOnFailureListener {
            Log.d("image", "Failed")

        }

        return imgUrl
    }

}