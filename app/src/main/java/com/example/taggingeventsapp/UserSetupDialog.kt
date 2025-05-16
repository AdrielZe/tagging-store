package com.example.taggingeventsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button


import com.example.taggingeventsapp.countries
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserSetupDialog(onConfirm: (String, Country) -> Unit) {
    var name by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Country?>(null) }

    AlertDialog(
        onDismissRequest = {},
        title = { Text("Bem-vindo!") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Seu nome") }
                )
                Spacer(Modifier.height(8.dp))
                LazyColumn {
                    items(countries) { country ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable { selected = country }
                                .padding(4.dp)
                        ) {
                            AsyncImage(
                                model = country.flagUrl,
                                contentDescription = country.name,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(country.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && selected != null) {
                        onConfirm(name, selected!!)
                    }
                }
            ) {
                Text("Confirmar")
            }
        }
    )
}

