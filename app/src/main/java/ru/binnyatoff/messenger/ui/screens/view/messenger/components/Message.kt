package ru.binnyatoff.messenger.ui.screens.view.messenger.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Message(
    text: String,
    time: String = "10:00",
    backgroundColor: Color,
    horizontalArrangement: Arrangement.Horizontal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp), horizontalArrangement = horizontalArrangement
    ) {
        Column() {
            Card(
                backgroundColor = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(modifier = Modifier.padding(4.dp), text = text)
            }
            Text(text = time)
        }

    }
}