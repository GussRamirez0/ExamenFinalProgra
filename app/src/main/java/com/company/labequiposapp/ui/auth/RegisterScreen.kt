package com.company.labequiposapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.labequiposapp.data.AuthRepository

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var carnet by remember { mutableStateOf("") }
    var career by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro Estudiante", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = carnet, onValueChange = { carnet = it }, label = { Text("Carnet") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = career, onValueChange = { career = it }, label = { Text("Carrera") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth())

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                loading = true
                error = null
                AuthRepository.registerStudent(
                    name, carnet, career, email, password
                ) { ok, msg ->
                    loading = false
                    if (ok) onRegistered() else error = msg ?: "Error al registrar"
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (loading) "Creando..." else "Registrarse")
        }
    }
}
