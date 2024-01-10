package com.example.alp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alp.ui.components.BottomNavItem
import com.example.alp.ui.navigation.AppNavigation
import com.example.alp.ui.navigation.Destinations
import com.example.alp.ui.theme.ALPTheme
import com.example.alp.ui.theme.Blue50
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    val buttonNavMenu = BottomNavItem().getItems()
                    val buttonNavMenuRotes = buttonNavMenu.map { it.route }
                    val backStackEntry = navController.currentBackStackEntryAsState()
                    val bottomNavVisibleTo =
                        navController.currentBackStackEntryAsState().value?.destination?.route in buttonNavMenuRotes.map { it }
                    var imageUri by remember { mutableStateOf<Uri?>(null) }
                    val activity = LocalContext.current as Activity

                    val takePictureLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.TakePicture()
                    ) { success: Boolean ->
                        if (success) {
                            navController.navigate(Destinations.CAMERA_ROUTE) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                    val permissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean ->
                        if (isGranted) {
                            val file = File(activity.externalCacheDir, "temp.jpg")
                            imageUri = FileProvider.getUriForFile(activity, "${activity.packageName}.provider", file)
                            takePictureLauncher.launch(imageUri)
                        }
                    }
                    Scaffold(
                        floatingActionButtonPosition = FabPosition.Center,
                        floatingActionButton = {
                            Box(){
                                AnimatedVisibility(
                                    visible = bottomNavVisibleTo,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    FloatingActionButton(
                                        onClick = {
                                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                                        },
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(50.dp)
                                            .offset(y = 50.dp)
                                            .border(BorderStroke(3.dp, Color.White), CircleShape),
                                        containerColor = Color(0xFF608EE9),
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_camera),
                                            contentDescription = null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }

                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = bottomNavVisibleTo,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {

                                NavigationBar(
                                    containerColor = Color.White,
                                    contentColor = Blue50,
                                    tonalElevation = 12.dp,

                                ) {
                                    buttonNavMenu.forEach { item ->
                                        if (item.route != Destinations.CAMERA_ROUTE) {
                                            NavigationBarItem(
                                                selected = item.route == backStackEntry.value?.destination?.route,
//                                            label = {
//                                                Text(item.name)
//                                            },
                                                colors = NavigationBarItemDefaults.colors(
                                                    // Custom icon and text color for the selected item
                                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                                    indicatorColor = Color.White,
                                                    // Custom icon and text color for the unselected item
                                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                                ),
                                                icon = {
                                                    Icon(
                                                        painter = painterResource(id = item.icon),
                                                        contentDescription = item.name
                                                    )
                                                },
                                                onClick = {
                                                    item.route?.let {
                                                        navController.navigate(item.route) {
                                                            popUpTo(navController.graph.findStartDestination().id) {
                                                                saveState = true
                                                            }
                                                            launchSingleTop = true
                                                            restoreState = true
                                                        }
                                                    }
                                                }
                                            )
                                        } else {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        },
                    ) { innerPadding ->
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}