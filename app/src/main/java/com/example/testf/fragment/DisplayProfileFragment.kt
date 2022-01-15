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
import com.example.testf.model.Profile
import com.example.testf.model.Project
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class DisplayProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val projectviewModel: ProjectListViewModel by viewModels()

    val listTest = mutableListOf<Project>(Project("HERE I'm", "DES","Riyadh"))

    private var _binding: FragmentDisplayProfileBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding!!.logoutBtn.setOnClickListener {
            // displayProfile_to_login
            profileViewModel.singOut()
            findNavController().navigate(R.id.displayProfile_to_login)

        }
        //onViewCreated
        profileViewModel.cv.observe(viewLifecycleOwner, { binding!!.bioInfo.setText(it) })

        profileViewModel.fullName.observe(viewLifecycleOwner, { binding!!.userFullName.setText(it) })

        val adapter = ItemListAdapter()
        adapter.submitList(listTest)
        Log.e("TAG", "onViewCreated: $listTest", )
        Log.e("TAG", "onViewCreated: ${profileViewModel.currentUserID()}", )
        Log.e("TAG", "onViewCreated: ${projectviewModel.userId.value}", )
        Log.e("TAG", "onViewCreated: ${projectviewModel.projectsStateFlow.value}", )
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.projectViewModel = projectviewModel
        binding?.itemOfReq?.adapter = adapter



//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                listviewModel.projectsStateFlow.collect {
//                    if (listviewModel.userId == profileViewModel.lastName) {
//                        Log.e(
//                            "TAG",
//                            "onViewCreated: ${listviewModel.userId} AND : ${profileViewModel.lastName}",
//                        )
//                        adapter.submitList(it)
//                    }
//                }
//            }
//        }
//        listviewModel.FunB()
//    }
    }

}