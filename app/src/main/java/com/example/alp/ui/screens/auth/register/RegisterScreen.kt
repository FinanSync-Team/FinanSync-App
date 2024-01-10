package com.example.alp.ui.screens.auth.register

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.abstraction.Resource
import com.example.alp.abstraction.SnackbarMessage
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.components.TextFieldValidation
import com.example.alp.ui.screens.auth.login.CustomEmailField
import com.example.alp.ui.screens.auth.login.CustomPasswordField
import com.example.alp.ui.screens.auth.login.Lines
import com.example.alp.ui.screens.auth.login.LoginEvent
import com.example.alp.ui.screens.auth.login.isValidEmail
import com.example.alp.ui.screens.auth.login.isValidPassword
import com.example.alp.ui.theme.buttonColor
import com.example.alp.ui.theme.linegoogle
import com.example.alp.ui.theme.loggoogletxt
import com.example.alp.ui.theme.textfilecol

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit = {},
    navigateToLoginAfterRegister: () -> Unit = {},
    state: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
) {
    val popfamilybold = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
    val popfamilyreg = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    val snackbarHostState = remember { SnackbarHostState() }


    if (state.successRegister) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                SnackbarMessage.success("Register Success")
            )
            navigateToLoginAfterRegister()
        }
    }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
        onRetry = { onEvent(RegisterEvent.OnRegisterClicked) }
    )
    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item{
                    Text(
                        text = "Create an Account",
                        fontFamily = popfamilybold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    Text(
                        text = "Manage your financial today!",
                        fontFamily = popfamilyreg,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier.padding(top = 24.dp)
                    ) {

                        TextFieldValidation(
                            inputState = state.name,
                            label = "Fullname",
                            placeholder = "John Doe",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp, vertical = 12.dp),
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        )

                        TextFieldValidation(
                            inputState = state.username,
                            label = "Username",
                            placeholder = "john.doe",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                                .padding(bottom = 12.dp),
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        )

                        TextFieldValidation(
                            inputState = state.email,
                            label = "Email",
                            placeholder = "example@gmail.com",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                                .padding(bottom = 12.dp),
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        )

                        TextFieldValidation(
                            inputState = state.phoneNumber,
                            label = "Phone Number",
                            placeholder = "082123456789",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp)
                                .padding(bottom = 12.dp),
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                        )

                        CustomPasswordField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 36.dp),
                            label = "Password",
                            inputState = state.password,
                            imeAction = ImeAction.Done,
                            onImeAction = { onEvent(RegisterEvent.OnRegisterClicked) },
                            placeholder = "Password",
                        )


                    }
                    Spacer(modifier = Modifier.padding(12.dp))

                    Button(
                        onClick = {
                                  onEvent(RegisterEvent.OnRegisterClicked)
                        },
                        enabled = (state.resource !is Resource.Loading) && !state.successRegister,
                        colors = ButtonDefaults.buttonColors(buttonColor),
                        modifier = Modifier
                            .width(312.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(6.dp),

                        ) {
                        Text(
                            text = "Sign Up",
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
                        onClick = {},
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
                                    text = "Sign Up with Google",
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
                            text = "Already have an account?",
                            fontFamily = popfamilyreg,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        TextButton(
                            onClick = navigateToLogin
                        ) {
                            Text(
                                text = "Login",
                                color = buttonColor,
                                fontFamily = popfamilybold,
                                fontSize = 16.sp
                            )
                        }
                    }

                }
            }
        }
    )


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(
        navigateToLogin = {},
        state = RegisterUiState(),
        onEvent = {}
    )
}