package com.example.alp.ui.screens.main.home

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.model.FrequentlyUsedModel
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.LoadingListItem
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.components.shimmerEffect
import com.example.alp.ui.items.FrequentlyUsedActionItem
import com.example.alp.ui.items.TransactionItem
import com.example.alp.ui.theme.Blue50
import com.example.alp.ui.theme.Blue600
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRefresh: Boolean = false,
    state: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    navigateToFinances: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(onRefresh) {
        if(onRefresh){
            onEvent(HomeEvent.Refresh)
        }
    }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
        onRetry = { onEvent(HomeEvent.Refresh) }
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                name = state.name,
                email = state.email,
                onCloseClicked = {
                    scope.launch { drawerState.close() }
                },
                onItemClicked = {
                    scope.launch { drawerState.close() }
                })
        },
        scrimColor =  DrawerDefaults.scrimColor
    ) {
        Scaffold(
            snackbarHost = { CustomSnackbarHost(snackbarHostState) },
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    item{
                        ToolbarHome(
                            leftIconDrawable = R.drawable.ic_menu,
                            leftIconClicked = {
                                scope.launch { drawerState.open() }
                            },
                            title = "Home",
                            rightIconDrawable = R.drawable.ic_notification
                        )
                    }
                    item{
                        Greeting(name = state.name)
                    }
                    item {
                        BalanceSection(state = state)
                    }
                    item {
                        Spacer(modifier = Modifier.height(21.dp))
                        IncomeExpenseSection(state = state)
                    }
                    item {
                        Spacer(modifier = Modifier.height(21.dp))
                        FrequentlyUsedActions()
                    }
                    if(state.resource is Resource.Loading){
                        items(4){
                            Spacer(modifier = Modifier.height(21.dp))
                            LoadingListItem()
                        }
                    } else {
                        item {
                            Spacer(modifier = Modifier.height(21.dp))
                            TransactionsHistory(
                                state = state,
                                navigateToFinances = navigateToFinances
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun BalanceSection(state: HomeUiState) {
    val isLoading = state.resource is Resource.Loading
    val balanceText = if (isLoading) " " else state.balance // Placeholder text for loading state

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Blue50, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Current Balance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Normal, color = Blue600)
            if(isLoading){
                Box(modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
                )
            } else {
                Text(modifier = Modifier.padding(top = 3.dp),
                    text = balanceText,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0077B6)
                )
            }

        }
    }
}

@Composable
fun IncomeExpenseSection(state: HomeUiState) {
    val isLoading = state.resource is Resource.Loading
    Row(modifier = Modifier.fillMaxWidth()) {
        FinancialItem(
            modifier = Modifier.weight(1f),
            isLoading = isLoading,
            title = "Net Income",
            amount = state.income,
            iconPainter = painterResource(id = R.drawable.ic_income),
            iconTint = Color.Red
        )
        Spacer(modifier = Modifier.width(12.dp))
        FinancialItem(
            modifier = Modifier.weight(1f),
            isLoading = isLoading,
            title = "Expenditure",
            amount = state.expense,
            iconPainter = painterResource(id = R.drawable.ic_income),
            iconTint = Color.Blue
        )
    }
}

@Composable
fun FinancialItem(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    isLoading: Boolean = true,
    iconPainter: Painter,
    iconTint: Color,
    backgroundColor: Color = Color.White,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation =  CardDefaults.cardElevation(
            3.dp
        ),
        border = BorderStroke(1.dp, Color(0xFFEAE9F0)),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = iconTint.copy(alpha = 0.2f))
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                if(isLoading){
                    Box(modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                    )
                } else {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.labelMedium,
                        color = contentColor,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Composable
private fun Greeting(name: String) {
    val calendar = Calendar.getInstance()
    val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

    val greeting = when (timeOfDay) {
        in 0..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        in 17..23 -> "Good evening"
        else -> "Hello"
    }

    Column {
        Text(text = "$greeting,", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 24.dp))
        Text(text = name, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun FrequentlyUsedActions() {
    Column {
        Text(
            text = "Frequently Used",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val freqs = listOf(
            FrequentlyUsedModel(
                icon = R.drawable.ic_phone,
                name = "Mobile Recharge",
                color = Color(0x1438D79F)
            ),
            FrequentlyUsedModel(
                icon = R.drawable.ic_bill,
                name = "Bill Payments",
                color = Color(0x14FF6E66)
            ),
            FrequentlyUsedModel(
                icon = R.drawable.ic_transfer,
                name = "Bank Transfer",
                color = Color(0x14FFC633)
            ),
            FrequentlyUsedModel(
                icon = R.drawable.ic_request_money,
                name = "Request Money",
                color = Color(0x143642DA)
            ),
            FrequentlyUsedModel(
                icon = R.drawable.ic_chart,
                name = "Transaction History",
                color = Color(0x14DA36CA)
            ),
        )
        LazyRow {
            items(freqs) { freq ->
                FrequentlyUsedActionItem(freq)
            }
        }
    }
}


@Composable
fun TransactionsHistory(
    state: HomeUiState,
    navigateToFinances: () -> Unit = {}
) {
    Column{
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transactions History",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = navigateToFinances,
                contentPadding = PaddingValues(0.dp) // Remove padding for tighter hit target
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.finances.forEach { transaction ->
            TransactionItem(transaction, onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun DrawerContent(
    name: String,
    email: String,
    onItemClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(end = 50.dp)
        .background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF272949))
                .padding(vertical = 16.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(51.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = name, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                Text(text = email, style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onCloseClicked,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close Drawer", tint = Color.White)
            }

        }
        Divider()

        // Drawer items
        DrawerButton(
            modifier = Modifier.padding(top = 18.dp),
            icon = Icons.Default.Analytics,
            label = "My Statistics",
            onItemClick = { onItemClicked("My Statistics") })
        DrawerButton(
            icon = Icons.Default.Analytics,
            label = "Invite Friends",
            onItemClick = { onItemClicked("Invite Friends") })
        DrawerButton(
            icon = Icons.Default.Analytics,
            label = "Settings",
            onItemClick = { onItemClicked("Settings") })
        DrawerButton(
            icon = Icons.Default.Analytics,
            label = "Discover",
            onItemClick = { onItemClicked("Discover") })
        DrawerButton(
            icon = Icons.Default.Analytics,
            label = "Report",
            onItemClick = { onItemClicked("Report") })
        DrawerButton(
            icon = Icons.Default.Analytics,
            label = "Reminder",
            onItemClick = { onItemClicked("Reminder") })
    }
}

@Composable
fun DrawerButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = modifier
    ){
        TextButton(
            onClick = onItemClick,
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF2F394E))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.titleMedium, color = Color(0xFF2F394E))
        }
        Divider(modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 9.dp)
            .padding(start = 30.dp))
    }

}

@Composable
fun ToolbarHome(
    leftIconDrawable: Int,
    leftIconClicked: () -> Unit = {},
    title: String,
    rightIconDrawable: Int,
    rightIconClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    leftIconClicked()
                },
            painter = painterResource(id = leftIconDrawable),
            contentDescription = "Left Icon"
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.weight(1f))

        Image(
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    rightIconClicked()
                },
            painter = painterResource(id = rightIconDrawable),
            contentDescription = "Right Icon"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val finances: List<FinanceModel> = listOf(
        FinanceModel(
            id = 1,
            name = "Makan Soto",
            amount = 100000,
            formattedAmount = "Rp 100.000",
            type = "Expense",
            createdAt = "2021-10-19T23:25:00.000Z",
            updatedAt = "2021-10-19T23:25:00.000Z",
            humanDiff = "2 hours ago",
            category = "Food",
            source = "BCA"
        )
    )
    HomeScreen(
        state = HomeUiState(
            name= "Lorem Ipsum",
            balance = "Rp 3.657.000",
            income = "Rp 3.000.000",
            expense = "Rp 657.000",
            finances = finances
        ),
        onEvent = {}
    )
}