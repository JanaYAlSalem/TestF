package com.example.testf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.testf.databinding.FragmentRequestDialogBinding
import com.example.testf.model.RequestProject


class RequestDialogFragment : DialogFragment() {

    private var _binding: FragmentRequestDialogBinding? = null
    private val binding get() = _binding

    private val reqViewModel: RequestProjectViewModel by viewModels()

    val testReq = RequestProject("","7jFEnQ3ankeE2B6E09FnzW13nVW2",
        "AsFB1nJhfk18viHrCrHO",
        "design","to design Ur App")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestDialogBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.makeReq?.setOnClickListener {
            reqViewModel.makeReq(testReq)
        }
    }




}