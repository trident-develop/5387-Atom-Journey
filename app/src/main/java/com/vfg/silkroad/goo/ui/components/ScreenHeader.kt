package com.vfg.silkroad.goo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vfg.silkroad.goo.ui.theme.OnDeepBlue
import com.vfg.silkroad.goo.ui.theme.OnDeepBlueMuted

@Composable
fun ScreenHeader(
    title: String,
    subtitle: String? = null,
    topInset: Dp,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = topInset + 12.dp,
                bottom = 10.dp
            )
    ) {
        Text(
            title,
            style = MaterialTheme.typography.displaySmall,
            color = OnDeepBlue,
            fontWeight = FontWeight.Bold
        )
        if (subtitle != null) {
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = OnDeepBlueMuted
            )
        }
    }
}
