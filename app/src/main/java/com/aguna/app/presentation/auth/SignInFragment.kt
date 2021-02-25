package com.aguna.app.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aguna.app.R
import com.aguna.app.model.User
import com.aguna.app.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.btn_login_google
import kotlinx.android.synthetic.main.fragment_sign_in.btn_masuk
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.regex.Pattern

class SignInFragment : Fragment(), View.OnClickListener{
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    var user = User()
    companion object {
        private const val TAG = "SignInFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        btn_masuk.setOnClickListener(this)
        btn_lupa_sandi.setOnClickListener(this)
        btn_daftar.setOnClickListener(this)
        btn_login_google.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_masuk -> {
                if (validateAccount()) signInEmail()
            }
            R.id.btn_lupa_sandi -> {
                findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
            }
            R.id.btn_daftar -> {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            R.id.btn_login_google -> {
                signIn()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.FIREBASE_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(SignUpFragment.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                showLoading(false)
                Log.d(SignUpFragment.TAG, "Google sign in failed", e)
            }
        }

    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showLoading(false)
                Log.d(SignUpFragment.TAG, "SignInWithCredential:success")
                updateUserInfoAndUI()
            } else {
                Log.d(SignUpFragment.TAG, "signInWithCredential:failure", task.exception)
                showToastMessage(getString(R.string.authentication_failed))
                showLoading(false)
            }
        }
    }

    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), mGoogleSignInOptions)
    }
    private fun signIn() {
        showLoading(true)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Constants.FIREBASE_GOOGLE_SIGN_IN)
    }

    private fun updateUserInfoAndUI() {
        findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state)
            sign_in_progress_bar.visibility = View.VISIBLE
        else
            sign_in_progress_bar.visibility = View.GONE
    }

    private fun signInEmail(){
        showLoading(true)
        firebaseAuth.signInWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        showLoading(false)
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
                        val user = firebaseAuth.currentUser
                    } else {
                        showLoading(false)
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "Email dan Password Tidak Sesuai",
                                Toast.LENGTH_SHORT).show()
                        // ...
                    }
                    // ...
                }

    }

    private fun validateAccount() : Boolean {
        var res = true
        user.email = edit_email_sign_in.text.toString()
        user.password = edit_password_sign_in.text.toString()

        if (user.email!!.isEmpty()){
            edit_email_sign_in.setError("Email Belum Diisi")
            res = false
        }
        if (!isValidEmail(user.email!!)){
            edit_email_sign_in.setError("Email Tidak Valid")
            res = false
        }
        if (user.password!!.isEmpty()){
            edit_password_sign_in.setError("Password Belum Diisi")
            res = false
        }
        if (user.password!!.length < 6){
            edit_password_sign_in.setError("Password Harus Lebih dari 6 Karakter")
            res = false
        }

        return res
    }
    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

}