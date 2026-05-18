package com.vfg.silkroad.goo.nodes

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.vfg.silkroad.goo.data.LoadingFlowContext
import com.vfg.silkroad.goo.event.FlowSignal
import com.vfg.silkroad.goo.event.LoadingNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GadidNode : LoadingNode {
    override val name: String = "GadidNode"

    override suspend fun run(ctx: LoadingFlowContext): Pair<LoadingFlowContext, FlowSignal> {

        val gadid = getGadid(ctx.context)

        return ctx.put("gadid", gadid) to FlowSignal.Continue
    }

    private val EMPTY_GADID = "00000000-0000-0000-0000-000000000000"

    private fun AdvertisingIdClient.Info?.safeId(): String {
        if (this == null) return EMPTY_GADID
        if (isLimitAdTrackingEnabled) return EMPTY_GADID
        return id?.takeIf { it.isNotBlank() } ?: EMPTY_GADID
    }

    suspend fun getGadid(context: Context): String =
        withContext(Dispatchers.IO) {
            runCatching {
                AdvertisingIdClient.getAdvertisingIdInfo(context)
            }.getOrNull().safeId()
        }

}