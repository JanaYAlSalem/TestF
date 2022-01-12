package com.example.testf.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.model.RequestProject
import com.example.testf.model.RequestState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RequestProjectViewModel : ViewModel()  {

    private val RequestCollectionRef = Firebase.firestore.collection("Requests")


    // RequestProject (reqId,userId,projectId,jobTitle,description,stateOfRequest)

    private val _reqId = MutableLiveData<String>()
    val reqId: MutableLiveData<String> get() = _reqId

    private val _userId = MutableLiveData<String>()
    val userId: MutableLiveData<String> get() = _userId

    private val _projectId = MutableLiveData<String>()
    val projectId: MutableLiveData<String> get() = _projectId

    private val _jobTitle = MutableLiveData<String>()
    val jobTitle: MutableLiveData<String> get() = _jobTitle

    private val _description = MutableLiveData<String>()
    val description: MutableLiveData<String> get() = _description

    private val _stateOfRequest = MutableLiveData<RequestState>()
    val stateOfRequest: MutableLiveData<RequestState> get() = _stateOfRequest

    fun makeReq (reqItem : RequestProject) {
        RequestCollectionRef.add(reqItem)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setRequestIdDocument(task.result.id)
                }
            }
    }


    // region 5- update a item by add id Document
    private fun setRequestIdDocument(documentId: String) {
        val reqDetails = mapOf("reqId" to documentId)

        RequestCollectionRef.document(documentId)
            .update(reqDetails)
            .addOnCompleteListener {
                Log.e("TAG", "setProjectIdDocument: ", )
            }

    }
    //endregion

}