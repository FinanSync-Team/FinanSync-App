package com.example.alp.ui.screens.auth.boarding

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.ui.theme.buttonColor
import com.example.alp.ui.theme.textGede

@Composable
fun OnboardingScreen(
    navigateToLogin: () -> Unit = {},
    navigateToRegister: () -> Unit = {}
) {

    val popfamilybold = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
    val popfamilyreg = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item{
            Image(
                painter = painterResource(id = R.drawable.onboarding),
                contentDescription = "Content",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                contentScale = ContentScale.Crop
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 36.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextGede(
                    text = "Spend Smarter",
                    modifier = Modifier.padding(top = 0.dp)
                )
                TextGede(
                    text = "Save More",
                    modifier = Modifier
                )

                Button(
                    onClick = navigateToRegister,
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .height(45.dp)
                        .width(305.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(buttonColor),

                    ) {
                    Text(
                        text = "Get Started",
//                    modifier = Modifier.padding(horizontal = 84.dp)
                        //        fontFamily = popfamilyreg

                    )
                }
                Row {
                    Text(
                        text = "Already Have Account?",
                        //        fontFamily = popfamilyreg,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 13.dp)

                    )
                    TextButton(
                        onClick = navigateToLogin,
                    ) {
                        Text(
                            text = "Log In",
                            fontFamily = popfamilybold,
                            fontSize = 14.sp,
                            color = buttonColor,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


}

@Composable
fun TextGede(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        fontSize = 36.sp,
        fontWeight = FontWeight.SemiBold,
//        fontFamily = popfamilyreg
        color = textGede,
        modifier = modifier
    )

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen()
}