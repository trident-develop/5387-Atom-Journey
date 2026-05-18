package com.vfg.silkroad.goo.event

import com.vfg.silkroad.goo.data.LoadingFlowContext

interface LoadingNode {
    val name: String
    suspend fun run(ctx: LoadingFlowContext): Pair<LoadingFlowContext, FlowSignal>
}