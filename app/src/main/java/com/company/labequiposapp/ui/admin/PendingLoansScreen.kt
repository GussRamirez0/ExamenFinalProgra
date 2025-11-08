package com.company.labequiposapp.ui.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.data.LoanRepository
import com.company.labequiposapp.model.Loan
import com.company.labequiposapp.model.LoanStatus
import com.company.labequiposapp.ui.components.LoanItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingLoansScreen(
    onBack: () -> Unit
) {
    var loans by remember { mutableStateOf<List<Loan>>(emptyList()) }

    LaunchedEffect(Unit) {
        LoanRepository.getPendingLoans { loans = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitudes pendientes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(loans) { loan ->
                Column(modifier = Modifier.padding(8.dp)) {
                    LoanItemRow(loan)
                    Row {
                        TextButton(onClick = {
                            LoanRepository.updateLoanStatus(
                                loan.id,
                                LoanStatus.APPROVED
                            ) { ok ->
                                if (ok) {
                                    loans = loans.filterNot { it.id == loan.id }
                                }
                            }
                        }) {
                            Text("Aprobar")
                        }
                        TextButton(onClick = {
                            LoanRepository.updateLoanStatus(
                                loan.id,
                                LoanStatus.REJECTED
                            ) { ok ->
                                if (ok) {
                                    loans = loans.filterNot { it.id == loan.id }
                                }
                            }
                        }) {
                            Text("Rechazar")
                        }
                    }
                }
            }
        }
    }
}
