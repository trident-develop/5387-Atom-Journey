package com.vfg.silkroad.goo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.data.Visual
import com.vfg.silkroad.goo.ui.theme.AcidGreen
import com.vfg.silkroad.goo.ui.theme.DeepBlueCard
import com.vfg.silkroad.goo.ui.theme.DeepBlueElevated
import com.vfg.silkroad.goo.ui.theme.ElectricCyan
import com.vfg.silkroad.goo.ui.theme.NeonAmber
import com.vfg.silkroad.goo.ui.theme.NeonPink
import com.vfg.silkroad.goo.ui.theme.NeonViolet

@Composable
fun SubtopicHero(
    visual: Visual,
    accent: Color = ElectricCyan,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.linearGradient(
                    listOf(DeepBlueCard, DeepBlueElevated)
                )
            )
            .border(1.2.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(22.dp))
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        when (visual) {
            Visual.Atom -> AtomVisual(
                modifier = Modifier.fillMaxSize(),
                nucleusColor = NeonAmber,
                orbitColor = accent,
                electronColor = AcidGreen,
                orbits = 3
            )
            Visual.Shells -> ElectronShellsVisual(modifier = Modifier.fillMaxSize())
            Visual.Molecule -> MoleculeVisual(
                modifier = Modifier.fillMaxWidth(),
                leftLabel = "H", rightLabel = "O",
                leftColor = ElectricCyan, rightColor = NeonPink,
                bondColor = AcidGreen
            )
            Visual.DoubleBond -> MoleculeVisual(
                modifier = Modifier.fillMaxWidth(),
                leftLabel = "C", rightLabel = "O",
                leftColor = NeonViolet, rightColor = NeonPink,
                bondColor = AcidGreen,
                doubleBond = true
            )
            Visual.PhScale -> PhScaleVisual(modifier = Modifier.fillMaxWidth(), value = 4f)
            Visual.EnergyExo -> EnergyDiagramVisual(modifier = Modifier.fillMaxWidth(), exothermic = true)
            Visual.EnergyEndo -> EnergyDiagramVisual(modifier = Modifier.fillMaxWidth(), exothermic = false)
            Visual.Reaction -> ReactionVisual(modifier = Modifier.fillMaxWidth())
            Visual.PeriodicCell -> PeriodicCellVisual(
                modifier = Modifier,
                number = 6, symbol = "C", name = "Carbon", accent = accent
            )
            Visual.BondIonic -> BondTypeVisual(modifier = Modifier.fillMaxWidth(), type = BondType.Ionic)
            Visual.BondCovalent -> BondTypeVisual(modifier = Modifier.fillMaxWidth(), type = BondType.Covalent)
            Visual.BondMetallic -> BondTypeVisual(modifier = Modifier.fillMaxWidth(), type = BondType.Metallic)
            Visual.Electron -> ElectronFlowVisual(modifier = Modifier.fillMaxWidth())
        }
    }
}
