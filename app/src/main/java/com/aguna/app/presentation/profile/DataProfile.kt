package com.aguna.app.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.aguna.app.R
import kotlinx.android.synthetic.main.fragment_data_profile.view.*
import kotlinx.android.synthetic.main.fragment_faq.view.*

class DataProfile : Fragment() {
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

    }
}