package com.example.lab33.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Breadcrumbs(
    items: List<Pair<String, () -> Unit>>
) {
    Row(modifier = Modifier.padding(16.dp)) {
        items.forEachIndexed { index, (name, onClick) ->
            Text(
                text = name,
                modifier = Modifier.clickable(onClick = onClick),
                color = MaterialTheme.colorScheme.primary
            )
            if (index < items.size - 1) {
                Text(
                    text = " / ",
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}
