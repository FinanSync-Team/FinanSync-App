package com.example.alp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(
    inputState: TextFieldState,
    items: List<String>,
    label: String? = null,
    placeholder: String = "Choose One",
    onItemSelected: (String) -> Unit = {inputState.text = it},
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filter { it is PressInteraction.Press }
            .collect {
                expanded = !expanded
            }
    }
    Column {
        label?.let{
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = FontFamily(
                    Font(R.font.poppins_regular, FontWeight.Normal)
                ),
                color = Color(0xFF695C5C),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        ExposedDropdownMenuStack(
            textField = {
                OutlinedTextField(
                    shape = RoundedCornerShape(12.dp),
                    value = inputState.text,
                    placeholder = { Text(placeholder) },
                    onValueChange = {},
                    interactionSource = interactionSource,
                    readOnly = true,
                    trailingIcon = {
                        val rotation by animateFloatAsState(if (expanded) 180F else 0F, label = "Rotate Arrow")
                        Icon(
                            rememberVectorPainter(Icons.Default.ArrowDropDown),
                            contentDescription = "Dropdown Arrow",
                            Modifier.rotate(rotation),
                        )
                    },
                    supportingText = {
                        if(inputState.displayErrors){
                            Text(text = inputState.errors.joinToString(separator = "\n"))
                        }
                    },
                    isError = inputState.displayErrors,
                )
            },
            dropdownMenu = { boxWidth, itemHeight ->
                Box(
                    Modifier
                        .width(boxWidth)
                        .wrapContentSize(Alignment.TopStart)
                ) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEach { item ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .height(itemHeight)
                                    .width(boxWidth),
                                onClick = {
                                    expanded = false
                                    onItemSelected(item)
                                },
                                text =  {
                                    Text(item)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}
@Composable
private fun ExposedDropdownMenuStack(
    textField: @Composable () -> Unit,
    dropdownMenu: @Composable (boxWidth: Dp, itemHeight: Dp) -> Unit
) {

    SubcomposeLayout(
        modifier = Modifier
            .fillMaxWidth()
    ){ constraints ->
        val textFieldPlaceable =
            subcompose(ExposedDropdownMenuSlot.TextField, textField).first().measure(constraints)
        val dropdownPlaceable = subcompose(ExposedDropdownMenuSlot.Dropdown) {
            dropdownMenu(textFieldPlaceable.width.toDp(), textFieldPlaceable.height.toDp())
        }.first().measure(constraints)
        layout(textFieldPlaceable.width, textFieldPlaceable.height) {
            textFieldPlaceable.placeRelative(0, 0)
            dropdownPlaceable.placeRelative(0, textFieldPlaceable.height)
        }
    }
}
private enum class ExposedDropdownMenuSlot { TextField, Dropdown }


@Preview(showBackground = true)
@Composable
fun ExposedDropdownMenuPreview() {
    ExposedDropdownMenu(
        items = listOf("Option 1", "Option 2", "Option 3"),
        label = "Label",
        inputState = TextFieldState(""),
    )
}