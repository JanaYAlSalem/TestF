package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentDisplayProfileBinding
import com.example.testf.model.Profile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DisplayProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentDisplayProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.editBtn.setOnClickListener {
            findNavController().navigate(R.id.displayProfile_to_profile)
        }
        binding!!.themeButton.setOnClickListener {

            findNavController().navigate(R.id.action_displayProfileFragment2_to_userPrefrencFragment)

        }

        binding!!.logoutBtn.setOnClickListener {
            // displayProfile_to_login
            profileViewModel.singOut()
            findNavController().navigate(R.id.displayProfile_to_login)

        }
        //onViewCreated
        profileViewModel.cv.observe(viewLifecycleOwner, { binding!!.bioInfo.setText(it) })

        profileViewModel.firstName.observe(viewLifecycleOwner, { binding!!.userFullName.setText(it) })
    }


}