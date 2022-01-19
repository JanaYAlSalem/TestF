package com.example.testf.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.testf.databinding.FragmentStateDialogBinding


class StateDialogFragment (var reqID : String, var isAccept : Boolean): DialogFragment() {


    private var _binding: FragmentStateDialogBinding? = null
    private val binding get() = _binding

    private val reqViewModel: RequestProjectViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStateDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkState(isAccept)

    }


    private fun checkState(isAccept : Boolean){

        if (isAccept) {

            binding!!.accStateBtn.setOnClickListener {
                reqViewModel.setRequestStateAccept(reqID)
                this.dismiss()
            }

            binding!!.decStateBtn.setOnClickListener {
                this.dismiss()
            }
        } else{

            binding!!.accStateBtn.setOnClickListener {
                reqViewModel.setRequestStateDeclined(reqID)
                this.dismiss()
            }

            binding!!.decStateBtn.setOnClickListener {
                this.dismiss()
            }

        }

    }

}