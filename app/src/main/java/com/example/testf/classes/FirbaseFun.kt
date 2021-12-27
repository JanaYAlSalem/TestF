package com.example.testf.classes

import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirbaseFun {

    var auth = Firebase.auth

    fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)

    } // end createAccount fun


     fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)

    } // end signIn fun



} // end class