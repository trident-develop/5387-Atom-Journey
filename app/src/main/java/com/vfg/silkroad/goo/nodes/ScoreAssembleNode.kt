package com.vfg.silkroad.goo.nodes

import com.vfg.silkroad.goo.data.LoadingFlowContext
import com.vfg.silkroad.goo.event.FlowSignal
import com.vfg.silkroad.goo.event.LoadingDecision
import com.vfg.silkroad.goo.event.LoadingNode
import com.vfg.silkroad.goo.ui.components.ScoreBuilder

class ScoreAssembleNode(
    private val scoreBuilder: ScoreBuilder
) : LoadingNode {

    override val name: String = "LinkAssembleNode"

    override suspend fun run(ctx: LoadingFlowContext): Pair<LoadingFlowContext, FlowSignal> {

        val url = scoreBuilder.build(ctx.data)

//        log("$name: link ready = $url")

        return ctx.put("final_url", url) to FlowSignal.Finish(
            LoadingDecision.OpenWebView(url)
        )
    }
}