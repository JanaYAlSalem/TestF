package com.example.testf.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.model.Profile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    //   Profile (userId, firstName ,lastName, cv)
    private val _firstName = MutableLiveData<String>()
    val firstName: MutableLiveData<String> get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: MutableLiveData<String> get() = _lastName

    private val _majoringOfUser = MutableLiveData<String>()
    val majoringOfUser: MutableLiveData<String> get() = _majoringOfUser

    private val _cv = MutableLiveData<String>()
    val cv: MutableLiveData<String> get() = _cv

    init {
    getProfilrByUserId()
    }


    fun isLogin(): Boolean {
        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            return true
        } else
            return false
    }

    fun singOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun currentUserID(): String {
        return Firebase.auth.currentUser!!.uid
    }


    fun getProfilrByUserId() {
        val userId = currentUserID()
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _firstName.value = documentSnapshot.data?.get("firstName").toString()
                            _lastName.value = documentSnapshot.data?.get("lastName").toString()
                            _majoringOfUser.value = documentSnapshot.data?.get("majoringOfUser").toString()
                            _cv.value = documentSnapshot.data?.get("cv").toString()
                        }
                    }
                })
        }


    }

    fun getOwnerProfilrByUserId(userId : String) {
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _firstName.value = documentSnapshot.data?.get("firstName").toString()
                            _lastName.value = documentSnapshot.data?.get("lastName").toString()
                            _majoringOfUser.value = documentSnapshot.data?.get("majoringOfUser").toString()
                            _cv.value = documentSnapshot.data?.get("cv").toString()
                        }
                    }
                })
        }


    }


}