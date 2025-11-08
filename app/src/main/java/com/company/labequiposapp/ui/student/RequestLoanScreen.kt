package com.company.labequiposapp.ui.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.data.AuthRepository
import com.company.labequiposapp.data.LoanRepository
import com.company.labequiposapp.model.Loan
import com.company.labequiposapp.model.LoanStatus
import java.text.SimpleDateFormat
import java.util.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestLoanScreen(
    equipmentId: String,
    equipmentName: String,
    onLoanCreated: () -> Unit
) {
    val user = AuthRepository.currentUser
    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val sdf = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val today = remember { Date() }
    val returnDate = remember {
        Calendar.getInstance().apply {
            time = today
            add(Calendar.DAY_OF_YEAR, 3)
        }.time
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Solicitar préstamo") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Equipo: $equipmentName")
            Text("Fecha de préstamo: ${sdf.format(today)}")
            Text("Fecha de devolución: ${sdf.format(returnDate)}")
            Spacer(Modifier.height(16.dp))

            message?.let {
                Text(it)
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    if (user == null) {
                        message = "Usuario no válido"
                        return@Button
                    }
                    loading = true
                    val loan = Loan(
                        userId = user.id,
                        userName = user.name,
                        equipmentId = equipmentId,
                        equipmentName = equipmentName,
                        loanDate = sdf.format(today),
                        returnDate = sdf.format(returnDate),
                        status = LoanStatus.PENDING
                    )
                    LoanRepository.createLoan(loan) { ok ->
                        loading = false
                        if (ok) {
                            message = "Solicitud creada"
                            onLoanCreated()
                        } else {
                            message = "Error al crear solicitud"
                        }
                    }
                },
                enabled = !loading
            ) {
                Text(if (loading) "Enviando..." else "Solicitar préstamo")
            }
        }
    }
}
