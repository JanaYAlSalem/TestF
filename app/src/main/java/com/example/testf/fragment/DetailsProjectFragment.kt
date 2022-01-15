package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.testf.databinding.FragmentDetailsProjectBinding


class DetailsProjectFragment : Fragment() {


    private var _binding: FragmentDetailsProjectBinding? = null
    private val binding get() = _binding

    private val projectViewModel: ProjectListViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

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

        (activity as AppCompatActivity).supportActionBar?.title = "Details Project"

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            detailsProjectViewModel = projectViewModel
            ownerProfileViewModel = profileViewModel
            detailsProjectFragment = this@DetailsProjectFragment
            projectViewModel.getItemInformation(documentId)
            profileViewModel.getOwnerProfilrByUserId(ownerId)
        }

        binding!!.joinToProject.setOnClickListener {
//            var action = DetailsProjectFragmentDirections
//                .actionDetailsProjectFragmentToRequestDialogFragment(projectViewModel.projectId.toString())
//            findNavController().navigate(action)

            var requestDialog = RequestDialogFragment(projectViewModel.projectId.value!!)
            requestDialog.show(childFragmentManager, "requestDialog")
        }


    }

}

