package com.example.appcontabilidade.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appcontabilidade.types.ClientType
import com.example.appcontabilidade.viewmodel.ClientListViewModel
import java.text.DecimalFormat

@Composable
fun ClientListScreen(
    navController: NavController,
    clientListViewModel: ClientListViewModel
) {
    Scaffold(
        backgroundColor = Color.Gray,
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.DarkGray,
                onClick = {
                    navController.navigate("addeditclientinfo")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Client add",
                    tint = Color.White,
                )
            }
        }
    ) {
        val clientList by clientListViewModel.clientList.observeAsState(listOf())
        val filter by clientListViewModel.filterBy.observeAsState("")

        Column {
            SearchClient(filter, clientListViewModel::changeFilterBy)
            ClientList(clientList, navController, clientListViewModel)
        }
    }
}

@Composable
fun SearchClient(
    term: String,
    onTermChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.DarkGray,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.LightGray,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.DarkGray
        ),
        label = {
            Row {
                Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = "Search term"
                )
                
                Text(text = "Filter")
            }
        },
        value = term,
        onValueChange = onTermChange
    )
}

@Composable
fun ClientList(
    clientList: List<ClientType>,
    navController: NavController,
    clientListViewModel: ClientListViewModel
) {
    LazyColumn {
        items(clientList) { client ->
                ClientEntry(
                    client = client,
                    onEditClient = {
                        navController.navigate("addeditclientinfo?id=${client.id}")
                    },
                    onDeleteClient = {
                        clientListViewModel.deleteClient(it)
                    }
                ) {
                    navController.navigate("addpaymentpurchase/${client.id}")
                }
        }
    }
}

@Composable
fun ClientEntry(
    client: ClientType,
    onEditClient: () -> Unit,
    onDeleteClient: (Int) -> Unit,
    onPaymentPurchaseClient: () -> Unit
) {
    val formatter = DecimalFormat("#0.00")
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Client",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(58.dp)
                    )
                    Text(
                        text = client.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                IconButton(
                    onClick = {
                        expanded = !expanded
                    }) {
                    if (!expanded)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Client details",
                            tint = Color.Gray,
                        )
                    else
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Client details",
                            tint = Color.Gray,
                        )
                }
            }

            if (expanded) {
                Divider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Number: ",
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = client.number,
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Address: ",
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = client.address,
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Debt: ",
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "$" + formatter.format(client.debt).toString(),
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }
                Divider(
                    color = Color.Gray,
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            onEditClient()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Client edit",
                            tint = Color.Gray,
                        )
                    }

                    IconButton(
                        onClick = {
                            onPaymentPurchaseClient()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Client payment/purchase",
                            tint = Color.Gray,
                        )
                    }

                    IconButton(
                        onClick = {
                            onDeleteClient(client.id)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Client remove",
                            tint = Color.Gray,
                        )
                    }
                }

            }
        }
    }
}