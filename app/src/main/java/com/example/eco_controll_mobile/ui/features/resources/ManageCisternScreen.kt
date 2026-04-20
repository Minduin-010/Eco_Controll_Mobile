package com.example.eco_controll_mobile.ui.features.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.WaterDrop
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
fun ManageCisternScreen(onNavigateBack: () -> Unit) {

    // Estados da Tela
    var alertsEnabled by remember { mutableStateOf(true) }
    var minLimit by remember { mutableStateOf(20f) }
    var sensorsEnabled by remember { mutableStateOf(true) }

    // Estados do Menu Dropdown (Unidade de Medida)
    var expandedUnitMenu by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf("Litros (L)") }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Lincoln", color = PrimaryGreen, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()), // Permite rolar a tela se ficar pequena
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- CABEÇALHO ---
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(WaterBlue), contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Gerenciar Cisterna", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            // --- 1. ALERTAS ATIVOS ---
            SettingsCard {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.NotificationsActive, contentDescription = null, tint = SolarOrange)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Alertas Ativos", color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text("Notificações de nível baixo", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                    Switch(checked = alertsEnabled, onCheckedChange = { alertsEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryGreen))
                }
            }

            // --- 2. LIMITE MÍNIMO (SLIDER) ---
            SettingsCard(containerColor = Color.White) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Speed, contentDescription = null, tint = Color.Red.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Limite Mínimo", color = Color.Black, fontWeight = FontWeight.Bold)
                            Text("Alerta quando atingir ${minLimit.toInt()}%", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = minLimit, onValueChange = { minLimit = it }, valueRange = 0f..100f,
                        colors = SliderDefaults.colors(thumbColor = PrimaryGreen, activeTrackColor = PrimaryGreen, inactiveTrackColor = DarkBackground.copy(alpha = 0.2f))
                    )
                }
            }

            // --- 3. UNIDADE DE MEDIDA (DROPDOWN MENU) ---
            SettingsCard(containerColor = Color.White) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Unidade de Medida", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Escolha entre Litros ou m³", color = Color.Gray, fontSize = 12.sp)
                    }

                    // Caixa que segura o botão e o menu pop-up
                    Box {
                        Button(
                            onClick = { expandedUnitMenu = true },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkBackground),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("$selectedUnit ▼", color = Color.White)
                        }

                        // O Menu em si que aparece ao clicar
                        DropdownMenu(
                            expanded = expandedUnitMenu,
                            onDismissRequest = { expandedUnitMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Litros (L)") },
                                onClick = { selectedUnit = "Litros (L)"; expandedUnitMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Metros Cúbicos (m³)") },
                                onClick = { selectedUnit = "Metros Cúbicos (m³)"; expandedUnitMenu = false }
                            )
                        }
                    }
                }
            } // <- ESTA ERA A CHAVE QUE FALTAVA FECHAR NO SEU CÓDIGO ORIGINAL!

            // --- 4. SENSORES ATIVOS ---
            SettingsCard(containerColor = Color.White) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Sensores Ativos", color = Color.Black, fontWeight = FontWeight.Bold)
                        Text("Monitoramento em tempo real", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(checked = sensorsEnabled, onCheckedChange = { sensorsEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryGreen))
                }
            }

            // Espaço no final para a tela rolar livremente até o fim sem cortar no teclado/borda
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Componente Auxiliar que cria o formato do "Card" com bordas arredondadas e padding interno
@Composable
fun SettingsCard(containerColor: Color = CardBackground, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}