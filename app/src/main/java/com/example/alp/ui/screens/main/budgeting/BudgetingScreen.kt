package com.example.alp.ui.screens.main.budgeting

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp.R
import com.example.alp.core.model.DetailProgressBudgetingModel
import com.example.alp.core.model.MonthlyBudgetingModel
import com.example.alp.core.model.ProgressBudgetingModel
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.screens.main.home.DrawerContent
import com.example.alp.ui.screens.main.home.ToolbarHome
import com.example.alp.utils.rupiahFormat
import kotlinx.coroutines.launch


data class PieChartData(val label: String, val value: Float, val color: Color, val logo: Int)

@Composable
fun PieChart(data: List<PieChartData>, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        var total = 0f
        data.forEach { datum ->
            total += datum.value
        }
        var startAngle = 0f
        data.forEach { datum ->
            val sweepAngle = (datum.value / total) * 360f
            drawArc(
                color = datum.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                size = Size(size.width - 80f, size.height - 80f),
                topLeft = Offset(40f, 40f),
                style = Stroke(width = 60f)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun ChartLegend(data: List<PieChartData>, modifier: Modifier = Modifier) {
    Column(modifier) {
        data.forEach { datum ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(datum.color.copy(alpha = 0.2f))
                        .padding(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = datum.logo),
                        contentDescription = "Transaction Icon",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = datum.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF908BA6)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetingScreen(
    onRefresh: Boolean = false,
    state: BudgetingUiState,
    onEvent: (BudgetingEvent) -> Unit,
    navigateToSetBudget: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false) }
    LaunchedEffect(onRefresh) {
        if (onRefresh) {
            onEvent(BudgetingEvent.Refresh)
        }
    }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
        onRetry = { onEvent(BudgetingEvent.Refresh) }
    )
    ConfirmBudgeting(
        title = "Budget not setup yet",
        message = "Your monthly budget is not setup yet, do you want to set it now?",
        openDialog = openDialog,
        onConfirm = navigateToSetBudget
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
        scrimColor = DrawerDefaults.scrimColor
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
                    item {
                        ToolbarHome(
                            leftIconDrawable = R.drawable.ic_menu,
                            leftIconClicked = {
                                scope.launch { drawerState.open() }
                            },
                            title = "Budgeting",
                            rightIconDrawable = R.drawable.ic_notification
                        )
                        Row(
                            modifier = Modifier.padding(top = 24.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(state.chart != null){
                                val data = state.chart.convertToPieChartModel()
                                PieChart(data = data, modifier = Modifier.size(150.dp))
                                Spacer(modifier = Modifier.width(40.dp))
                                ChartLegend(data = data, modifier = Modifier.weight(1f))
                            }
                        }

                        if(state.monthlyBudgeting != null){
                            if(state.monthlyBudgeting.budget <= 0){
                                openDialog.value = true
                            }
                            BudgetCard(
                                modifier = Modifier.padding(top = 24.dp),
                                budgeting = state.monthlyBudgeting
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        state.progress.forEach{
                            BudgetProgress(
                                modifier = Modifier.padding(bottom = 24.dp),
                                progressBudgetingModel = it
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun ConfirmBudgeting(
    title: String,
    message: String,
    openDialog: MutableState<Boolean> = remember { mutableStateOf(false) },
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmText: String = "Yes, set budget",
    dismissText: String = "No",
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismiss()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text(dismissText)
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BudgetingScreenPreview() {
    BudgetingScreen(
        state = BudgetingUiState(
            progress = listOf(
                ProgressBudgetingModel(
                    category = "Transport",
                    total = 500_000,
                    detail = listOf(
                        DetailProgressBudgetingModel(
                            category = "Transport",
                            title = "Grab",
                            total = 200_000,
                        ),
                        DetailProgressBudgetingModel(
                            category = "Transport",
                            title = "Gojek",
                            total = 100_000,
                        ),
                        DetailProgressBudgetingModel(
                            category = "Transport",
                            title = "Other",
                            total = 100_000,
                        ),
                    )
                )
            )
        ),
        onEvent = {},
    )
}

@Composable
fun BudgetCard(
    modifier: Modifier = Modifier,
    budgeting: MonthlyBudgetingModel
) {
    val leftAmount = budgeting.leftBudget.toFloat()
    val budget = budgeting.budget.toFloat()
    val progress = if (leftAmount > 0) leftAmount / budget else 100f

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BudgetText(
                    modifier = Modifier.weight(1f),
                    title = "Left to spend",
                    amount = rupiahFormat(leftAmount.toInt()),
                    textAlign = TextAlign.Start
                )
                BudgetText(
                    modifier = Modifier.weight(1f),
                    title = "Monthly budget",
                    amount = rupiahFormat(budget.toInt()),
                    textAlign = TextAlign.End
                )
            }
            BudgetProgressBar(progress = progress)
        }
    }
}

@Composable
fun BudgetText(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Column(modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = textAlign
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = amount,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            textAlign = textAlign
        )
    }
}

@Composable
fun BudgetProgressBar(
    progress: Float,
    color: Color = Color(0xFF1EB980)
) {
    val backgroundColor = Color.LightGray

    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp)),
        trackColor = backgroundColor,
        color = color
    )
}


@Composable
fun BudgetProgress(
    modifier: Modifier = Modifier,
    progressBudgetingModel: ProgressBudgetingModel
) {
    val color = when (progressBudgetingModel.category) {
        "Transport" -> Color(0xFF6347EB)
        "Bills" -> Color(0xFFF46040)
        "Food" -> Color(0xFF2B87E3)
        else -> Color(0xFF2BE368)
    }
    val icon = when (progressBudgetingModel.category) {
        "Transport" -> R.drawable.ic_transport
        "Bills" -> R.drawable.ic_bills
        "Food" -> R.drawable.ic_food
        else -> R.drawable.ic_other
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color.copy(alpha = 0.2f))
                        .padding(10.dp)

                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = icon),
                        contentDescription = "Transaction Icon",
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = progressBudgetingModel.category,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal, fontSize = 20.sp),
                    color = Color(0xFF2C2646)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = rupiahFormat(progressBudgetingModel.total),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal, fontSize = 20.sp),
                    color = Color(0xFFAEABC2)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            progressBudgetingModel.detail.forEach{
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                            color = Color(0xFF2C2646)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = rupiahFormat(it.total),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                            color = Color(0xFF2C2646)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    val percentage = it.total.toFloat() / progressBudgetingModel.total.toFloat()
                    BudgetProgressBar(progress = percentage, color=color)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun previewBudgetProgress() {
    BudgetProgress(
        progressBudgetingModel = ProgressBudgetingModel(
            category = "Transport",
            total = 500_000,
            detail = listOf(
                DetailProgressBudgetingModel(
                    category = "Transport",
                    title = "Grab",
                    total = 200_000,
                ),
                DetailProgressBudgetingModel(
                    category = "Transport",
                    title = "Gojek",
                    total = 100_000,
                ),
                DetailProgressBudgetingModel(
                    category = "Transport",
                    title = "Other",
                    total = 100_000,
                ),
            )
        )
    )
}

