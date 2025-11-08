package com.company.labequiposapp.data

import com.company.labequiposapp.model.Loan
import com.company.labequiposapp.model.LoanStatus
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object LoanRepository {

    private val db = Firebase.firestore

    fun createLoan(loan: Loan, onResult: (Boolean) -> Unit) {
        val doc = db.collection("loans").document()
        val finalLoan = loan.copy(id = doc.id)
        doc.set(finalLoan)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getLoansForUser(userId: String, onResult: (List<Loan>) -> Unit) {
        db.collection("loans")
            .whereEqualTo("userId", userId)
            .orderBy("loanDate", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { res ->
                val list = res.documents.mapNotNull { it.toObject(Loan::class.java) }
                onResult(list)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }

    fun getPendingLoans(onResult: (List<Loan>) -> Unit) {
        db.collection("loans")
            .whereEqualTo("status", LoanStatus.PENDING.name)
            .get()
            .addOnSuccessListener { res ->
                onResult(res.documents.mapNotNull { it.toObject(Loan::class.java) })
            }
            .addOnFailureListener { onResult(emptyList()) }
    }

    fun getActiveLoans(onResult: (List<Loan>) -> Unit) {
        db.collection("loans")
            .whereEqualTo("status", LoanStatus.APPROVED.name)
            .get()
            .addOnSuccessListener { res ->
                onResult(res.documents.mapNotNull { it.toObject(Loan::class.java) })
            }
            .addOnFailureListener { onResult(emptyList()) }
    }

    fun updateLoanStatus(loanId: String, status: LoanStatus, onResult: (Boolean) -> Unit) {
        db.collection("loans").document(loanId)
            .update("status", status.name)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getLoansCountByEquipment(onResult: (Map<String, Int>) -> Unit) {
        db.collection("loans").get()
            .addOnSuccessListener { res ->
                val counts = mutableMapOf<String, Int>()
                res.documents.mapNotNull { it.toObject(Loan::class.java) }.forEach { loan ->
                    val key = loan.equipmentName
                    counts[key] = (counts[key] ?: 0) + 1
                }
                onResult(counts)
            }
            .addOnFailureListener { onResult(emptyMap()) }
    }
}
