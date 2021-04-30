package com.aguna.app.presentation.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.aguna.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_data_profile.*
import kotlinx.android.synthetic.main.fragment_data_profile.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*

class DataProfile : Fragment() {

    private lateinit var auth  : FirebaseAuth
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_data_profile, container, false)

        view.imageView13.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_dataProfile_to_profileFragment) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        textView24.setOnClickListener {
            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse("@drawable/profile_avatar")
                else -> user.photoUrl
            }

            val name = editTextNamaLengkap.text.toString().trim()
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }


    }
}