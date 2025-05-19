package com.example.taggingeventsapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taggingeventsapp.Product

@Composable
fun CheckoutScreen(
    cart: List<Product>,
    onConfirm: (address: String, payment: String) -> Unit,
    onBack: () -> Unit,
    viewModel: MainViewModel
) {
    var address by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Finalizar Compra", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))
        Text("Endereço de entrega")
        TextField(value = address, onValueChange = { address = it })

        Spacer(modifier = Modifier.height(16.dp))
        Text("Forma de pagamento")
        TextField(value = payment, onValueChange = {
            payment = it ;
            viewModel.logAddPaymentInfo(cart)
        })

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onConfirm(address, payment) }) {
            Text("Finalizar compra")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onBack) {
            Text("Voltar")
        }
    }
}
