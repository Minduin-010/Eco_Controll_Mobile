package com.example.eco_controll_mobile.ui.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.components.EcoTextField
import com.example.eco_controll_mobile.ui.theme.DarkBackground
import com.example.eco_controll_mobile.ui.theme.PrimaryGreen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.eco_controll_mobile.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import android.util.Log
import android.content.Context
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.platform.LocalContext
import com.example.eco_controll_mobile.network.RetrofitClient
import android.widget.Toast

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onNavigateToForgotPassword: () -> Unit, onNavigateToSignUp: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    // 1. Nova variável de estado para controlar o "olhinho"
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground)) {
        Box(modifier = Modifier.weight(1.2f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(170.dp)
                )
                Text(text = "Bem-vindo de volta!", color = Color.White.copy(alpha = 0.7f), fontSize = 24.sp)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().weight(1.8f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ){
            // Removi o Arrangement.spacedBy para ter controle manual do espaço
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 16.dp)) {
                // Campo E-mail
                Text("E-mail", color = DarkBackground, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                EcoTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = "E-mail",
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = Color.Gray) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo Senha (Atualizado com o Olho)
                Text("Senha", color = DarkBackground, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                EcoTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = "Senha",
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Gray) },

                    // 2. Controlamos se ele é senha baseado no estado
                    isPassword = !passwordVisible,

                    // 3. Adicionamos o ícone no final (trailingIcon)
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
                        }
                    }
                )

                // Esqueci minha senha
                TextButton(
                    onClick = onNavigateToForgotPassword,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Esqueci minha senha", color = Color(0xFF5D88A5))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // BOTÃO ENTRAR COM AS VALIDAÇÕES E MENSAGENS
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                // Dados mockados para desenvolvimento (APAGAR DEPOIS)
                                if (usuario == "front" && senha == "front123") {
                                    Toast.makeText(context, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show()
                                    onLoginClick()
                                    return@launch
                                }

                                // Faz a chamada para o Back-end
                                val response = RetrofitClient.api.login(LoginRequest(usuario = usuario, senha = senha))

                                if (response.isSuccessful) {
                                    // 1. MENSAGEM DE SUCESSO
                                    Toast.makeText(context, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show()

                                    val token = response.body()?.token
                                    val sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE)
                                    sharedPref.edit().putString("token", token).apply()
                                    onLoginClick()
                                } else {
                                    // 2. MENSAGEM DE CREDENCIAIS INCORRETAS
                                    Toast.makeText(context, "Usuario e/ou senha incorretos", Toast.LENGTH_SHORT).show()
                                }

                            } catch (e: Exception) {
                                // 3. MENSAGEM E LOG DE ERRO NO SERVIDOR
                                Toast.makeText(context, "Erro ao acessar o servidor", Toast.LENGTH_SHORT).show()
                                Log.e("LOGIN_ERRO", "Erro de conexão com o back-end: ${e.message}", e)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen), shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Entrar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onNavigateToSignUp) {
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = PrimaryGreen)) { append("Ainda não tem conta? ") }
                        withStyle(style = SpanStyle(color = Color(0xFF5D88A5))) { append("Cadastre-se!") }
                    })
                }
            }
        }
    }
}