package com.vfadin.events.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vfadin.events.R

@Composable
fun Tabs(
    list: List<String>,
    selectedIndex: MutableState<Int>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    TabRow(selectedTabIndex = selectedIndex.value,
        backgroundColor = colorResource(R.color.surface_gray),
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .then(modifier),
        indicator = {
            Box(Modifier.height(0.dp))
        }
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex.value == index
            Box(Modifier.padding(4.dp)) {
                Tab(
                    modifier = if (selected) Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White)
                    else Modifier
                        .clip(RoundedCornerShape(6.dp)),
                    text = {
                        Text(
                            text = text,
                            color = if (!selected) colorResource(R.color.gray)
                            else colorResource(
                                R.color.text_black
                            )
                        )
                    },
                    onClick = {
                        selectedIndex.value = index
                        onClick()
                    },
                    selected = selected
                )
            }
        }
    }
}