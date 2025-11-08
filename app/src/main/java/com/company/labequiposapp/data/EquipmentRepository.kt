package com.company.labequiposapp.data

import com.company.labequiposapp.model.Equipment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object EquipmentRepository {

    private val db = Firebase.firestore

    fun getEquipmentList(onResult: (List<Equipment>) -> Unit) {
        db.collection("equipment").get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull { doc ->
                    doc.toObject(Equipment::class.java)?.copy(id = doc.id)
                }
                onResult(list)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }
}
