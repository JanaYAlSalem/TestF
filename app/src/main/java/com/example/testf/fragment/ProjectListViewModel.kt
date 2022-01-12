package com.example.testf.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testf.model.Project
import com.example.testf.model.RequestProject
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectListViewModel : ViewModel()  {

    val allProjects : ArrayList<Project?> = arrayListOf()

    private val _projectsStateFlow = MutableStateFlow<List<Project?>>(emptyList())
    val projectsStateFlow: StateFlow<List<Project?>> = _projectsStateFlow.asStateFlow()



    private var _title = MutableLiveData<String>()
    val title: MutableLiveData<String> get() = _title


    private var _description = MutableLiveData<String>()
    val description: MutableLiveData<String> get() = _description


    private var _location = MutableLiveData<String>()
    val location: MutableLiveData<String> get() = _location


    private var _userId = MutableLiveData<String>()
    val userId: MutableLiveData<String> get() = _userId


    private var _listRequestProject = MutableLiveData<List<RequestProject>?>()
    val listRequestProject: MutableLiveData<List<RequestProject>?> get() = _listRequestProject



    private suspend fun FunA (): Flow<List<Project>> = callbackFlow {
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
                        //    Log.d("TAG", "Current data: ${it.data}")
                    } else {
                        //      Log.d("TAG", "Current data: null")
                    }

                }


                trySend(list)


            }

        awaitClose {

        }
    }

     fun FunB () {
         viewModelScope.launch {
             FunA().collect{ list ->
                 Log.d("TAG", "FunB: $list")
                 _projectsStateFlow.update{list}
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

                                Log.e("TAG", "getItemInformation: ${_title.value}", )
                            }
                        }
                    })
            }
    }
}  // end ProjectListViewModel CLASS

