package com.example.alp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldValidation(
    inputState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String? = null,
    placeholder: String = "",
    onValueChange : (String) -> Unit = { inputState.text = it },
    modifier: Modifier = Modifier
){
    val popfamilyreg = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    Column(
        modifier = modifier
    ){
        if(label != null){
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = popfamilyreg,
                color = Color(0xFF695C5C),
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            value = inputState.text,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder)
            },
            supportingText = {
                if(inputState.displayErrors){
                    Text(text = inputState.errors.joinToString(separator = "\n"))
                }
            },
            isError = inputState.displayErrors,
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
        )
    }
}

@Preview
@Composable
fun PreviewTextFieldValidation(){
    TextFieldValidation(
        inputState = TextFieldState(
            validationRules = "required|email"
        ),
        label = "Username or email",
        placeholder = "example@gmail.com",
    )
}