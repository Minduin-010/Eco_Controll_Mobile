package com.example.eco_controll_mobile.ui.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eco_controll_mobile.ui.theme.*
import android.util.Patterns
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Recuperar Senha", color = PrimaryGreen) },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Digite seu e-mail cadastrado para enviarmos um link de recuperação.",
                color = Color.White,
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // 1. VALIDAÇÃO SE O E-MAIL É INVÁLIDO (Vazio ou não tem formato de @email.com)
                    if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "E-mail inválido", Toast.LENGTH_SHORT).show()
                    } else {
                        // 2. MENSAGEM DE SUCESSO (Aqui você colocaria a chamada para o Back-end)
                        Toast.makeText(context, "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show()
                        onBackClick() // Retorna para o Login automaticamente
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Enviar E-mail", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}