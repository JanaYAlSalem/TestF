package com.example.testf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentRequestDialogBinding
import com.example.testf.model.Profile
import com.example.testf.model.Project
import com.example.testf.model.RequestProject
import com.example.testf.model.RequestState


class RequestDialogFragment(var projectDocumentId: String) : DialogFragment() {

    private var _binding: FragmentRequestDialogBinding? = null
    private val binding get() = _binding

    private val reqViewModel: RequestProjectViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestDialogBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.makeReq?.setOnClickListener {
//            requestDialog_to_detailsProject
           // findNavController().navigate(R.id.requestDialog_to_detailsProject)

            val request = getRequestItem()
            val checkInfo = checkInfo(request)
            if(checkInfo){
            reqViewModel.makeReq(request)
            this.dismiss()
            }

        }

        binding!!.cancelBtn.setOnClickListener {
            this.dismiss()
        }
    }

    // region 1- check if Info NOT empty
    private fun checkInfo (request: RequestProject): Boolean{

        if (request.jobTitle.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Title Of Job", Toast.LENGTH_SHORT).show()
            return false
        } else if (request.description.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Describe Of Job", Toast.LENGTH_SHORT).show()
            return false
        } else{
            return true
        }
    }
    //endregion


    // region 2- return a request item
    private fun getRequestItem(): RequestProject {
//        val projectDocumentId = projectDocumentId
        val userId = profileViewModel.currentUserID()
        val jobTitle = binding!!.titleOfJob.text.toString()
        val describeOfJob = binding!!.describeOfJob.text.toString()


        // RequestProject (userId, projectId,jobTitle,description,reqId,stateOfRequest)
        return RequestProject(userId, projectDocumentId, jobTitle, describeOfJob)
    }
    //endregion





}