package com.example.alp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.alp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToBoarding: () -> Unit,
    navigateToHome: () -> Unit,
){
    val viewModel: SplashViewModel = hiltViewModel<SplashViewModel>()
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(true) {
        delay(2100)
        if(state.isAuthenticated){
            navigateToHome()
        } else {
            navigateToBoarding()
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = stringResource(
                id = R.string.app_name
            )
        )
    }
}