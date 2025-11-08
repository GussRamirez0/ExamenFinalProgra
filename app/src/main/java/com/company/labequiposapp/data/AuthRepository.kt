package com.company.labequiposapp.data

import com.company.labequiposapp.model.Role
import com.company.labequiposapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    // Usuario en memoria para saber el rol
    var currentUser: User? = null
        private set

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: run {
                    onResult(false, "Usuario inválido")
                    return@addOnSuccessListener
                }
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            currentUser = doc.toObject(User::class.java)?.copy(id = uid)
                            onResult(true, null)
                        } else {
                            onResult(false, "Perfil no encontrado")
                        }
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun registerStudent(
        name: String,
        carnet: String,
        career: String,
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""
                val user = User(
                    id = uid,
                    name = name,
                    carnet = carnet,
                    career = career,
                    email = email,
                    role = Role.STUDENT
                )
                db.collection("users").document(uid).set(user)
                    .addOnSuccessListener {
                        onResult(true, null)
                    }
                    .addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }

    fun signOut() {
        auth.signOut()
        currentUser = null
    }
}
