package com.example.testf.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testf.model.Project
import com.example.testf.model.RequestProject
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectListViewModel : ViewModel() {

    private val _projectsStateFlow = MutableStateFlow<List<Project?>>(emptyList())
    val projectsStateFlow: StateFlow<List<Project?>> = _projectsStateFlow.asStateFlow()

    private val _projectsUser = MutableStateFlow<List<Project?>>(emptyList())
    val projectsUser: StateFlow<List<Project?>> = _projectsUser.asStateFlow()

    private var _title = MutableLiveData<String>()
    val title: MutableLiveData<String> get() = _title


    private var _description = MutableLiveData<String>()
    val description: MutableLiveData<String> get() = _description


    private var _location = MutableLiveData<String>()
    val location: MutableLiveData<String> get() = _location


    private var _userId = MutableLiveData<String>()
    val userId: MutableLiveData<String> get() = _userId

    //projectId
    private var _projectId = MutableLiveData<String>()
    val projectId: MutableLiveData<String> get() = _projectId

    private var _listRequestProject = MutableLiveData<List<RequestProject>?>()
    val listRequestProject: MutableLiveData<List<RequestProject>?> get() = _listRequestProject


    private suspend fun FunA(): Flow<List<Project>> = callbackFlow {
        val fireBaseDb = FirebaseFirestore.getInstance()

        fireBaseDb.collection("projects")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                var list = mutableListOf<Project>()
                snapshot?.documents?.forEach {
                    if (it.exists()) {
                        val projectList = it.toObject(Project::class.java)
                        list.add(projectList!!)
                    } else {
                    }

                }


                trySend(list)


            }

        awaitClose {

        }
    }

    fun FunB() {
        viewModelScope.launch {
            FunA().collect { list ->
                _projectsStateFlow.update { list }
            }

        }

    }


    // title ,description , location,userId,listRequestProject
    fun getItemInformation(documentId: String) {
        viewModelScope.launch {
            Firebase.firestore.collection("projects").whereEqualTo("projectId", documentId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _title.value = documentSnapshot.data?.get("title").toString()
                            _description.value = documentSnapshot.data?.get("description").toString()
                            _location.value = documentSnapshot.data?.get("location").toString()
                            _userId.value = documentSnapshot.data?.get("userId").toString()
                            _projectId.value = documentSnapshot.data?.get("projectId").toString()
                        }
                    }
                })
        }
    }


    // title ,description , location,userId,listRequestProject
    fun getProjectInformation(userId: String): List<Project?> {
        viewModelScope.launch {
            Firebase.firestore.collection("projects").whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                    if (task.isSuccessful) {
                        for (documentSnapshot in task.result.documents) {
                            _title.value = documentSnapshot.data?.get("title").toString()
                            _description.value = documentSnapshot.data?.get("description").toString()
                            _location.value = documentSnapshot.data?.get("location").toString()
                            _userId.value = documentSnapshot.data?.get("userId").toString()
                            _projectId.value = documentSnapshot.data?.get("projectId").toString()
                            _projectsUser.value = mutableListOf(Project(_title.value!!, _description.value!!))
                        }
                    }
                })
        }

        return projectsUser.value!!

    }


    private suspend fun FunC(): Flow<List<Project>> = callbackFlow {
        val fireBaseDb = FirebaseFirestore.getInstance()
        val id = Firebase.auth.currentUser!!.uid
        fireBaseDb.collection("projects").whereEqualTo("userId", id)
            .addSnapshotListener { snapshot, exception ->



                if (exception != null) {
                    return@addSnapshotListener
                }
                var list = mutableListOf<Project>()
                snapshot?.documents?.forEach {

                    if (it.exists()) {
                        val projectList = it.toObject(Project::class.java)
                        list.add(projectList!!)
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
                _projectsUser.update { list }
            }
        }


    }


}  // end ProjectListViewModel CLASS

