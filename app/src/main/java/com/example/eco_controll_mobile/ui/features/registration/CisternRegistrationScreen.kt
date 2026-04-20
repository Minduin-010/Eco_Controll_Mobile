package com.example.eco_controll_mobile.ui.features.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CisternRegistrationScreen(onBackClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var roofArea by remember { mutableStateOf("") }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Nova Cisterna", color = WaterBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, null, tint = TextPrimary) }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(value = name, onValueChange = { name = it }, label = "Nome / Apelido (Ex: Cisterna Quintal)")
            CustomTextField(value = capacity, onValueChange = { capacity = it }, label = "Capacidade Total (Litros)")
            CustomTextField(value = roofArea, onValueChange = { roofArea = it }, label = "Área de Captação do Telhado (m²)")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WaterBlue),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Salvar Cisterna", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White) }
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.DarkGray,
            focusedLabelColor = PrimaryGreen, unfocusedLabelColor = Color.Gray,
            cursorColor = PrimaryGreen
        ),
        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
    )
}