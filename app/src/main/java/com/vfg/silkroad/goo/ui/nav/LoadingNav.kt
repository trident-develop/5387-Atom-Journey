package com.vfg.silkroad.goo.ui.nav

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vfg.silkroad.goo.LoadingActivity
import com.vfg.silkroad.goo.MainActivity
import com.vfg.silkroad.goo.ui.screens.ConnectScreen
import com.vfg.silkroad.goo.ui.screens.LoadingScreen
import com.vfg.silkroad.goo.ui.screens.isConnected
import kotlinx.coroutines.delay

object Routes {
    const val LOADING = "loading"
    const val CONNECT = "connect"
}

@Composable
@SuppressLint("ContextCastToActivity")
fun LoadingGraph() {

    val navController = rememberNavController()
    val context = LocalContext.current as LoadingActivity

    NavHost(
        navController = navController,
        startDestination = if (context.isConnected()) Routes.LOADING else Routes.CONNECT
    ) {
        composable(Routes.LOADING) {

            LaunchedEffect(Unit) {
                delay(2000)
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                context.finish()
            }

            LoadingScreen({})
        }

        composable(Routes.CONNECT) {
            ConnectScreen(navController)
        }
    }
}