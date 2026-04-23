package com.example.eco_controll_mobile.ui.features.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.theme.*
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onBackClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Estados dos "olhinhos" (visibilidade)
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Criar Conta", color = PrimaryGreen) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nome Completo
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome Completo") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray)
            )

            // Apelido (Novo)
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Apelido") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray)
            )

            // E-mail
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray)
            )

            // Senha (Atualizado com o Olho)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Mostrar senha", tint = Color.Gray)
                    }
                }
            )

            // Confirmar Senha (Novo com Olho)
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Senha") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Color.Gray),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = "Mostrar senha", tint = Color.Gray)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÃO DE FINALIZAR CADASTRO
            Button(
                onClick = {
                    // 1. VALIDAÇÃO DE CAMPOS VAZIOS
                    if (name.isBlank() || nickname.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        Toast.makeText(context, "Dados não preenchidos corretamente", Toast.LENGTH_SHORT).show()
                        return@Button // Impede o código de continuar
                    }

                    // 2. VALIDAÇÃO DE QUANTIDADE DE CARACTERES (Mínimo 8)
                    if (password.length < 8) {
                        Toast.makeText(context, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Bônus: Validar se as senhas são iguais
                    if (password != confirmPassword) {
                        Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Se passou por todas as regras, tenta salvar no servidor
                    scope.launch {
                        try {
                            // AQUI VAI O CÓDIGO DO SEU RETROFIT PARA CADASTRAR (Exemplo ilustrativo)
                            // val response = RetrofitClient.api.cadastrar(...)

                            // Simulando que deu certo:
                            // 3. MENSAGEM DE SUCESSO
                            Toast.makeText(context, "Cadastro efetuado com sucesso", Toast.LENGTH_SHORT).show()
                            onBackClick() // Volta para a tela de login

                        } catch (e: Exception) {
                            // 4. MENSAGEM DE ERRO DO SERVIDOR
                            Toast.makeText(context, "Erro ao acessar o servidor", Toast.LENGTH_SHORT).show()
                            Log.e("CADASTRO_ERRO", "Erro de conexão: ${e.message}", e)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Finalizar Cadastro", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}