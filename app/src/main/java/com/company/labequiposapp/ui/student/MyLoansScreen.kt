package com.company.labequiposapp.ui.student

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.company.labequiposapp.data.AuthRepository
import com.company.labequiposapp.data.LoanRepository
import com.company.labequiposapp.model.Loan
import com.company.labequiposapp.ui.components.LoanItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLoansScreen(
    onBack: () -> Unit
) {
    val user = AuthRepository.currentUser
    var loans by remember { mutableStateOf<List<Loan>>(emptyList()) }

    LaunchedEffect(user?.id) {
        val uid = user?.id ?: return@LaunchedEffect
        LoanRepository.getLoansForUser(uid) {
            loans = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis préstamos") },
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
                LoanItemRow(loan)
            }
        }
    }
}
