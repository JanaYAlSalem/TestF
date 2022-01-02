package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentProfileBinding
import com.example.testf.model.Profile
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val profilesCollectionRef = Firebase.firestore.collection("profiles")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on click add
        binding!!.saveBtn.setOnClickListener {
            val project =
                addProfile(createProfile())
        }

    }


    private fun addProfile (profile : Profile) {

        profilesCollectionRef.add(profile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Save", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.profile_to_login)
                } else {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun createProfile (): Profile {

        var firstName =  binding!!.firstNameProfile.text.toString()
        var lastName = binding!!.lastNameProfile.toString()
        var cv = binding!!.cvProfile.text.toString()

        //   val project = Project(firstName, lastName , cv)
        return Profile(firstName,lastName,cv)
    }
}