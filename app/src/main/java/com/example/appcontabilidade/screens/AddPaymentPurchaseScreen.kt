package com.example.appcontabilidade.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcontabilidade.types.ClientType
import com.example.appcontabilidade.viewmodel.ClientInfoViewModel
import java.text.DecimalFormat

@Composable
fun AddPaymentPurchaseScreen(
    navController: NavController,
    addEditClientInfoViewModel: ClientInfoViewModel,
    clientEdit: ClientType,
    onUpdateClient: (ClientType) -> Unit
) {
    addEditClientInfoViewModel.changeName(clientEdit.name)
    addEditClientInfoViewModel.changeAddress(clientEdit.address)
    addEditClientInfoViewModel.changeNumber(clientEdit.number)
    addEditClientInfoViewModel.changeDebt(clientEdit.debt)

    AddPaymentPurchaseForm(
        addEditClientInfoViewModel,
        navController,
        clientEdit,
        onUpdateClient
    )
}

@Composable
fun AddPaymentPurchaseForm(
    addPaymentPurchaseClientInfoViewModel: ClientInfoViewModel,
    navController: NavController,
    clientEdit: ClientType,
    onUpdateClient: (ClientType) -> Unit
) {
    val formatter = DecimalFormat("#0.00")

    var newDebt by remember {
        mutableStateOf(0.00)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            text = "Current debt: $${formatter.format(clientEdit.debt)}",
            style = MaterialTheme.typography.h5
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.LightGray,
                unfocusedLabelColor = Color.Gray,
                focusedLabelColor = Color.LightGray,
                cursorColor = Color.LightGray
            ),
            value =  "$newDebt",
            label = {
                Text(text = "Value")
            },
            onValueChange = { changedDebt ->
                newDebt = changedDebt.toDouble();
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                onClick = {
                    val toSave = newDebt.plus(clientEdit.debt)

                    addPaymentPurchaseClientInfoViewModel.changeDebt(toSave)

                    addPaymentPurchaseClientInfoViewModel.updateClient(clientEdit.id, onUpdateClient)

                    navController.navigate("clientlist") {
                        popUpTo("clientlist") {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Confirm purchase",
                    tint = Color.White
                )
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                onClick = {
                    val toSave = clientEdit.debt.minus(newDebt)

                    if (toSave > 0) {
                        addPaymentPurchaseClientInfoViewModel.changeDebt(toSave)

                        addPaymentPurchaseClientInfoViewModel.updateClient(clientEdit.id, onUpdateClient)

                        navController.navigate("clientlist") {
                            popUpTo("clientlist") {
                                inclusive = true
                            }
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Confirm payment",
                    tint = Color.White
                )
            }
        }


    }
}