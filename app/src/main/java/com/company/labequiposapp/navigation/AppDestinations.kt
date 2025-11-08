package com.company.labequiposapp.navigation

object AppDestinations {
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val STUDENT_HOME = "student_home"
    const val REQUEST_LOAN = "request_loan/{equipmentId}/{equipmentName}"
    const val MY_LOANS = "my_loans"

    const val ADMIN_HOME = "admin_home"
    const val PENDING_LOANS = "pending_loans"
    const val ACTIVE_LOANS = "active_loans"
    const val REPORTS = "reports"

    fun requestLoanRoute(equipmentId: String, equipmentName: String): String =
        "request_loan/$equipmentId/$equipmentName"
}
