package com.company.labequiposapp.data

import com.company.labequiposapp.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserRepository {

    private val db = Firebase.firestore

    fun getUserById(userId: String, onResult: (User?) -> Unit) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { snap ->
                val user = snap.toObject(User::class.java)?.copy(id = userId)
                onResult(user)
            }
            .addOnFailureListener { onResult(null) }
    }
}
