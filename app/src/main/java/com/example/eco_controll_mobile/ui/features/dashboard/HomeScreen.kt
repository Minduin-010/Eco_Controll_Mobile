package com.example.eco_controll_mobile.ui.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToManageCistern: () -> Unit,
    onNavigateToHistoryCistern: () -> Unit,
    onNavigateToManageSolar: () -> Unit,
    onNavigateToHistorySolar: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var showNotificationDialog by remember { mutableStateOf(false) }

    // Dialog de Notificações
    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            containerColor = CardBackground,
            title = { Text("Notificações", color = PrimaryGreen, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("✅ Sistema operando normalmente.", color = Color.White)
                    Text("⚠️ Consumo de água um pouco alto hoje.", color = SolarOrange)
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotificationDialog = false }) {
                    Text("Fechar", color = PrimaryGreen)
                }
            }
        )
    }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopBarProfile(
                onProfileClick = onNavigateToProfile,
                onNotificationClick = { showNotificationDialog = true }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. CARDS DE CISTERNA E SOLAR VOLTARAM AO NORMAL (Largura toda + Botões)
            ResourceCard(
                title = "Cisterna",
                value = "80%",
                icon = { Icon(Icons.Rounded.WaterDrop, null, tint = WaterBlue, modifier = Modifier.size(32.dp)) },
                onDetailsClick = onNavigateToHistoryCistern,
                onManageClick = onNavigateToManageCistern
            )

            ResourceCard(
                title = "Energia Solar",
                value = "4.2 kWh",
                icon = { Icon(Icons.Rounded.WbSunny, null, tint = SolarOrange, modifier = Modifier.size(32.dp)) },
                onDetailsClick = onNavigateToHistorySolar,
                onManageClick = onNavigateToManageSolar
            )

            // 2. NOVO CARD: DICA SUSTENTÁVEL (Substitui o de Economia)
            EcoTipCard(modifier = Modifier.fillMaxWidth())

            // 3. ACESSO RÁPIDO
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Acesso Rápido", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    QuickAccessCard("Configurações", Icons.Default.Settings, Modifier.weight(1f), onClick = onNavigateToSettings)
                    QuickAccessCard("Relatórios", Icons.Default.Description, Modifier.weight(1f), onClick = onNavigateToReports)
                    QuickAccessCard("Perfil", Icons.Default.Person, Modifier.weight(1f), onClick = onNavigateToProfile)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ResourceCard(title: String, value: String, icon: @Composable () -> Unit, onDetailsClick: () -> Unit, onManageClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = CardBackground)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(50.dp).clip(RoundedCornerShape(12.dp)).background(DarkBackground), contentAlignment = Alignment.Center) { icon() }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(title, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text(value, color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onDetailsClick, colors = ButtonDefaults.buttonColors(containerColor = TextSecondary.copy(alpha = 0.2f)), modifier = Modifier.weight(1f)) { Text("Detalhes") }
                Button(onClick = onManageClick, colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen), modifier = Modifier.weight(1f)) { Text("Gerenciar") }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(onProfileClick: () -> Unit, onNotificationClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
        title = {
            // Mudamos para Row para ficar alinhado à esquerda perfeitamente
            Row(
                modifier = Modifier.clickable { onProfileClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto/Avatar
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Textos ao lado da foto
                Column {
                    Text(text = "Olá,", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                    Text(text = "Lincoln", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        },
        actions = {
            IconButton(onClick = onNotificationClick) {
                Icon(Icons.Default.Notifications, contentDescription = "Notificações", tint = PrimaryGreen)
            }
        }
    )
}

// === NOVO COMPONENTE: DICA SUSTENTÁVEL ===
@Composable
fun EcoTipCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryGreen.copy(alpha = 0.15f)) // Fundo verde transparente
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone de Lâmpada (Dica)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(PrimaryGreen.copy(alpha = 0.2f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Lightbulb, contentDescription = "Dica", tint = PrimaryGreen, modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Texto da Dica
            Column {
                Text("Dica do Dia", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Utilize a água da sua cisterna para regar plantas no início da manhã ou fim da tarde para evitar a evaporação rápida.",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun MiniResourceCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconTint: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = CardBackground)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, color = TextSecondary, fontSize = 14.sp)
            Text(value, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuickAccessCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Card(modifier = modifier.height(100.dp).clickable { onClick() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = CardBackground)) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = TextSecondary, fontSize = 12.sp)
        }
    }
}