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

class RequestProjectViewModel : ViewModel() {

    private val requestCollectionRef = Firebase.firestore.collection("requests")
    private val projectCollectionRef = Firebase.firestore.collection("projects")


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


    fun makeReq(reqItem: RequestProject) {
        requestCollectionRef.add(reqItem)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setRequestIdDocument(reqItem, task.result.id)

                }
            }
    }


    // region update a item by add id Document
    private fun setRequestIdDocument(reqItem: RequestProject, documentId: String) {
        val reqDetails = mapOf("reqId" to documentId)

        requestCollectionRef.document(documentId)
            .update(reqDetails)
            .addOnCompleteListener {
                // add req
//                addReq(reqItem, reqItem.projectId)

            }

    }
    //endregion

    private fun addReq(reqItem: RequestProject, projectDocumentId: String) {
        val request = mapOf("listRequestProject" to reqItem)

        projectCollectionRef.document(projectDocumentId).update(request)
            .addOnCompleteListener {
                Log.e("TAG", "addReq: REQ IS ADD TO LIST $projectDocumentId and $request")
            }
    }

}