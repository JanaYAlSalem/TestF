package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentAddProjectBinding
import com.example.testf.model.Project
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddProjectFragment : Fragment() {

    private var _binding: FragmentAddProjectBinding? = null
    private val binding get() = _binding

    private val projectsCollectionRef = Firebase.firestore.collection("projects")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on click add
        binding!!.addBtn.setOnClickListener {
            val project =
            addProject (createproject())
        }


    }

    private fun addProject (project : Project) {

        projectsCollectionRef.add(project)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Added", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.addProject_to_projectsList)
                } else {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun createproject (): Project {

        var title =  binding!!.titleOfProject.text.toString()
        var description = binding!!.descriptionOfProject.text.toString()
        var locations = binding!!.locations.text.toString()

//        val project = Project(title,description,locations)
            return Project(title,description,locations)
    }

}