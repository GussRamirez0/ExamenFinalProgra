package com.company.labequiposapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.company.labequiposapp.navigation.AppNavHost
import com.company.labequiposapp.ui.theme.LabEquiposAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabEquiposAppTheme {
                AppNavHost()
            }
        }
    }
}
