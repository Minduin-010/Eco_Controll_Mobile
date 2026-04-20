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
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onNavigateToForgotPassword: () -> Unit, onNavigateToSignUp: () -> Unit) {
    var usuario by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
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

                // Campo Senha
                Text("Senha", color = DarkBackground, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                EcoTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = "Senha",
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = Color.Gray) },
                    isPassword = true
                )

                // Esqueci minha senha
                TextButton(
                    onClick = onNavigateToForgotPassword,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Esqueci minha senha", color = Color(0xFF5D88A5))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botão Entrar
                Button(
                    onClick = {
//                        Log.d("LOGIN", "Botão clicado")
                        println("BOTAO CLICADO")

                        scope.launch {
                            try {
                                Log.d("LOGIN", "Iniciando requisição")

                                val response = RetrofitClient.api.login(
                                    LoginRequest(
                                        usuario = usuario,
                                        senha = senha
                                    )
                                )

                                Log.d("LOGIN", "Resposta recebida")

                                if (response.isSuccessful) {
                                    val token = response.body()?.token

                                    val sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE)
                                    sharedPref.edit().putString("token", token).apply()

                                    Log.d("LOGIN", "Token salvo: $token")

                                    onLoginClick()
                                } else {
                                    Log.d("LOGIN", "Erro HTTP: ${response.code()}")
                                    Log.d("LOGIN", "Erro body: ${response.errorBody()?.string()}")
                                }

                            } catch (e: Exception) {
                                Log.e("LOGIN", "Erro: ${e.message}", e)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Entrar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Criar conta (Sempre visível no fundo do card)
                TextButton(onClick = onNavigateToSignUp) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = PrimaryGreen)) {
                                append("Ainda não tem conta? ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF5D88A5))) {
                                append("Cadastre-se!")
                            }
                        }
                    )
                }
            }
        }
    }
}