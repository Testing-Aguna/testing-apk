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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.regex.Pattern


class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    var user = User()

    companion object {
        const val TAG = "Sign Up Fragment"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        configureGoogleSignIn()
        val user = firebaseAuth.currentUser
        if (user != null) {
            updateUserInfoAndUI()
        }
    }

    private fun init(){
        btn_masuk.setOnClickListener(this)
        btn_daftar_sign_up.setOnClickListener(this)
        btn_login_google.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_masuk -> {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
            R.id.btn_daftar_sign_up -> {
                if (validateAccount()) {
                    signUp()
                }
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
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                showLoading(false)
                Log.d(TAG, "Google sign in failed", e)
            }
        }

    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showLoading(false)
                Log.d(TAG, "SignInWithCredential:success")
                updateUserInfoAndUI()
            } else {
                Log.d(TAG, "signInWithCredential:failure", task.exception)
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
            sign_up_progress_bar.visibility = View.VISIBLE
        else
            sign_up_progress_bar.visibility = View.GONE
    }

    private fun validateAccount() : Boolean {
        var res = true
        user.name = edit_full_name.text.toString()
        user.email = edit_email.text.toString()
        user.password = edit_password.text.toString()
        if (user.name!!.isEmpty()){
            edit_full_name.setError("Nama Lengkap Belum Diisi")
            res = false
        }
        if (user.email!!.isEmpty()){
            edit_email.setError("Email Belum Diisi")
            res = false
        }
        if (!isValidEmail(user.email!!)){
            edit_email.setError("Email Tidak Valid")
            res = false
        }
        if (user.password!!.isEmpty()){
            edit_password.setError("Password Belum Diisi")
            res = false
        }
        if (user.password!!.length < 6){
            edit_password.setError("Password Harus Lebih dari 6 Karakter")
            res = false
        }
        if (edit_re_password.text.toString().isEmpty() &&
                edit_re_password.text.toString().equals(user.password!!)){
            edit_re_password.setError("Password Tidak Sesuai")
            res = false
        }
        return res
    }
    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun signUp(){
        firebaseAuth.createUserWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val action = SignUpFragmentDirections.actionSignUpFragmentToMainFragment(false)
                        findNavController().navigate(action)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    }

                }
                .addOnFailureListener { exception ->
                    handleException(exception)
                }

    }
    private fun handleException(exception: Exception?) {
        //exception is null
        when(exception){
            is FirebaseAuthUserCollisionException -> {
                Toast.makeText(context, "Email Sudah Digunakan", Toast.LENGTH_SHORT).show()
            }
            is FirebaseNetworkException -> Toast.makeText(context, "Tidak Ada Jaringan", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, "Coba Lagi", Toast.LENGTH_SHORT).show()
        }
    }
}