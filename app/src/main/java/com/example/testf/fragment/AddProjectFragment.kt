package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentAddProjectBinding
import com.example.testf.model.Project
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddProjectFragment : Fragment() {
// ProfileViewModel
    private val profileViewModel: ProfileViewModel by viewModels()


    private var _binding: FragmentAddProjectBinding? = null
    private val binding get() = _binding

    private val projectsCollectionRef = Firebase.firestore.collection("projects")

    override fun onResume() {
        super.onResume()
        val citys=resources.getStringArray(R.array.Select_a_city)
        val arrayAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_item, citys)
        binding?.autoCompleteTextView?.setAdapter(arrayAdapter)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Add Project"

        checkUserIsLogin()
        // on click add
        binding!!.saveBtn.setOnClickListener {

            val project = getProjectItem()
            val checkInfo = checkInfo (project)
            if (checkInfo)
                saveProject(project)

        }


    }

    // region 1- check User IsLogin
    private fun checkUserIsLogin() {

        if (profileViewModel.isLogin() == false) {
            findNavController().navigate(R.id.addProject_to_login)
            Toast.makeText(this.requireContext(), "You Should be login first", Toast.LENGTH_SHORT)
                .show()
        }
    }
    //endregion

    // region 2- check if Info NOT empty
    private fun checkInfo (project: Project): Boolean{

        if (project.title.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Title Of Project", Toast.LENGTH_SHORT).show()
            return false
        } else if (project.description.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Description Of Project", Toast.LENGTH_SHORT).show()
            return false
        } else if (project.location.isEmpty()) {
            Toast.makeText(this.requireContext(), "Please Enter Your Major", Toast.LENGTH_SHORT).show()
            return false
        } else{
            return true
        }
    }
    //endregion

    // region 3- return a project item
    private fun getProjectItem(): Project {

        val title =  binding!!.titleOfProject.text.toString()
        val description = binding!!.descriptionOfProject.text.toString()
        val locations = binding!!.locations.editText?.text.toString()
        val OwnerOfProject = profileViewModel.currentUserID()

//        val project = Project(title,description,locations, idUser)
        return Project(title,description,locations,OwnerOfProject)
    }
    //endregion

    // region 4- save Project with return id Document
    private fun saveProject (project : Project) {

        projectsCollectionRef.add(project)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setProjectIdDocument(task.result.id)
                } else {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }
    //endregion

    // region 5- update a item by add id Document
    private fun setProjectIdDocument(documentId: String) {
        val projectDetails = mapOf("projectId" to documentId)

        projectsCollectionRef.document(documentId)
            .update(projectDetails)
            .addOnCompleteListener {
                Toast.makeText(this.context, "Added", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.addProject_to_projectsList)
            }

    }
    //endregion

}