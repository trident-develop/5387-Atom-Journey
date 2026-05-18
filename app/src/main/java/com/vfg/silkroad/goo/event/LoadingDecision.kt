package com.vfg.silkroad.goo.event

sealed class LoadingDecision {
    data class OpenWebView(val url: String) : LoadingDecision()
    data class OpenOtherScreen(val reason: String) : LoadingDecision()
}