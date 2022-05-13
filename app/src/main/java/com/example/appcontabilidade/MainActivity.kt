package com.example.appcontabilidade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcontabilidade.screens.AddEditClientInfoScreen
import com.example.appcontabilidade.screens.AddPaymentPurchaseScreen
import com.example.appcontabilidade.screens.ClientListScreen
import com.example.appcontabilidade.ui.theme.AppContabilidadeTheme
import com.example.appcontabilidade.viewmodel.ClientInfoViewModel
import com.example.appcontabilidade.viewmodel.ClientListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val clientListViewModel: ClientListViewModel by viewModels()
        val addEditClientInfoViewModel: ClientInfoViewModel by viewModels()

        setContent {
            AppContabilidadeTheme {
                AppContabilidade(
                    clientListViewModel,
                    addEditClientInfoViewModel
                )
            }
        }
    }
}

@Composable
fun AppContabilidade(
    clientListViewModel: ClientListViewModel,
    clientInfoViewModel: ClientInfoViewModel
) {
    val navController = rememberNavController()

    Scaffold {
        NavHost(navController = navController, startDestination = "clientlist") {
            composable(route = "clientlist") {
                ClientListScreen(navController, clientListViewModel)
            }

            composable(
                route = "addeditclientinfo?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                val clientEdit = clientListViewModel.getClient(id)

                AddEditClientInfoScreen(
                    navController,
                    clientInfoViewModel,
                    clientEdit,
                    clientListViewModel::addClient,
                    clientListViewModel::updateClient
                )
            }

            composable(
                route = "addpaymentpurchase/{id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                val clientEdit = clientListViewModel.getClient(id)

                AddPaymentPurchaseScreen(
                    navController,
                    clientInfoViewModel,
                    clientEdit,
                    clientListViewModel::updateClient
                )
            }
        }
    }
}



