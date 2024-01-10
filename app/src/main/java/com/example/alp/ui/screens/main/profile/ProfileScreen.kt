package com.example.alp.ui.screens.main.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alp.R
import com.example.alp.abstraction.Resource
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.DefaultTopBar
import com.example.alp.ui.components.LoadingBar
import com.example.alp.ui.components.SnackbarOnError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit,
    navigateToSetBudget: () -> Unit = {},
    navigateToBoarding: () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = state.returnFromLogout){
        if(state.returnFromLogout){
            navigateToBoarding()
        }
    }

    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
    )
    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
    ) { pad ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pad)
                .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.resource is Resource.Loading){
                item{
                    LoadingBar()
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                    Image(
                        painter = painterResource(id = R.drawable.img_profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(shape = CircleShape)
                    )
                    Text(text = state.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "@${state.username}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF438883)
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    profileMenu(
                        title = "Invite Friends",
                        image = R.drawable.ic_diamond
                    )
                    Divider(modifier = Modifier.padding(horizontal = 10.dp))
                    profileMenu(title = "Monthly Budgeting", icon = Icons.Default.Money, action = navigateToSetBudget)
                    profileMenu(title = "Account Info", icon = Icons.Default.AccountCircle)
                    profileMenu(title = "Personal profile", icon = Icons.Default.SupervisorAccount)
                    profileMenu(title = "Message center", icon = Icons.Default.Mail)
                    profileMenu(title = "Login and security", icon = Icons.Default.Shield)
                    profileMenu(title = "Login and security", icon = Icons.Default.Lock)
                    profileMenu(title = "Logout", icon = Icons.Default.Logout, action = {
                        onEvent(ProfileEvent.Logout)
                    })
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

}

@Composable
private fun profileMenu(
    title: String,
    image: Int? = null,
    icon: ImageVector = Icons.Default.Logout,
    action: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(horizontal = 10.dp)
            .clickable { action() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (image != null) {
            Image(painter = painterResource(id = image), contentDescription = title)
        } else {
            Icon(icon, contentDescription = title)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(ProfileUiState(name = "Wuwung Sebastian", username = "@julius.sw"), onEvent={}) {}
}