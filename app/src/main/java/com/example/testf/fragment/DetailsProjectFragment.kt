package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testf.adapter.ItemListAdapter
import com.example.testf.adapter.ItemReqAdapter
import com.example.testf.databinding.FragmentDetailsProjectBinding
import com.example.testf.model.RequestProject
import kotlinx.coroutines.launch


class DetailsProjectFragment : Fragment() {


    private var _binding: FragmentDetailsProjectBinding? = null
    private val binding get() = _binding

    private val projectViewModel: ProjectListViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val listReq = listOf(RequestProject(),RequestProject())
    lateinit var documentId: String
    lateinit var ownerId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            documentId = it?.getString("id").toString()
            ownerId = it?.getString("ownerId").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsProjectBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Details ${projectViewModel.title.value}"

        displayInfo ()

        recyclerview()

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            detailsProjectViewModel = projectViewModel
            ownerProfileViewModel = profileViewModel
            detailsProjectFragment = this@DetailsProjectFragment
            projectViewModel.getProjectItemInformation(documentId)
        }

        binding!!.joinToProject.setOnClickListener {

            var requestDialog = RequestDialogFragment(projectViewModel.projectId.value!!)
            requestDialog.show(childFragmentManager, "requestDialog")
        }

        // detailsProject_to_detailsProfile
        binding!!.nameOfOwnerProject.setOnClickListener{
            var action = DetailsProjectFragmentDirections.detailsProjectToDetailsProfile(ownerId)
            findNavController().navigate(action)
        }

    }

    private fun recyclerview() {

        val adapter = ItemReqAdapter("DetailsProject",{},{})
        binding?.itemOfRequest?.adapter = adapter
        adapter.submitList(listReq)


//        projectViewModel.getProjectInformation(UserId)
//
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                projectViewModel.projectsUser.collect {
//                    adapter.submitList(it)
//                }
//            }
//        }
//        projectViewModel.FunD()
    }

    // region 4- get Info to Display
    private fun displayInfo () {
        profileViewModel.getOwnerProfileByUserId(ownerId)

        profileViewModel.fullNameUser.observe(viewLifecycleOwner, { binding!!.nameOfOwnerProject.setText(it) })

    }
    //endregion



}

