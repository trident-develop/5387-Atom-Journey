package com.vfg.silkroad.goo.nodes

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.vfg.silkroad.goo.data.LoadingFlowContext
import com.vfg.silkroad.goo.event.FlowSignal
import com.vfg.silkroad.goo.event.LoadingNode
import kotlinx.coroutines.tasks.await

class FirebaseIdNode : LoadingNode {

    override val name: String = "FirebaseIdNode"

    override suspend fun run(
        ctx: LoadingFlowContext
    ): Pair<LoadingFlowContext, FlowSignal> {

        val firebaseId = loadFirebaseId()

        return ctx.put("firebase_id", firebaseId) to FlowSignal.Continue
    }

    private suspend fun loadFirebaseId(): String {
        return runCatching {
            Firebase.analytics.appInstanceId.await()
        }.getOrNull() ?: "null"
    }
}