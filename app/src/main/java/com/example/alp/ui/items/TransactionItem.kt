package com.example.alp.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alp.R
import com.example.alp.core.model.FinanceModel

@Composable
fun TransactionItem(transaction: FinanceModel, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick
                       },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF0F6F5))
                .padding(10.dp)
        ) {
            val logo = when (transaction.source) {
                "BCA" -> R.drawable.ic_bca
                "OVO" -> R.drawable.ic_ovo
                else -> R.drawable.ic_income
            }
            Image(
                painter = painterResource(id = logo),
                contentDescription = "Transaction Icon",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = transaction.humanDiff,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(end=6.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(if (transaction.type == "Expense") Color(0xFFFFE7E7) else Color(0xFFE7F3FF))
                .padding(7.dp)

        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = if (transaction.type == "Expense") R.drawable.ic_up else R.drawable.ic_down),
                contentDescription = "Transaction Icon",
                tint = Color.Unspecified
            )
        }

        Text(
            text = transaction.formattedAmount,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionItemPreview() {
    TransactionItem(
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
        ),
        onClick = {}
    )
}