package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.databinding.FragmentDetailsProfileBinding
import kotlinx.coroutines.launch


class DetailsProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val projectviewModel: ProjectListViewModel by viewModels()

    private var _binding: FragmentDetailsProfileBinding? = null
    private val binding get() = _binding

    // UserId
    lateinit var UserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // arg
        arguments.let {
            UserId = it?.getString("UserId").toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsProfileBinding.inflate(inflater, container, false)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview()

        displayInfo ()

    }

    private fun recyclerview() {
        val adapter = ItemListAdapter("Profile")
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.projectViewModel = projectviewModel
        binding?.itemOfProjectUser?.adapter = adapter

        projectviewModel.getProjectInformation(UserId)


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                projectviewModel.projectsUser.collect {
                    adapter.submitList(it)
                }
            }
        }
        projectviewModel.FunD()
    }


    // region 4- get Info to Display
    private fun displayInfo () {
        profileViewModel.getOwnerProfilrByUserId(UserId)

        profileViewModel.fullNameUser.observe(viewLifecycleOwner, { binding!!.userFullName.setText(it) })
        profileViewModel.cvUser.observe(viewLifecycleOwner, { binding!!.bioInfo.setText(it) })

    }
    //endregion

}