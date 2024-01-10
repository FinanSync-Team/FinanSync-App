package com.example.alp.ui.screens.main.split_bill

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.ui.theme.PoppinsFontFamily
import com.example.alp.ui.theme.defblue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitBill(
    modifier: Modifier = Modifier,
    navigateToDetail: () -> Unit = {},
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .background(Color.White)) {
        item {
            MainNavbar()
        }
        item {
            ActionBar(navigateToDetail = navigateToDetail)
        }
    }
}

@Composable
fun MainNavbar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.ic_menu), contentDescription = "Menu")
        }
        Spacer(modifier = Modifier.width(95.dp))
        Text(
            text = "Split Bill",
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(95.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications"
            )
        }
    }
}

@Composable
fun ActionBar(
    navigateToDetail: () -> Unit = {}
) {
    var activeClicked by remember { mutableStateOf(false) }
    var historyClicked by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 42.dp, end = 42.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .size(200.dp)
                    .background(
                        color = Color.LightGray, shape = RoundedCornerShape(40.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                if (activeClicked) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    activeClicked = true
                                    historyClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = defblue
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.LightGray, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    historyClicked = true
                                    activeClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HISTORY",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray
                            )
                        }
                    }
                } else if (historyClicked) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.LightGray, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    activeClicked = true
                                    historyClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    historyClicked = true
                                    activeClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HISTORY",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = defblue
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    activeClicked = true
                                    historyClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = defblue
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(width = 130.dp, height = 35.dp)
                                .background(
                                    color = Color.LightGray, shape = RoundedCornerShape(40.dp)
                                )
                                .clickable {
                                    historyClicked = true
                                    activeClicked = false
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HISTORY",
                                fontFamily = PoppinsFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
        if (activeClicked) {
            ActiveFrame(navigateToDetail = navigateToDetail)
        } else if (historyClicked) {
            HistoryFrame(
                navigateToDetail = navigateToDetail
            )
        } else {
            ActiveFrame(navigateToDetail = navigateToDetail)
        }
    }
}

@Composable
fun ActiveFrame(
    navigateToDetail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "YOU HAVE 44 ACTIVE BILLS",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 25.dp),
            fontSize = 14.sp
        )
        ActiveCard(
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun ActiveCard(
    navigateToDetail: () -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(0.5.dp, Color.LightGray), shape = RoundedCornerShape(5.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp)
                ) {
                    Text(
                        text = "HAIDILAO",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily
                    )
                    Text(
                        text = "08/02/2023 07:25",
                        color = Color(0xFF888888),
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        fontFamily = PoppinsFontFamily,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Rp 200.781",
                        color = defblue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        fontFamily = PoppinsFontFamily,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Divider(
                        thickness = 0.5.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(85.dp)) {
                        Text(
                            text = "11 items • 6 persons",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 10.sp,
                            color = Color(0xFF888888)
                        )
                        Button(
                            onClick = navigateToDetail,
                            modifier = Modifier
                                .width(85.dp)
                                .height(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                defblue
                            ), contentPadding = PaddingValues(5.dp)
                        ) {
                            Text(
                                text = "See Detail",
                                fontFamily = PoppinsFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryFrame(
    navigateToDetail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "DECEMBER 2023",
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 25.dp),
            fontSize = 16.sp
        )
        HistoryCard(
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun HistoryCard(
    navigateToDetail: () -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(0.5.dp, Color.Gray),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp)
                ) {
                    Text(
                        text = "YOSHINOYA",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = PoppinsFontFamily
                    )
                    Text(
                        text = "08/02/2023 07:25",
                        color = Color(0xFF888888),
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        fontFamily = PoppinsFontFamily,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Rp 121.381",
                        color = defblue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        fontFamily = PoppinsFontFamily,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Divider(
                        thickness = 0.5.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = "3 items • 2 persons",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 11.sp,
                            color = Color(0xFF888888)
                        )
                        Button(
                            onClick = navigateToDetail,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .padding(start = 50.dp),
                            colors = ButtonDefaults.buttonColors(
                                defblue
                            )
                        ) {
                            Text(
                                text = "See Detail",
                                fontFamily = PoppinsFontFamily,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplitBillPreview() {
    SplitBill()
}