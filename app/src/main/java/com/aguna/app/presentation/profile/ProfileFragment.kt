package com.aguna.app.presentation.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aguna.app.R
import com.aguna.app.presentation.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    companion object{
        const val REQUEST_CAMERA = 100
    }

    private lateinit var auth  : FirebaseAuth
    private lateinit var imageUri: Uri
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     val view = inflater.inflate(R.layout.fragment_profile, container, false)

     view.textView6.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_dataProfile) }
     view.textView9.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_aturPasswordFragment) }
     view.textView12.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_faqFragment) }
     view.textView13.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_bantuanFragment) }
     view.textView14.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_tentangFragment) }


     return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null){
            if (user.photoUrl != null){
                Picasso.get().load(user.photoUrl).into(imageView2)
            } else{
                Picasso.get().load("@drawable/profile_avatar").into(imageView2)
            }
            textView2.setText(user.displayName)
            textView3.setText(user.email)

            if (user.displayName != null){
                textView2.setText(user.displayName)
            } else{
                textView2.setText("Aguners")
            }
        }

        //    Logout
        textView16.setOnClickListener{
            auth.signOut()
            val i = Intent(activity,
                    MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        imageView2.setOnClickListener {
            intentcamera()
        }

    }

    private fun intentcamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also{
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("avatar/${FirebaseAuth.getInstance().currentUser?.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            imageUri= it
                            imageView2.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

}
