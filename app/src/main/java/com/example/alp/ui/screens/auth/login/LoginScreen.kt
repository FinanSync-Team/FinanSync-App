package com.example.alp.ui.screens.auth.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.abstraction.SnackbarMessage
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.components.TextFieldState
import com.example.alp.ui.components.TextFieldValidation
import com.example.alp.ui.theme.buttonColor
import com.example.alp.ui.theme.cekboxcolor
import com.example.alp.ui.theme.forpassCol
import com.example.alp.ui.theme.line
import com.example.alp.ui.theme.linegoogle
import com.example.alp.ui.theme.loggoogletxt
import com.example.alp.ui.theme.textfilecol
import kotlinx.coroutines.delay
import java.util.regex.Pattern

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    state: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
) {
    val popfamilybold = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
    val popfamilyreg = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )

    val snackbarHostState = remember { SnackbarHostState() }

    if (state.isSuccessLogin) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                SnackbarMessage.success("Login Success")
            )
            navigateToHome()
        }
    }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
        onRetry = { onEvent(LoginEvent.OnLoginClicked) }
    )
    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Hi, Welcome Back!\uD83D\uDC4B",
                    fontFamily = popfamilybold,
                    fontSize = 24.sp
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    TextFieldValidation(
                        inputState = state.login,
                        label="Username or email",
                        placeholder = "example@gmail.com",
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 36.dp)
                            .padding(bottom = 15.dp),
                    )

                    CustomPasswordField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp),
                        label = "Password",
                        inputState = state.password,
                        imeAction = ImeAction.Done,
                        onImeAction = { onEvent(LoginEvent.OnLoginClicked) },
                        placeholder = "Password",
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val checkedState = remember { mutableStateOf(true) }

                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            uncheckedColor = cekboxcolor,
                            checkedColor = buttonColor
                        )
                    )
                    Text(
                        text = "Remember Me",
                        fontFamily = popfamilyreg,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 6.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Text(
                                text = "Forgot Password?",
                                fontFamily = popfamilyreg,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = forpassCol
                            )
                        }

                    }
                }
                Button(
                    onClick = {
                        onEvent(LoginEvent.OnLoginClicked)
                    },
                    colors = ButtonDefaults.buttonColors(buttonColor),
                    modifier = Modifier
                        .width(312.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(6.dp),
                    enabled = (state.login.text.isNotBlank() && state.password.text.isNotBlank()) && !state.isSuccessLogin
                ) {
                    Text(
                        text = "Login",
                        fontFamily = popfamilyreg,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, top = 36.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Lines()
                    Text(
                        text = "Or With",
                        fontFamily = popfamilyreg,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Lines()

                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    border = BorderStroke(1.dp, linegoogle),
                    modifier = Modifier
                        .width(312.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = "Google Logo",
                                modifier = Modifier
//                            .padding(end = 48.dp)
                                    .size(24.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Login with Google",
                                color = loggoogletxt,
                                fontFamily = popfamilyreg,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 48.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontFamily = popfamilyreg,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    TextButton(
                        onClick = navigateToRegister
                    ) {
                        Text(
                            text = "Sign Up",
                            color = buttonColor,
                            fontFamily = popfamilybold,
                            fontSize = 16.sp
                        )
                    }
                }

            }
        }
    )


}

@Composable
fun Lines() {
    Box(
        modifier = Modifier
            .width(width = 120.dp)
            .height(height = 1.dp)
            .background(color = line)
    )
}

// Function to validate email using regex
fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$",
        Pattern.CASE_INSENSITIVE
    )
    return emailPattern.matcher(email).matches()
}

// Function to validate password
fun isValidPassword(password: String): Boolean {
    val passwordPattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%_^&+=])(?=\\S+\$).{8,}\$"
    )
    return passwordPattern.matcher(password).matches()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEmailField(
    value: String,
    onValueChanged: (String) -> Unit,
    text: String,
    keyboardOptions: KeyboardOptions,
    isEmailValid: Boolean,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = text,
//                fontFamily = popfamilyreg,
                fontSize = 16.sp,
                color = textfilecol
            )
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        isError = !isEmailValid
    )
            if (!isEmailValid) {
                Text(
                    text = "Invalid Email Format",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    Color.Red
                )
            }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPasswordField(
    inputState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Password,
    onImeAction: () -> Unit = {},
    placeholder: String = "",
    label: String? = null,
    onValueChange : (String) -> Unit = { inputState.text = it },
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val popfamilyreg = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    if(label != null){
        Text(
            text = label,
            fontFamily = popfamilyreg,
            fontSize = 14.sp,
            color = Color(0xFF695C5C),
            modifier = modifier.padding(bottom = 6.dp)
        )
    }

    OutlinedTextField(
        value = inputState.text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        isError = inputState.displayErrors,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible }
            ) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )
            }
        },
        supportingText = {
            if(inputState.displayErrors){
                Text(text = inputState.errors.joinToString(separator = "\n"))
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen(
        state = LoginUiState(),
        onEvent = {},
    )
}