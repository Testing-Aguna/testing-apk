package com.aguna.app.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aguna.app.R
import kotlinx.android.synthetic.main.fragment_bantuan.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class BantuanFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bantuan, container, false)

        view.arrowBack.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_bantuanFragment_to_profileFragment) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}