package com.vfg.silkroad.goo

import com.vfg.silkroad.goo.nodes.BrokenScoreNode
import com.vfg.silkroad.goo.nodes.CachedScoreNode
import com.vfg.silkroad.goo.nodes.DeviceNode
import com.vfg.silkroad.goo.nodes.FirebaseIdNode
import com.vfg.silkroad.goo.nodes.FirstInstNode
import com.vfg.silkroad.goo.nodes.GadidNode
import com.vfg.silkroad.goo.nodes.ProbeNode
import com.vfg.silkroad.goo.nodes.ReferrerNode
import com.vfg.silkroad.goo.nodes.ScoreAssembleNode
import com.vfg.silkroad.goo.ui.components.ScoreBuilder

object LoadingFlowFactory {

    fun create(baseUrl: String): LoadingFlowEngine {
        return LoadingFlowEngine(
            nodes = listOf(
                CachedScoreNode(),
                BrokenScoreNode(),
                ReferrerNode(),
                GadidNode(),
                DeviceNode(),
                ProbeNode(),
                FirstInstNode(),
                FirebaseIdNode(),
                ScoreAssembleNode(
                    scoreBuilder = ScoreBuilder(baseUrl)
                )
            )
        )
    }
}