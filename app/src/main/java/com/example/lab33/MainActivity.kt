package com.example.lab33

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.lab33.ui.theme.Lab33Theme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {

        composable("home") {
            HomeScreen(
                openSubjects = { nav.navigate("subjects") },
                openDoneLabs = { nav.navigate("done_labs") }
            )
        }

        composable("subjects") {
            com.example.lab33.ui.SubjectListScreen(
                onNavigateToLabs = { subjectId -> nav.navigate("labs/$subjectId") },
                onNavigateBack = { nav.popBackStack() }
            )
        }

        composable(
            route = "labs/{subjectId}",
            arguments = listOf(androidx.navigation.navArgument("subjectId") { type = androidx.navigation.NavType.LongType })
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getLong("subjectId") ?: 0L
            com.example.lab33.ui.LabListScreen(
                subjectId = subjectId,
                onNavigateBack = { nav.popBackStack() },
                onNavigateToSubjects = { nav.navigate("subjects") { popUpTo("home") } }
            )
        }

        composable("done_labs") {
            com.example.lab33.ui.DoneLabsScreen(
                onNavigateBack = { nav.popBackStack() }
            )
        }
    }
}
