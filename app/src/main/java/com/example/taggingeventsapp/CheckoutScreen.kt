package com.example.taggingeventsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taggingeventsapp.Product


@Composable
fun CheckoutScreen(
    cart: List<Product>,
    onConfirm: (address: String, payment: String) -> Unit,
    onBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current
    var address by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("") }
    var hasLoggedAddress by remember { mutableStateOf(false) }

    val paymentOptions = listOf("Cartão", "Pix", "Boleto")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Finalizar Compra", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Endereço de entrega")
            TextField(
                value = address,
                onValueChange = { address = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused && address.isNotBlank() && !hasLoggedAddress) {
                            viewModel.logAddShippingInfo(cart, address)
                            hasLoggedAddress = true
                        } else if (address.isBlank()) {
                            hasLoggedAddress = false
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Forma de pagamento")

            Column {
                paymentOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = payment == option,
                            onClick = {
                                payment = option
                                viewModel.logAddPaymentInfo(cart, option)
                            }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onConfirm(address, payment); viewModel.logGenerateLead(cart) },
                enabled = address.isNotBlank() && payment.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finalizar compra")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar")
            }
        }
    }
}