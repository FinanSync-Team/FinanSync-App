package com.example.alp.ui.screens.main.split_bill

import android.widget.Space
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.ui.theme.PoppinsFontFamily
import com.example.alp.ui.theme.defblue
import com.example.alp.ui.theme.lightgray

@Composable
fun BillDetail() {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        item {
            DetailNavbar()
        }
        item {
            DetailSection()
        }
        item {
            ResultSection()
        }
    }
}

@Composable
fun DetailSection() {
    Column(modifier = Modifier.padding(horizontal = 40.dp, vertical = 18.dp)) {
        Text(
            text = "Bill Detail",
            color = defblue,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            fontFamily = PoppinsFontFamily
        )
        Text(
            text = "HAIDILAO",
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            fontFamily = PoppinsFontFamily
        )
        Text(
            text = "Bill Date: 08/02/2023 07:25",
            color = Color(0xFF888888),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = PoppinsFontFamily
        )
        Text(
            text = "Rp 258.792",
            color = defblue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            fontFamily = PoppinsFontFamily
        )
        ParticipantsCard()
    }
}

@Composable
fun ResultSection() {
    var isClicked by remember { mutableStateOf(true) }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp), color = lightgray
    )
    Column(modifier = Modifier.padding(horizontal = 40.dp, vertical = 18.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(top = 22.dp, bottom = 15.dp),
                text = "SPLIT RESULT",
                color = Color(0xFF888888),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily
            )
            Spacer(modifier = Modifier.width(210.dp))
            if (isClicked) {
                IconButton(onClick = {
                    isClicked = false
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_up),
                        contentDescription = "Up",
                        Modifier.padding(top = 20.dp)
                    )
                }
            } else if (!isClicked) {
                IconButton(onClick = {
                    isClicked = true
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = "Up",
                        Modifier.padding(top = 20.dp)
                    )
                }
            }
        }
        if (isClicked) {
            ParticipantTotal()
        }
    }
}

@Composable
fun ParticipantTotal() {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(45.dp)
            )
            Column {
                Text(
                    text = "Jerome's total",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Rp 72.299 ",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = defblue
                )
            }
        }
        Divider(thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OrderRow()
            PriceDetails()
        }
    }
}

@Composable
fun PriceDetails() {
    var isClicked by remember { mutableStateOf(false) }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Bill details",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(240.dp))
            if (isClicked) {
                IconButton(onClick = {
                    isClicked = false
                }, modifier = Modifier.size(15.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_up),
                        contentDescription = "Up"
                    )
                }
            } else if (!isClicked) {
                IconButton(
                    onClick = {
                        isClicked = true
                    }, Modifier.size(15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = "Up"
                    )
                }
            }
        }
        if (isClicked) {
            Divider(thickness = 0.5.dp, modifier = Modifier.padding(bottom = 10.dp))
            DetailRow("+ Tax", "6.572")
            DetailRow("+ Service Charge", "3.426")
            DetailRow("- Discount", "0")
            DetailRow("+ Others", "3.426")
        }
        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 22.dp))
    }
}

@Composable
fun DetailRow(sub: String, price: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            text = sub,
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            modifier = Modifier.width(120.dp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.width(120.dp))
        Text(
            text = price,
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            modifier = Modifier.width(70.dp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun OrderRow() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        Text(
            text = "Baby Buncis Cah Sapi Cincang",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            modifier = Modifier.width(150.dp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
        Text(
            text = "x1",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            modifier = Modifier.width(40.dp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.width(35.dp))
        Text(
            text = "29.500",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            modifier = Modifier.width(80.dp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ReminderButton() {
    OutlinedButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 10.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White, contentColor = defblue
        ),
        border = BorderStroke(1.dp, defblue)
    ) {
        Text(
            text = "Send Reminder",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ParticipantsCard() {
    var isClicked by remember { mutableStateOf(true) }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(top = 22.dp, bottom = 15.dp),
                text = "PARTICIPANTS (3)",
                color = Color(0xFF888888),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                fontFamily = PoppinsFontFamily
            )
            Spacer(modifier = Modifier.width(180.dp))
            if (isClicked) {
                IconButton(onClick = {
                    isClicked = false
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_up),
                        contentDescription = "Up",
                        Modifier.padding(top = 15.dp)
                    )
                }
            } else if (!isClicked) {
                IconButton(onClick = {
                    isClicked = true
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = "Up",
                        Modifier.padding(top = 15.dp)
                    )
                }
            }
        }
        if (isClicked) {
            ParticipantsRow()
            ReminderButton()
        }
    }
}

@Composable
fun ParticipantsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(40.dp)
        )
        Text(
            text = "Jerome",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(120.dp))
        Text(
            text = "Rp 72.299",
            fontFamily = PoppinsFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Divider(thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
}

@Composable
fun DetailNavbar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(130.dp))
        Text(
            text = "Split Bill",
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(100.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BillDetailPreview() {
    BillDetail()
}