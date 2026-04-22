package com.example.eco_controll_mobile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_controll_mobile.ui.features.auth.ForgotPasswordScreen
import com.example.eco_controll_mobile.ui.features.auth.LoginScreen
import com.example.eco_controll_mobile.ui.features.auth.SignUpScreen
import com.example.eco_controll_mobile.ui.features.auth.SplashScreen
import com.example.eco_controll_mobile.ui.features.dashboard.HomeScreen
import com.example.eco_controll_mobile.ui.features.profile.HelpSupportScreen
import com.example.eco_controll_mobile.ui.features.profile.SettingsScreen
import com.example.eco_controll_mobile.ui.features.profile.UserProfileScreen
import com.example.eco_controll_mobile.ui.features.registration.CisternRegistrationScreen
import com.example.eco_controll_mobile.ui.features.registration.SolarRegistrationScreen
import com.example.eco_controll_mobile.ui.features.reports.ReportsScreen
import com.example.eco_controll_mobile.ui.features.resources.CisternHistoryScreen
import com.example.eco_controll_mobile.ui.features.resources.ManageCisternScreen
import com.example.eco_controll_mobile.ui.features.resources.ManageSolarScreen
import com.example.eco_controll_mobile.ui.features.resources.SolarHistoryScreen
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@Composable
fun EcoControllApp() {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE)
    val token = sharedPref.getString("token", null)
    val navController = rememberNavController()
    val startDestination = if (token != null) "home" else "login"

    NavHost(navController = navController, startDestination = "splash") {

        // --- 0. TELA DE APRESENTAÇÃO ---
        composable(route = "splash") {
            SplashScreen(
                onTimeout = {
                    if (token != null) {
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    } else {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
            )
        }

        // --- 1. MÓDULO DE AUTENTICAÇÃO ---
        composable(route = "login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate("sign_up") },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") }
            )
        }
        composable(route = "sign_up") {
            SignUpScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = "forgot_password") {
            ForgotPasswordScreen(onBackClick = { navController.popBackStack() })
        }

        // --- 2. TELA INICIAL ---
        composable(route = "home") {
            HomeScreen(
                onNavigateToManageCistern = { navController.navigate("manage_cistern") },
                onNavigateToHistoryCistern = { navController.navigate("history_cistern") },
                onNavigateToManageSolar = { navController.navigate("manage_solar") },
                onNavigateToHistorySolar = { navController.navigate("history_solar") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToReports = { navController.navigate("reports") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }

        // --- 3. MÓDULO DE RECURSOS (HISTÓRICO E GERENCIAMENTO) ---
        composable(route = "history_cistern") {
            CisternHistoryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegistration = { navController.navigate("register_cistern") }
            )
        }
        composable(route = "history_solar") {
            SolarHistoryScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegistration = { navController.navigate("register_solar") }
            )
        }
        composable(route = "manage_cistern") { ManageCisternScreen { navController.popBackStack() } }
        composable(route = "manage_solar") { ManageSolarScreen { navController.popBackStack() } }

        // --- 4. MÓDULO DE CADASTRO ---
        composable(route = "register_cistern") {
            CisternRegistrationScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = "register_solar") {
            SolarRegistrationScreen(onBackClick = { navController.popBackStack() })
        }

        // --- 5. MÓDULO DE PERFIL E CONFIGURAÇÕES ---
        composable(route = "settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHelp = { navController.navigate("help_support") }
            )
        }
        composable(route = "help_support") {
            HelpSupportScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = "profile") { UserProfileScreen { navController.popBackStack() } }
        composable(route = "reports") { ReportsScreen { navController.popBackStack() } }
    }
}