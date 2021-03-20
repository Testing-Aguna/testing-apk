package com.aguna.app.presentation.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aguna.app.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.regex.Pattern

class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    var email = ""

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_reset_password.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_reset_password -> {
                if (validateEmail()) resetPassword()
            }
        }
    }

    private fun validateEmail() : Boolean{
        var res = true
        email = edit_email_forgot.text.toString()
        if (email.isEmpty()){
            edit_email_forgot.setError("Email Belum Diisi")
            res = false
        }
        if (!isValidEmail(email)){
            edit_email_forgot.setError("Email Tidak Valid")
            res = false
        }
        return res
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun resetPassword(){
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Link Reset Password Sudah Terkirim", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
                    }
                    else {
                        Toast.makeText(context, "Email Tidak Valid", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}