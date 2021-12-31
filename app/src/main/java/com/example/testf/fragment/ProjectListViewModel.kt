package com.example.testf.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testf.model.Project
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class ProjectListViewModel : ViewModel()  {

    val allProjects : ArrayList<Project?> = arrayListOf()
    var c = arrayListOf(Project("GG","G","rr"), Project("FF","Dd","dd"))


    private val _infoItem = MutableLiveData<ArrayList<Project?>>()
    val infoItem: LiveData<ArrayList<Project?>> = _infoItem

    fun getProjectItem(){

        viewModelScope.launch {
            try {
                _infoItem.value = allProjects
                Log.e("JANA", "${_infoItem.value}")
            } catch (e: Exception) {
                _infoItem.value = arrayListOf()
            }
        } // end coroutine

    } // end getProjectItem fun


    private suspend fun ibraFunA (): Flow<Project> = callbackFlow {
        val fireBaseDb = FirebaseFirestore.getInstance()

        fireBaseDb.collection("projects")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                snapshot?.documents?.forEach {
                    if (it.exists()) {
                        val productList = it.toObject(Project::class.java)
                        trySend(productList!!)
                        //    Log.d("TAG", "Current data: ${it.data}")
                    } else {
                        //      Log.d("TAG", "Current data: null")
                    }

                }




            }

        awaitClose {

        }
    }

     fun ibraFunB () {
         viewModelScope.launch {
             ibraFunA().collect{
                 allProjects.add(it)
             }

         }

    }


}  // end ProjectListViewModel CLASS

