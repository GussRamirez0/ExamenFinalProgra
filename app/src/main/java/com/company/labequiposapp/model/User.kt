package com.company.labequiposapp.model

data class User(
    val id: String = "",
    val name: String = "",
    val carnet: String = "",
    val career: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val role: Role = Role.STUDENT
)
