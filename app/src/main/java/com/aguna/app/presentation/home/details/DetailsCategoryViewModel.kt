package com.aguna.app.presentation.home.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.aguna.app.firebase.Firebase
import com.aguna.app.model.Transaction
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsCategoryViewModel : ViewModel() {

    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    init {
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = firebaseStorage!!.reference
    }

    fun addTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            val newDocRef: DocumentReference =
                    Firebase.db.collection(Firebase.TRANSACTION).document()

            val gson = Gson()
            val strItems = gson.toJson(transaction.items)
            val data = hashMapOf(
                    Firebase.ID to newDocRef.id,
                    Firebase.USER_ID to transaction.userId,
                    Firebase.ITEMS to strItems,
                    Firebase.TOTAL_PRICE to transaction.totalPrice,
                    Firebase.TYPE to transaction.type,
                    Firebase.TIMESTAMP to  FieldValue.serverTimestamp()

            )

            newDocRef.set(data)
                    .addOnSuccessListener {
                        Log.d(Firebase.TAG, "succeed")
                    }
                    .addOnFailureListener { Log.d(Firebase.TAG, "Fail", it) }

        }
    }

    fun uploadPhoto(transaction : Transaction) {

        Log.d("ffff", "photo")
        transaction.items.forEach { itemCategory ->
            if (itemCategory.number >= 1){
                val ref = storageReference?.child("transaction/${Firebase.userId}/${itemCategory.id}/1")
                ref?.putFile(itemCategory.img1!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                    Log.d(Firebase.TAG, "succeed")
                })?.addOnFailureListener(OnFailureListener { e ->
                    Log.d(Firebase.TAG, "Fail", e)
                })
            }
            if (itemCategory.number >=2 ){
                val ref = storageReference?.child("transaction/${Firebase.userId}/${itemCategory.id}/2")
                ref?.putFile(itemCategory.img1!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                    Log.d(Firebase.TAG, "succeed")
                })?.addOnFailureListener(OnFailureListener { e ->
                    Log.d(Firebase.TAG, "Fail", e)
                })
            }
            if (itemCategory.number >= 3 ){
                val ref = storageReference?.child("transaction/${Firebase.userId}/${itemCategory.id}/3")
                ref?.putFile(itemCategory.img1!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                    Log.d(Firebase.TAG, "succeed")
                })?.addOnFailureListener(OnFailureListener { e ->
                    Log.d(Firebase.TAG, "Fail", e)
                })
            }
        }


    }

}