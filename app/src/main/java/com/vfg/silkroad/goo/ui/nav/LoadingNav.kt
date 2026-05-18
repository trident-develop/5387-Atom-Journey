package com.vfg.silkroad.goo.ui.nav

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vfg.silkroad.goo.LoadingActivity
import com.vfg.silkroad.goo.LoadingFlowFactory
import com.vfg.silkroad.goo.MainActivity
import com.vfg.silkroad.goo.data.db.ScoreStorage
import com.vfg.silkroad.goo.event.LoadingDecision
import com.vfg.silkroad.goo.ui.components.getBaseUrl
import com.vfg.silkroad.goo.ui.screens.ConnectScreen
import com.vfg.silkroad.goo.ui.screens.LoadingScreen
import com.vfg.silkroad.goo.ui.screens.isConnected
import com.vfg.silkroad.goo.ui.screens.privacy.MainClient
import kotlinx.coroutines.delay

object Routes {
    const val LOADING = "loading"
    const val CONNECT = "connect"
}

@Composable
@SuppressLint("ContextCastToActivity")
fun LoadingGraph(
    mainClient: MainClient,
    scoreStorage: ScoreStorage,
) {

    val navController = rememberNavController()
    val context = LocalContext.current as LoadingActivity
    val openOtherScreen = remember { {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        context.finish()
    } }

    LaunchedEffect(Unit) {
        mainClient.setOnOpenOtherScreenCallback(openOtherScreen)
    }

    NavHost(
        navController = navController,
        startDestination = if (context.isConnected()) Routes.LOADING else Routes.CONNECT
    ) {
        composable(Routes.LOADING) {

            LaunchedEffect(Unit) {
                val engine = LoadingFlowFactory.create(getBaseUrl())

                engine.execute(
                    context = context,
                    storage = scoreStorage
                ).collect { decision ->
                    when (decision) {
                        is LoadingDecision.OpenWebView -> {
//                                log("LoadingScreen: open webview = ${decision.url}")
                            mainClient.loadUrl(decision.url)
                        }

                        is LoadingDecision.OpenOtherScreen -> {
//                                log("LoadingScreen: open other screen, reason = ${decision.reason}")
                            openOtherScreen()
                        }
                    }
                }
            }


            LoadingScreen({})
        }

        composable(Routes.CONNECT) {
            ConnectScreen(navController)
        }
    }
}