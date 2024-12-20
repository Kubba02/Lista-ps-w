package com.example.schronisko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.schronisko.ui.theme.SchroniskoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchroniskoTheme {
                DogShelterScreen()
            }
        }
    }
}

@Composable
fun DogShelterScreen() {
    var dogName by remember { mutableStateOf(TextFieldValue("")) }
    var dogs by remember { mutableStateOf(listOf<String>()) }
    val dogSet = remember { mutableSetOf<String>() }
    var errorMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = dogName,
            onValueChange = {
                dogName = it
                errorMessage = "" // Czyszczenie błędu przy zmianie tekstu
            },
            label = { Text("Wpisz nazwę psa") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(
                onClick = {
                    val name = dogName.text.trim()
                    if (name.isNotEmpty()) {
                        if (dogSet.contains(name)) {
                            errorMessage = "Ten pies już istnieje!"
                        } else {
                            dogSet.add(name)
                            dogs = listOf(name) + dogs // Dodawanie psa na górę listy
                            dogName = TextFieldValue("") // Czyszczenie pola tekstowego
                        }
                    }
                },
                enabled = dogName.text.isNotEmpty()
            ) {
                Text("Dodaj")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val name = dogName.text.trim()
                    if (dogs.contains(name)) {
                        errorMessage = "Pies $name jest na liście."
                    } else {
                        errorMessage = "Nie znaleziono psa $name."
                    }
                },
                enabled = dogName.text.isNotEmpty()
            ) {
                Text("Szukaj")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(dogs) { dog ->
                Text(
                    text = dog,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
