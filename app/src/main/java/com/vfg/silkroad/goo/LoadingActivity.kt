package com.vfg.silkroad.goo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.vfg.silkroad.goo.data.db.ScoreDao
import com.vfg.silkroad.goo.data.db.ScoreDbHelper
import com.vfg.silkroad.goo.data.db.ScoreStorage
import com.vfg.silkroad.goo.ui.nav.LoadingGraph
import com.vfg.silkroad.goo.ui.screens.privacy.MainClient

class LoadingActivity : ComponentActivity() {

    private val scoreStorage by lazy {
        ScoreStorage(
            scoreDao = ScoreDao(
                dbHelper = ScoreDbHelper(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemBars()
        val mainClient = MainClient(this, scoreStorage)
        setContent {
            LoadingGraph(mainClient, scoreStorage)
        }
    }

    private fun hideSystemBars() {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemBars()
    }

    override fun onResume() {
        super.onResume()
        hideSystemBars()
    }
}