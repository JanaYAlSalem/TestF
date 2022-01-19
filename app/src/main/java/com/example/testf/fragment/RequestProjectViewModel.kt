package com.example.testf.fragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.model.Project
import com.example.testf.model.RequestProject
import com.example.testf.model.RequestState
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RequestProjectViewModel : ViewModel() {

    private val requestCollectionRef = Firebase.firestore.collection("requests")
    private val projectCollectionRef = Firebase.firestore.collection("projects")

    // RequestProject (reqId,userId,projectId,jobTitle,description,stateOfRequest)
    private val _requestProjectStateFlow = MutableStateFlow<List<RequestProject?>>(emptyList())
    val requestProjectStateFlow: StateFlow<List<RequestProject?>> = _requestProjectStateFlow.asStateFlow()


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


    // region update a item by add ID Request Document
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

    // region update a item by change a state to ACCEPT
     fun setRequestStateAccept(documentId: String) {
        val reqState = mapOf("stateOfRequest" to RequestState.ACCEPT)
        requestCollectionRef.document(documentId)
            .update(reqState)
            .addOnCompleteListener {
                // add req
//                addReq(reqItem, reqItem.projectId)

            }

    }
    //endregion

    // region update a item by change a state to DECLINED
     fun setRequestStateDeclined(documentId: String) {
        val reqState = mapOf("stateOfRequest" to RequestState.DECLINED)
        requestCollectionRef.document(documentId)
            .update(reqState)
            .addOnCompleteListener {
                // add req
//                addReq(reqItem, reqItem.projectId)

            }

    }
    //endregion

     fun addReq(reqItem: RequestProject, projectDocumentId: String) {
        val request = mapOf("listRequestProject" to reqItem)

        projectCollectionRef.document(projectDocumentId).update(request)
            .addOnCompleteListener {
                Log.e("TAG", "addReq: REQ IS ADD TO LIST $projectDocumentId and $request")
            }
    }


    fun setProjectId (projectID: String) {
        _projectId.value = projectID
    }

    fun getAllReqByProjectId() {
        viewModelScope.launch {
            requestCollectionRef.whereEqualTo("projectId", _projectId.value)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _reqId.value = documentSnapshot.data?.get("reqId").toString()
                            _userId.value = documentSnapshot.data?.get("userId").toString()
                            _jobTitle.value = documentSnapshot.data?.get("jobTitle").toString()
                            _description.value = documentSnapshot.data?.get("description").toString()
                            _stateOfRequest.value = setValueOfState(documentSnapshot.data?.get("stateOfRequest").toString())
                        }
                    }
                })
        }


    }

    private suspend fun FunC(): Flow<List<RequestProject>> = callbackFlow {
        val fireBaseDb = FirebaseFirestore.getInstance()

        fireBaseDb.collection("requests").whereEqualTo("projectId", _projectId.value)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                var list = mutableListOf<RequestProject>()
                snapshot?.documents?.forEach {
                    if (it.exists()) {
                        val reqList = it.toObject(RequestProject::class.java)
                        Log.e("TAG", "FunC: $reqList")
                        list.add(reqList!!)
                    } else {
                    }

                }


                trySend(list)


            }

        awaitClose {

        }
    }

    fun FunD() {
        viewModelScope.launch {
            FunC().collect { list ->
                Log.e("TAG", "FunD: $list")
                _requestProjectStateFlow.update { list }
            }

        }

    }



    private fun setValueOfState(state: String): RequestState {
        when (state) {
            "WAITING" -> return RequestState.WAITING
            "ACCEPT" -> return RequestState.ACCEPT
            "DECLINED" -> return RequestState.DECLINED
            else -> return RequestState.WAITING
        }
    }

}