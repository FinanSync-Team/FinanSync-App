package com.example.alp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun LoadingListItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .shimmerEffect()
        )
        Column(
            modifier = Modifier.padding(start = 12.dp)
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom=5.dp)
                .height(15.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
            )
        }
    }
}