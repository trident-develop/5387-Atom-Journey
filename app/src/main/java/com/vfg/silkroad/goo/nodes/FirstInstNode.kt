package com.vfg.silkroad.goo.nodes

import android.content.Context
import com.vfg.silkroad.goo.data.LoadingFlowContext
import com.vfg.silkroad.goo.event.FlowSignal
import com.vfg.silkroad.goo.event.LoadingNode

class FirstInstNode : LoadingNode {
    override val name: String = "PackageNode"

    override suspend fun run(ctx: LoadingFlowContext): Pair<LoadingFlowContext, FlowSignal> {
        val firstInst = readInstallTime(ctx.context)

        return ctx.put("package", firstInst) to FlowSignal.Continue
    }

    private fun readInstallTime(context: Context): String {
        return runCatching {
            context.packageManager
                .getPackageInfo(context.packageName, 0)
                .firstInstallTime
                .toString()
        }.getOrDefault("0")
    }
}