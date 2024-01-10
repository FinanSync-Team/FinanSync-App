package com.example.alp.ui.navigation

import android.Manifest
import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp.ui.screens.auth.boarding.OnboardingScreen
import com.example.alp.ui.screens.auth.login.LoginScreen
import com.example.alp.ui.screens.auth.login.LoginViewModel
import com.example.alp.ui.screens.auth.register.RegisterScreen
import com.example.alp.ui.screens.auth.register.RegisterViewModel
import com.example.alp.ui.screens.main.budgeting.BudgetingScreen
import com.example.alp.ui.screens.main.budgeting.BudgetingViewModel
import com.example.alp.ui.screens.finances.finance_list.FinanceListScreen
import com.example.alp.ui.screens.finances.finance_list.FinanceListViewModel
import com.example.alp.ui.screens.finances.manage_finance.ManageFinanceScreen
import com.example.alp.ui.screens.finances.manage_finance.ManageFinanceViewModel
import com.example.alp.ui.screens.finances.monthly_budget.MonthlyBudgetScreen
import com.example.alp.ui.screens.finances.monthly_budget.MonthlyBudgetViewModel
import com.example.alp.ui.screens.main.home.HomeScreen
import com.example.alp.ui.screens.main.home.HomeViewModel
import com.example.alp.ui.screens.main.profile.ProfileScreen
import com.example.alp.ui.screens.main.profile.ProfileUiState
import com.example.alp.ui.screens.main.profile.ProfileViewModel
import com.example.alp.ui.screens.main.split_bill.BillDetail
import com.example.alp.ui.screens.main.split_bill.SplitBill
import com.example.alp.ui.screens.splash.SplashScreen
import java.io.File

@Composable
fun AppNavigation(
    startDestination: String = Destinations.SPLASH_ROUTE,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val actions = remember(navController) {
        AppActions(navController)
    }
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(Destinations.SPLASH_ROUTE) {
            SplashScreen(navigateToBoarding = {
                navController.navigate(Destinations.BOARDING_ROUTE) {
                    popUpTo(Destinations.SPLASH_ROUTE) {
                        inclusive = true
                    }
                }
            }, navigateToHome = {
                navController.navigate(Destinations.HOME_ROUTE) {
                    popUpTo(Destinations.SPLASH_ROUTE) {
                        inclusive = true
                    }
                }
            })
        }

        composable(Destinations.BOARDING_ROUTE) {
            OnboardingScreen(
                navigateToLogin = {
                    navController.navigate(Destinations.LOGIN_ROUTE)
                },
                navigateToRegister = {
                    navController.navigate(Destinations.REGISTER_ROUTE)
                },
            )
        }

        composable(Destinations.LOGIN_ROUTE) {
            val viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()
            LoginScreen(
                navigateToRegister = {
                    navController.navigate(Destinations.REGISTER_ROUTE)
                },
                navigateToHome = {
                    navController.navigate(Destinations.HOME_ROUTE) {
                        launchSingleTop = true
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                state = state,
                onEvent = viewModel::onEvent,
            )
        }

        composable(Destinations.REGISTER_ROUTE) {
            val viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>()
            val state by viewModel.uiState.collectAsState()
            RegisterScreen(
                navigateToLogin = {
                    navController.navigate(Destinations.LOGIN_ROUTE)
                },
                navigateToLoginAfterRegister = {
                    navController.navigate(Destinations.LOGIN_ROUTE) {
                        popUpTo(Destinations.REGISTER_ROUTE) {
                            inclusive = true
                        }
                    }
                },
                state = state,
                onEvent = viewModel::onEvent,
            )
        }

        composable(Destinations.HOME_ROUTE) { entry ->
            val viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.uiState.collectAsState()
            val onRefresh = entry.savedStateHandle.get<Boolean>("refresh") ?: false
            HomeScreen(
                onRefresh = onRefresh,
                state = state,
                onEvent = viewModel::onEvent,
                navigateToFinances = {
                    navController.navigate(Destinations.FINANCES_ROUTE)
                },
            )
        }

        composable(Destinations.BUDGETING_ROUTE) { entry ->
            val onRefresh = entry.savedStateHandle.get<Boolean>("refresh") ?: false
            val viewModel: BudgetingViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            BudgetingScreen(
                onRefresh = onRefresh,
                state = state,
                onEvent = viewModel::onEvent,
                navigateToSetBudget = {
                    navController.navigate(Destinations.SET_BUDGET_ROUTE)
                },
            )
        }

        composable(Destinations.SET_BUDGET_ROUTE) {
            val viewModel: MonthlyBudgetViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            MonthlyBudgetScreen(
                state = state,
                onEvent = viewModel::onEvent,
                back = {
                    navController.popBackStack()
                },
                finishBack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                }
            )
        }

        composable(Destinations.CAMERA_ROUTE) {
//            var imageUri by remember { mutableStateOf<Uri?>(null) }
//            val activity = LocalContext.current as Activity
//
//            val takePictureLauncher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.TakePicture()
//            ) { success: Boolean ->
//                if (success) {
//
//                }
//            }
//            val permissionLauncher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.RequestPermission()
//            ) { isGranted: Boolean ->
//                if (isGranted) {
//                    val file = File(activity.externalCacheDir, "temp.jpg")
//                    imageUri = FileProvider.getUriForFile(activity, "${activity.packageName}.provider", file)
//                    takePictureLauncher.launch(imageUri)
//                }
//            }
//
//            LaunchedEffect(Unit) {
//                permissionLauncher.launch(Manifest.permission.CAMERA)
//            }
            //permissionLauncher.launch(Manifest.permission.CAMERA)

//            val file = File(LocalContext.current.externalCacheDir, "new_photo.jpg")
//            imageUri = FileProvider.getUriForFile(LocalContext.current, "${LocalContext.current.packageName}.provider", file)
//            imageUri?.let { uri ->
//                takePictureLauncher.launch(uri)
//            }
            SplitBill {
                navController.navigate(Destinations.SPLIT_BILL_DETAIL_ROUTE)
            }
        }

        composable(Destinations.SPLIT_BILL_DETAIL_ROUTE) {
            BillDetail()
        }

        composable(Destinations.BALANCE_ROUTE) {
            //HomeScreen()
        }

        composable(Destinations.PROFILE_ROUTE) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            ProfileScreen(state, onEvent = viewModel::onEvent, navigateToSetBudget= {
                navController.navigate(Destinations.SET_BUDGET_ROUTE)
            }) {
                navController.navigate(Destinations.BOARDING_ROUTE) {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }

        //fiance
        composable(Destinations.FINANCES_ROUTE) {entry ->
            val onRefresh = entry.savedStateHandle.get<Boolean>("refresh") ?: false
            val viewModel: FinanceListViewModel = hiltViewModel<FinanceListViewModel>()
            val state by viewModel.uiState.collectAsState()
            FinanceListScreen(
                onRefresh = onRefresh,
                state = state,
                onEvent = viewModel::onEvent,
                back = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                },
                navigateToManageFinance = {
                    navController.navigate(Destinations.MANAGE_FINANCE_ROUTE)
                },
                navigateToEditFinance = actions.navigateToEditFinance
            )
        }

        composable(Destinations.MANAGE_FINANCE_ROUTE) {
            val viewModel: ManageFinanceViewModel = hiltViewModel<ManageFinanceViewModel>()
            val state by viewModel.uiState.collectAsState()
            ManageFinanceScreen(
                state = state,
                onEvent = viewModel::onEvent,
                back = {
                    navController.popBackStack()
                },
                finishBack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${Destinations.MANAGE_FINANCE_ROUTE}/{${Params.FINANCE_ID}}",
            arguments = listOf(
                navArgument(Params.FINANCE_ID) {
                    type = NavType.IntType
                }
            )
        ){
            val viewModel: ManageFinanceViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            ManageFinanceScreen(
                state = state,
                onEvent = viewModel::onEvent,
                back = {
                    navController.popBackStack()
                },
                finishBack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle?.set("refresh", true)
                    navController.popBackStack()
                }
            )
        }
    }
}