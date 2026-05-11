package com.vfg.silkroad.goo.ui.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vfg.silkroad.goo.ui.components.ChemistryBackground
import com.vfg.silkroad.goo.ui.screens.LabScreen
import com.vfg.silkroad.goo.ui.screens.LearnScreen
import com.vfg.silkroad.goo.ui.screens.ResultsScreen
import com.vfg.silkroad.goo.ui.screens.SubtopicStudyScreen
import com.vfg.silkroad.goo.ui.screens.TestResultScreen
import com.vfg.silkroad.goo.ui.screens.TestScreen
import com.vfg.silkroad.goo.ui.screens.TestsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBar = currentRoute in setOf("learn", "tests", "lab", "results")

    Box(modifier = Modifier.fillMaxSize()) {
        ChemistryBackground()
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                AnimatedVisibility(
                    visible = showBar,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    BottomNav(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "learn",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("learn") {
                    LearnScreen(
                        contentPadding = padding,
                        onSubtopic = { topicId, subId ->
                            navController.navigate("subtopic_study/$topicId/$subId")
                        }
                    )
                }
                composable("tests") {
                    TestsScreen(
                        contentPadding = padding,
                        onStartTest = { topicId ->
                            navController.navigate("test/$topicId")
                        }
                    )
                }
                composable("results") {
                    ResultsScreen(contentPadding = padding)
                }
                composable("lab") {
                    LabScreen(contentPadding = padding)
                }
                composable(
                    route = "subtopic_study/{topicId}/{subtopicId}",
                    arguments = listOf(
                        navArgument("topicId") { type = NavType.StringType },
                        navArgument("subtopicId") { type = NavType.StringType }
                    )
                ) { entry ->
                    val topicId = entry.arguments?.getString("topicId").orEmpty()
                    val subId = entry.arguments?.getString("subtopicId").orEmpty()
                    SubtopicStudyScreen(
                        topicId = topicId,
                        subtopicId = subId,
                        contentPadding = padding,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(
                    route = "test/{topicId}",
                    arguments = listOf(navArgument("topicId") { type = NavType.StringType })
                ) { entry ->
                    val topicId = entry.arguments?.getString("topicId").orEmpty()
                    TestScreen(
                        topicId = topicId,
                        contentPadding = padding,
                        onBack = { navController.popBackStack() },
                        onFinished = { correct, total, time ->
                            navController.navigate(
                                "test_result/$topicId/$correct/$total/$time"
                            ) {
                                popUpTo("tests")
                            }
                        }
                    )
                }
                composable(
                    route = "test_result/{topicId}/{correct}/{total}/{time}",
                    arguments = listOf(
                        navArgument("topicId") { type = NavType.StringType },
                        navArgument("correct") { type = NavType.IntType },
                        navArgument("total") { type = NavType.IntType },
                        navArgument("time") { type = NavType.IntType },
                    )
                ) { entry ->
                    val topicId = entry.arguments?.getString("topicId").orEmpty()
                    val correct = entry.arguments?.getInt("correct") ?: 0
                    val total = entry.arguments?.getInt("total") ?: 0
                    val time = entry.arguments?.getInt("time") ?: 0
                    TestResultScreen(
                        topicId = topicId,
                        correct = correct,
                        total = total,
                        timeSpentSeconds = time,
                        contentPadding = padding,
                        onClose = {
                            navController.popBackStack(route = "tests", inclusive = false)
                        },
                        onRetry = {
                            navController.popBackStack(route = "tests", inclusive = false)
                            navController.navigate("test/$topicId")
                        }
                    )
                }
            }
        }
    }
}
