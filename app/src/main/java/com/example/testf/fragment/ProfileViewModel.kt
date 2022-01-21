package com.example.testf.fragment


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    // region Profile variables
    private val _fullName = MutableLiveData<String>()
    val fullName: MutableLiveData<String> get() = _fullName


    private val _majoringOfUser = MutableLiveData<String>()
    val majoringOfUser: MutableLiveData<String> get() = _majoringOfUser

    private val _cv = MutableLiveData<String>()
    val cv: MutableLiveData<String> get() = _cv


    private val _fullNameUser = MutableLiveData<String>()
    val fullNameUser: MutableLiveData<String> get() = _fullNameUser


    private val _majoringOfUserProfile = MutableLiveData<String>()
    val majoringOfUserProfile: MutableLiveData<String> get() = _majoringOfUserProfile

    private val _cvUser = MutableLiveData<String>()
    val cvUser: MutableLiveData<String> get() = _cvUser
    //endregion


    init {
        getProfileByUserId()
    }


    // region Sing Out
    fun singOut() = FirebaseAuth.getInstance().signOut()
    //endregion

    // region get current user ID
    fun currentUserID(): String = Firebase.auth.currentUser!!.uid
    //endregion


    // region get profile info by user ID
    fun getProfileByUserId() {
        val userId = currentUserID()
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _fullName.value = documentSnapshot.data?.get("fullName").toString()
                            _majoringOfUser.value = documentSnapshot.data?.get("majoringOfUser").toString()
                            _cv.value = documentSnapshot.data?.get("cv").toString()
                        }
                    }
                })
        }


    }
    //endregion

    // region get owner profile info by user ID
    fun getOwnerProfileByUserId(userId : String) {
        viewModelScope.launch {
            Firebase.firestore.collection("profiles").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _fullNameUser.value = documentSnapshot.data?.get("fullName").toString()
                            _majoringOfUserProfile.value = documentSnapshot.data?.get("majoringOfUser").toString()
                            _cvUser.value = documentSnapshot.data?.get("cv").toString()
                        }
                    }
                })
        }


    }
    //endregion

    // region get name by user ID
    fun getName(userId : String) {
            viewModelScope.launch {
                Firebase.firestore.collection("profiles").whereEqualTo("userId", userId)
                    .get()
                    .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                        if (task.isSuccessful) {
                            for (documentSnapshot in task.result.documents) {
                                _fullNameUser.value = documentSnapshot.data?.get("fullName").toString()
                            }
                        }
                    })
            }
    }
    //endregion


}