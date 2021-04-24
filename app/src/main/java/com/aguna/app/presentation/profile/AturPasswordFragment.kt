package com.aguna.app.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.aguna.app.R
import com.aguna.app.presentation.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_atur_password.*
import kotlinx.android.synthetic.main.fragment_atur_password.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*
import kotlinx.android.synthetic.main.fragment_profile.*

class AturPasswordFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_atur_password, container, false)

        view.imageView13.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_aturPasswordFragment_to_profileFragment) }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        textView24.setOnClickListener{
            changepass()
        }

    }

    private fun changepass(){

        if(editTextNamaLengkap.text.isNotEmpty() &&
                editTextNamaLengkap2.text.isNotEmpty() &&
                editTextNamaLengkap3.text.isNotEmpty()
        ) {
            if (editTextNamaLengkap2.text.toString().equals(editTextNamaLengkap3.text.toString())){
                val user:FirebaseUser = auth.currentUser
                if (user != null && user.email != null){
                    val credential:AuthCredential = EmailAuthProvider.getCredential(user.email, editTextNamaLengkap.text.toString())

                    user?.reauthenticate(credential)
                            ?.addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(requireActivity(), "Re-authenticate Success.", Toast.LENGTH_LONG).show()
                                    user?.updatePassword(editTextNamaLengkap2.text.toString())
                                            ?.addOnCompleteListener { task ->
                                                if(task.isSuccessful){
                                                    Toast.makeText(requireActivity(), "Password Changed Successfully.", Toast.LENGTH_LONG).show()
                                                    auth.signOut()
                                                    val i = Intent(activity,
                                                            MainActivity::class.java)
                                                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                    startActivity(i)
                                                }
                                            }
                                } else{
                                    Toast.makeText(requireActivity(), "Re-authenticate Failed.", Toast.LENGTH_LONG).show()
                                }
                            }
                }else{
                    val i = Intent(activity,
                            MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            } else{
                Toast.makeText(requireActivity(), "Password Mismatch.", Toast.LENGTH_LONG).show()

            }

        } else{
            Toast.makeText(requireActivity(), "Please enter all the fields.", Toast.LENGTH_LONG).show()
        }
    }


}