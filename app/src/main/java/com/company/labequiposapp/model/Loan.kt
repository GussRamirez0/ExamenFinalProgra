package com.company.labequiposapp.model

data class Loan(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val equipmentId: String = "",
    val equipmentName: String = "",
    val loanDate: String = "",
    val returnDate: String = "",
    val status: LoanStatus = LoanStatus.PENDING
)
