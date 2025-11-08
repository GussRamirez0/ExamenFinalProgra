package com.company.labequiposapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.company.labequiposapp.data.AuthRepository
import com.company.labequiposapp.data.EquipmentRepository
import com.company.labequiposapp.data.LoanRepository
import com.company.labequiposapp.model.Role
import com.company.labequiposapp.ui.admin.ActiveLoansScreen
import com.company.labequiposapp.ui.admin.AdminHomeScreen
import com.company.labequiposapp.ui.admin.PendingLoansScreen
import com.company.labequiposapp.ui.admin.ReportsScreen
import com.company.labequiposapp.ui.auth.LoginScreen
import com.company.labequiposapp.ui.auth.RegisterScreen
import com.company.labequiposapp.ui.student.MyLoansScreen
import com.company.labequiposapp.ui.student.RequestLoanScreen
import com.company.labequiposapp.ui.student.StudentHomeScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN,
        modifier = modifier
    ) {

        composable(AppDestinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    val user = AuthRepository.currentUser
                    if (user?.role == Role.ADMIN) {
                        navController.navigate(AppDestinations.ADMIN_HOME) {
                            popUpTo(AppDestinations.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(AppDestinations.STUDENT_HOME) {
                            popUpTo(AppDestinations.LOGIN) { inclusive = true }
                        }
                    }
                },
                onGoToRegister = {
                    navController.navigate(AppDestinations.REGISTER)
                }
            )
        }

        composable(AppDestinations.REGISTER) {
            RegisterScreen(
                onRegistered = {
                    navController.popBackStack()
                }
            )
        }

        // Estudiante

        composable(AppDestinations.STUDENT_HOME) {
            StudentHomeScreen(
                onLogout = {
                    AuthRepository.signOut()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(0)
                    }
                },
                onGoToMyLoans = {
                    navController.navigate(AppDestinations.MY_LOANS)
                },
                onRequestLoan = { equipmentId, equipmentName ->
                    navController.navigate(
                        AppDestinations.requestLoanRoute(equipmentId, equipmentName)
                    )
                }
            )
        }

        composable(AppDestinations.MY_LOANS) {
            MyLoansScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = AppDestinations.REQUEST_LOAN,
            arguments = listOf(
                navArgument("equipmentId") { type = NavType.StringType },
                navArgument("equipmentName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getString("equipmentId") ?: ""
            val equipmentName = backStackEntry.arguments?.getString("equipmentName") ?: ""
            RequestLoanScreen(
                equipmentId = equipmentId,
                equipmentName = equipmentName,
                onLoanCreated = {
                    navController.popBackStack()
                }
            )
        }

        // Admin

        composable(AppDestinations.ADMIN_HOME) {
            AdminHomeScreen(
                onLogout = {
                    AuthRepository.signOut()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(0)
                    }
                },
                onGoPending = { navController.navigate(AppDestinations.PENDING_LOANS) },
                onGoActive = { navController.navigate(AppDestinations.ACTIVE_LOANS) },
                onGoReports = { navController.navigate(AppDestinations.REPORTS) }
            )
        }

        composable(AppDestinations.PENDING_LOANS) {
            PendingLoansScreen(onBack = { navController.popBackStack() })
        }

        composable(AppDestinations.ACTIVE_LOANS) {
            ActiveLoansScreen(onBack = { navController.popBackStack() })
        }

        composable(AppDestinations.REPORTS) {
            ReportsScreen(onBack = { navController.popBackStack() })
        }
    }
}
