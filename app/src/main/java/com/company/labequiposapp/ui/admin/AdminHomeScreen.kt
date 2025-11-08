package com.company.labequiposapp.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.ui.components.AppTopBar

@Composable
fun AdminHomeScreen(
    onLogout: () -> Unit,
    onGoPending: () -> Unit,
    onGoActive: () -> Unit,
    onGoReports: () -> Unit
) {
    Scaffold(
        topBar = { AppTopBar("Panel Administrador") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onGoPending, modifier = Modifier.fillMaxWidth()) {
                Text("Solicitudes pendientes")
            }
            Button(onClick = onGoActive, modifier = Modifier.fillMaxWidth()) {
                Text("Préstamos activos")
            }
            Button(onClick = onGoReports, modifier = Modifier.fillMaxWidth()) {
                Text("Reporte de equipos más solicitados")
            }
            OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                Text("Cerrar sesión")
            }
        }
    }
}
