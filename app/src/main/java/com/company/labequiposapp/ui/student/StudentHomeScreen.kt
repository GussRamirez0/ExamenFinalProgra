package com.company.labequiposapp.ui.student

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.data.AuthRepository
import com.company.labequiposapp.data.EquipmentRepository
import com.company.labequiposapp.model.Equipment
import com.company.labequiposapp.ui.components.AppTopBar
import com.company.labequiposapp.ui.components.EquipmentItemCard

@Composable
fun StudentHomeScreen(
    onLogout: () -> Unit,
    onGoToMyLoans: () -> Unit,
    onRequestLoan: (String, String) -> Unit
) {
    var equipmentList by remember { mutableStateOf<List<Equipment>>(emptyList()) }

    LaunchedEffect(Unit) {
        EquipmentRepository.getEquipmentList { list ->
            equipmentList = list
        }
    }

    Scaffold(
        topBar = { AppTopBar("Catálogo de Equipos") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onGoToMyLoans) {
                    Text("Mis préstamos")
                }
                TextButton(onClick = onLogout) {
                    Text("Cerrar sesión")
                }
            }

            LazyColumn {
                items(equipmentList) { equipment ->
                    if (equipment.available) {
                        EquipmentItemCard(
                            equipment = equipment,
                            onClick = {
                                onRequestLoan(equipment.id, equipment.name)
                            }
                        )
                    }
                }
            }
        }
    }
}
