package com.company.labequiposapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.model.Loan

@Composable
fun LoanItemRow(
    loan: Loan
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Equipo: ${loan.equipmentName}")
            Text(text = "Prestado a: ${loan.userName}")
            Text(text = "Del ${loan.loanDate} al ${loan.returnDate}")
            Text(text = "Estado: ${loan.status}")
        }
    }
}
