package com.example.testf.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentProfileBinding
import com.example.testf.model.Profile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val profileViewModel: ProfileViewModel by viewModels()
    private val profilesCollectionRef = Firebase.firestore.collection("profiles")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.updateBtn.isVisible = false

        displayInfo ()

        // on click save
        binding!!.saveBtn.setOnClickListener {
            val profile = getProfileInfo()
            val checkInfo = checkInfo (profile)
            if (checkInfo)
            saveProfileInfo(profile)
        }
    }


    // region 1- check if Info NOT empty
    private fun checkInfo (profile: Profile): Boolean{

        if (profile.firstName.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter First Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (profile.lastName.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Last Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (profile.majoringOfUser.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Your Major", Toast.LENGTH_SHORT).show()
            return false
        } else if (profile.cv.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Your CV", Toast.LENGTH_SHORT).show()
            return false
        } else{
            return true
        }
    }
    //endregion

    // region 2- return a Profile Info
    private fun getProfileInfo(): Profile {

        val userId = profileViewModel.currentUserID()
        val firstName = binding!!.firstNameProfile.text.toString()
        val lastName = binding!!.lastNameProfile.text.toString()
        val Majoring = binding!!.majoringOfUser.text.toString()
        val cv = binding!!.cvProfile.text.toString()

        binding!!.saveBtn.isEnabled = true
        //   val project = Project(Id ,firstName, lastName , cv)
        return Profile(userId, firstName, lastName,Majoring, cv)
    }
    //endregion

    // region 3- save Profile Info
    private fun saveProfileInfo(profile: Profile) {
        profilesCollectionRef.document(profile.userId).set(profile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Save", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.profile_to_displayProfile)
                } else {
                    Toast.makeText(this.context, "Try again", Toast.LENGTH_LONG).show()
                }
            }

    }
    //endregion

    // region 4- get Info to Display
    private fun displayInfo () {
        profileViewModel.firstName.observe(viewLifecycleOwner, { binding!!.firstNameProfile.setText(it) })

        profileViewModel.lastName.observe(viewLifecycleOwner, { binding!!.lastNameProfile.setText(it) })

        profileViewModel.majoringOfUser.observe(viewLifecycleOwner, { binding!!.majoringOfUser.setText(it) })

        profileViewModel.cv.observe(viewLifecycleOwner, { binding!!.cvProfile.setText(it) })
    }
    //endregion


    // region test FUN
    private fun checkUserInfo() {
        /// test TWO
            val userId = profileViewModel.currentUserID()
        profilesCollectionRef.whereEqualTo("userId", userId).get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.e("TAG", "checkUserInfo: COMPLETE isSuccessful", )
                    Log.e("TAG", "checkUserInfo: $userId", )
                    Log.e("TAG", "checkUserInfo: $task", )
                    binding!!.saveBtn.isVisible = false
                    binding!!.updateBtn.isVisible = true
                }else {
                    Log.e("TAG", "checkUserInfo: COMPLETE is NOT Successful", )
                    binding!!.saveBtn.isVisible = true
                    binding!!.updateBtn.isVisible = false
                }
            }
            .addOnFailureListener{
                Log.e("TAG", "checkUserInfo: FAILURE", )
                binding!!.saveBtn.isVisible = true
                binding!!.updateBtn.isVisible = false
            }

//                Firebase.firestore.collection("profiles").whereEqualTo("userId", userId).get()
//                    .addOnCompleteListener{ task ->
//                        if (task.isSuccessful) {
//                            Log.e("TAG", "checkUserInfo: COMPLETE isSuccessful", )
//                            binding!!.saveBtn.isVisible = false
//                            binding!!.updateBtn.isVisible = true
//                        }else {
//                            Log.e("TAG", "checkUserInfo: COMPLETE is NOT Successful", )
//                            binding!!.saveBtn.isVisible = true
//                            binding!!.updateBtn.isVisible = false
//                        }
//                    }
//                    .addOnFailureListener{
//                        Log.e("TAG", "checkUserInfo: FAILURE", )
//                        binding!!.saveBtn.isVisible = true
//                        binding!!.updateBtn.isVisible = false
//                    }
    }

    //        FirebaseFirestore.getInstance().collection("profiles").whereEqualTo("userId",userID).get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful){
//                    for (doc in task.result.documents){
//                    Log.e("TAG", "getIdDec ${doc.data!!.get("userID")}")}
//
//                    binding!!.saveBtn.isVisible = false
//                    binding!!.updateBtn.isVisible = true
//                } else {
//                    binding!!.saveBtn.isVisible = true
//                    binding!!.updateBtn.isVisible = false
//                }
//
//
//            }


//
//    for (doc in task.result.documents)
//    if(currentUserID == doc.data!!.get("userId"))
//    binding!!.saveBtn.isVisible = false
//    else
//    binding!!.updateBtn.isVisible = false
//                         Log.e("TAG", "getIdDec ${doc.data!!.get("userId")}")


    private fun update() {
        var profile = getProfileInfo()

        profilesCollectionRef.document(profile.userId).update("firstName", profile.firstName, "lastName",profile.lastName, "cv", profile.cv)

    }
    //endregion


}