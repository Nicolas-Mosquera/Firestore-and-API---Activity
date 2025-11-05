package com.example.composefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioScreen()
        }
    }
}

@Composable
fun FormularioScreen() {
    val db = FirebaseFirestore.getInstance()
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Estudiante", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = grade,
            onValueChange = { grade = it },
            label = { Text("Grade") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && grade.isNotEmpty()) {
                    val estudiante = hashMapOf(
                        "name" to name,
                        "grade" to grade
                    )

                    db.collection("estudiantes")
                        .add(estudiante)
                        .addOnSuccessListener {
                            mensaje = "✅ Registro guardado correctamente"
                            name = ""
                            grade = ""
                        }
                        .addOnFailureListener {
                            mensaje = "❌ Error al guardar: ${it.message}"
                        }
                } else {
                    mensaje = "Por favor llena ambos campos"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(mensaje)
    }
}
