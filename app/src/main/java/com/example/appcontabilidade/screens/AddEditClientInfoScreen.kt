package com.example.appcontabilidade.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcontabilidade.types.ClientType
import com.example.appcontabilidade.viewmodel.ClientInfoViewModel

@Composable
fun AddEditClientInfoScreen(
    navController: NavController,
    addEditClientInfoViewModel: ClientInfoViewModel,
    clientEdit: ClientType,
    onInsertClient: (ClientType) -> Unit,
    onUpdateClient: (ClientType) -> Unit
) {
    addEditClientInfoViewModel.changeName(clientEdit.name)
    addEditClientInfoViewModel.changeAddress(clientEdit.address)
    addEditClientInfoViewModel.changeNumber(clientEdit.number)
    addEditClientInfoViewModel.changeDebt(clientEdit.debt)

    AddEditClientInfoForm(
        addEditClientInfoViewModel,
        navController,
        clientEdit,
        onInsertClient,
        onUpdateClient
    )
}

@Composable
fun AddEditClientInfoForm(
    addEditClientInfoViewModel: ClientInfoViewModel,
    navController: NavController,
    clientEdit: ClientType,
    onInsertClient: (ClientType) -> Unit,
    onUpdateClient: (ClientType) -> Unit
) {
    val name = addEditClientInfoViewModel.name.observeAsState()
    val address = addEditClientInfoViewModel.address.observeAsState()
    val number = addEditClientInfoViewModel.number.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Gray,
                focusedBorderColor = LightGray,
                unfocusedLabelColor = Gray,
                focusedLabelColor = LightGray,
                cursorColor = LightGray
            ),
            value =  "${name.value}",
            label = {
                Text(text = "Name")
            },
            onValueChange = { newName ->
                addEditClientInfoViewModel.changeName(newName)
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            label = {
                Text(text = "Address")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Gray,
                focusedBorderColor = LightGray,
                unfocusedLabelColor = Gray,
                focusedLabelColor = LightGray,
                cursorColor = LightGray
            ),
            value =  "${address.value}",
            onValueChange = { newAddress ->
                addEditClientInfoViewModel.changeAddress(newAddress)
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            label = {
                    Text(text = "Number")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Gray,
                focusedBorderColor = LightGray,
                unfocusedLabelColor = Gray,
                focusedLabelColor = LightGray,
                cursorColor = LightGray
            ),
            value =  "${number.value}",
            onValueChange = { newNumber ->
                addEditClientInfoViewModel.changeNumber(newNumber)
            }
        )

        addEditClientInfoViewModel.changeDebt(clientEdit.debt)

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .size(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
            onClick = {
                if (clientEdit.id == -1)
                    addEditClientInfoViewModel.addClient(onInsertClient)
                else
                    addEditClientInfoViewModel.updateClient(clientEdit.id, onUpdateClient)

                navController.navigate("clientlist") {
                    popUpTo("clientlist") {
                        inclusive = true
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirm add",
                tint = Color.White
            )
        }
    }
}