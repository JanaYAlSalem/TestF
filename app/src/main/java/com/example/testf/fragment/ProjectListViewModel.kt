package com.example.testf.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testf.adapter.bindRecyclerView
import com.example.testf.model.Project
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProjectListViewModel : ViewModel()  {


    var c = arrayListOf(Project("GG","G","rr"), Project("FF","Dd","dd"))


    private val _infoItem = MutableLiveData<List<Project?>>()
    val infoItem: LiveData<List<Project?>> = _infoItem

    fun getProjectItem(){

        viewModelScope.launch {
            try {
                _infoItem.value = getProjectFromFireStore()
                Log.e("JANA", "${_infoItem.value}")
            } catch (e: Exception) {
                _infoItem.value = listOf()
            }
        } // end coroutine

    } // end getProjectItem fun

    fun getProjectFromFireStore() : ArrayList<Project?> {

      //  val allProjects = mutableListOf<Project?>()
       val allProjects : ArrayList<Project?> = arrayListOf()
        Log.e("TAG", "START allProjects $allProjects", )
        val db = FirebaseFirestore.getInstance()
        db.collection("projects").orderBy("title", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Fire Store Error: ", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Log.e("TAG", " DC IS $dc", )
                            allProjects.add(dc.document.toObject(Project::class.java))
                        }
                    }
                    Log.e("TAG", "END allProjects $allProjects", )
                    // .notifyDataSetChanged()
                }

            })


        return allProjects
    }

}  // end ProjectListViewModel CLASS

