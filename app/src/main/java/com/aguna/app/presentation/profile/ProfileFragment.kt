package com.aguna.app.presentation.profile

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.*

class ProfileFragment : Fragment() {

    private lateinit var auth  : FirebaseAuth

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

//    Logout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        textView16.setOnClickListener{
            auth.signOut()
            val i = Intent(activity,
                    MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }
}
