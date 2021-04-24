package com.aguna.app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aguna.app.R
import com.aguna.app.presentation.chat.ChatFragment
import com.aguna.app.presentation.history.HistoryFragment
import com.aguna.app.presentation.home.HomeFragment
import com.aguna.app.presentation.profile.MainProfile
import com.aguna.app.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        setDefaultFragment()

        return v;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavigationConfiguration()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        if (user == null) findNavController().navigate(R.id.action_mainFragment_to_signInFragment)
        else if (!user.isEmailVerified) findNavController().navigate(R.id.action_mainFragment_to_signInFragment)

    }

    private fun setBottomNavigationConfiguration() {
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragmentTransaction = parentFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    fragmentTransaction.replace(R.id.fl_fragment_container, homeFragment)
                    fragmentTransaction.commit()
                    true
                }
                R.id.navigation_history -> {
                    val historyFragment = HistoryFragment()
                    fragmentTransaction.replace(R.id.fl_fragment_container, historyFragment)
                    fragmentTransaction.commit()
                    true
                }
                R.id.navigation_chat -> {
                    val chatFragment = ChatFragment()
                    fragmentTransaction.replace(R.id.fl_fragment_container, chatFragment)
                    fragmentTransaction.commit()
                    true
                }
                R.id.navigation_profile -> {
                    val MainProfile = MainProfile()
                    fragmentTransaction.replace(R.id.fl_fragment_container, MainProfile)
                    fragmentTransaction.commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun setDefaultFragment() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_fragment_container, HomeFragment())
        fragmentTransaction.commit()
    }


}