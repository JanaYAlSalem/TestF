package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.databinding.FragmentDisplayProfileBinding
import kotlinx.coroutines.launch


class DisplayProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val projectviewModel: ProjectListViewModel by viewModels()

    private var _binding: FragmentDisplayProfileBinding? = null
    private val binding get() = _binding

//    private var _bindingitem: FragmentDisplayProfileBinding? = null
//    private val binding get() = _binding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Profile"

        binding!!.editBtn.setOnClickListener {
            findNavController().navigate(R.id.displayProfile_to_profile)
        }
        binding!!.themeButton.setOnClickListener {

            findNavController().navigate(R.id.displayProfile_to_userPrefrenc)

        }

        // logout
        binding!!.theButton.setOnClickListener {
            // TODO: 1/18/2022 singOut().isSucc -> nav , lodoing image gone
            profileViewModel.singOut()
            findNavController().navigate(R.id.displayProfile_to_login)

        }

        //onViewCreated
        profileViewModel.fullName.observe(viewLifecycleOwner, { binding!!.userFullName.setText(it) })

        profileViewModel.cv.observe(viewLifecycleOwner, { binding!!.bioInfo.setText(it) })

        profileViewModel.majoringOfUser.observe(viewLifecycleOwner,{binding!!.bioTitle.setText(it)})

        val adapter = ItemListAdapter("Req",{projectviewModel.deleteReq(it.projectId)})
//        binding?.lifecycleOwner = viewLifecycleOwner
//        binding?.projectViewModel = projectviewModel
        binding?.itemOfProject?.adapter = adapter

        projectviewModel.getUserProjectInformation(profileViewModel.currentUserID()!!)

        projectviewModel.collectGetProjectsUser()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                projectviewModel.projectsUser.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

}