package com.example.eco_controll_mobile.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.components.EcoTextField
import com.example.eco_controll_mobile.ui.theme.*
import androidx.compose.material.icons.rounded.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(onNavigateBack: () -> Unit) {

    // Estados dos campos de texto
    var nome by remember { mutableStateOf("Lincoln Miguel") }
    var email by remember { mutableStateOf("lincolnmig492@gmail.com") }
    var telefone by remember { mutableStateOf("(11) 91045-1902") }

    var senhaAtual by remember { mutableStateOf("********") }
    var novaSenha by remember { mutableStateOf("********") }
    var confirmarSenha by remember { mutableStateOf("********") }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Voltar", color = TextPrimary, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = TextPrimary) }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Cabeçalho de Perfil (Foto e Nome)
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        // Placeholder da imagem do usuário
                        Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.Gray))

                        // Ícone de câmera (Badge)
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape).background(PrimaryGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.CameraAlt, contentDescription = "Mudar foto", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(nome.split(" ")[0], color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(email, color = Color.Gray, fontSize = 14.sp)
                }
            }

            // Sessão: Informações Pessoais
            ProfileSectionCard(title = "Informações Pessoais", icon = { Icon(Icons.Rounded.Person, contentDescription = null, tint = PrimaryGreen) }) {
                Text("Nome Completo", color = Color.DarkGray, fontSize = 12.sp)
                EcoTextField(value = nome, onValueChange = { nome = it }, label = "")
                Spacer(modifier = Modifier.height(12.dp))

                Text("E-mail", color = Color.DarkGray, fontSize = 12.sp)
                EcoTextField(value = email, onValueChange = { email = it }, label = "")
                Spacer(modifier = Modifier.height(12.dp))

                Text("Telefone", color = Color.DarkGray, fontSize = 12.sp)
                EcoTextField(value = telefone, onValueChange = { telefone = it }, label = "")
            }

            // Sessão: Detalhes do Sistema (Substitui Segurança)
            ProfileSectionCard(title = "Equipamentos Instalados", icon = { Icon(Icons.Rounded.Settings, null, tint = PrimaryGreen) }) {
                Text("Tipo de Cisterna", color = Color.Gray, fontSize = 12.sp)
                Text("Polietileno Vertical - 10.000L", color = Color.Black, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                Text("Marca dos Painéis Solares", color = Color.Gray, fontSize = 12.sp)
                Text("Canadian Solar - HiKu6 Mono PERC", color = Color.Black, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(12.dp))

                Text("Modelo do Inversor", color = Color.Gray, fontSize = 12.sp)
                Text("Growatt MIN 5000TL-X", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            // Botões de Ação
            Button(
                onClick = { /* Salvar */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Salvar Alterações", color = Color.White, fontWeight = FontWeight.Bold)
            }

            TextButton(
                onClick = { /* Excluir conta */ },
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text("Sair da conta", color = Color.Red.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun ProfileSectionCard(title: String, icon: @Composable () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                icon()
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            content()
        }
    }
}