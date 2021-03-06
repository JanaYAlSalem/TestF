package com.example.testf.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.testf.R
import com.example.testf.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth


    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Log In"

        isLogin()

        // on click singIn text
        binding!!.login.setOnClickListener {
            signIn()
        }

        // on click singIn text
        binding!!.singIn.setOnClickListener {
            findNavController().navigate(R.id.login_to_registration)

        }
    }

    private fun signIn() {

        var email =  binding!!.email.text.toString()
        var pass = binding!!.password.text.toString()

        if (email.isNotEmpty()){
            if (pass.isNotEmpty()){

                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this.context, "Logg in", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.login_to_projectsList)
                        } else {
                            Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else
                Toast.makeText(this.requireContext(), "Enter password", Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(this.requireContext(), "Enter Email", Toast.LENGTH_SHORT).show()
    }

    private fun isLogin() {

        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            findNavController().navigate(R.id.login_to_displayProfile)
        }
    }

}