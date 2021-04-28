package com.aguna.app.firebase

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Firebase {
    companion object{
        const val TAG = "Firebase"

        const val TRANSACTION = "transaction"
        const val ID = "id"
        const val USER_ID = "user_id"
        const val ITEMS = "items"
        const val TOTAL_PRICE = "total_price"
        const val TYPE = "type"
        const val TIMESTAMP = "timestamp"

        const val NEWS = "news"
        const val IMAGE = "image"
        const val DESC = "desc"
        const val OFFERS = "offers"


        @SuppressLint("StaticFieldLeak")
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val emailUser = FirebaseAuth.getInstance().currentUser!!.email
    }
}