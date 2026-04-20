package com.example.eco_controll_mobile.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Straighten
import androidx.compose.material.icons.rounded.WifiTethering
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
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHelp: () -> Unit // Parâmetro adicionado para a tela de suporte
) {
    // 1. Estados dos botões de Notificação
    var lowLevelAlert by remember { mutableStateOf(true) }
    var weeklyReports by remember { mutableStateOf(false) }
    var monthlyEconomy by remember { mutableStateOf(true) }

    // 2. Estados das Unidades de Medida escolhidas
    var capacityUnit by remember { mutableStateOf("Litros (L)") }
    var energyUnit by remember { mutableStateOf("Kilowatt-hora (kWh)") }
    var tempUnit by remember { mutableStateOf("Celsius (°C)") }

    // 3. Estados para controlar se os Pop-ups (Dialogs) estão abertos ou fechados
    var showCapacityDialog by remember { mutableStateOf(false) }
    var showEnergyDialog by remember { mutableStateOf(false) }
    var showTempDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Lincoln", color = PrimaryGreen, fontSize = 16.sp) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- SEÇÃO 1: NOTIFICAÇÕES ---
            SettingsSectionCard(title = "Notificações", icon = { Icon(Icons.Rounded.Notifications, contentDescription = null, tint = PrimaryGreen) }) {
                SwitchSettingRow("Alertas de nível baixo", lowLevelAlert) { lowLevelAlert = it }
                SwitchSettingRow("Relatórios semanais", weeklyReports) { weeklyReports = it }
                SwitchSettingRow("Economia mensal", monthlyEconomy) { monthlyEconomy = it }
            }

            // --- SEÇÃO 2: UNIDADES DE MEDIDA (AGORA INTERATIVAS) ---
            SettingsSectionCard(title = "Unidades de Medida", icon = { Icon(Icons.Rounded.Straighten, contentDescription = null, tint = PrimaryGreen) }) {
                // Ao clicar na linha, mudamos o estado para true, abrindo o pop-up correspondente
                ChevronSettingRow("Capacidade", capacityUnit) { showCapacityDialog = true }
                ChevronSettingRow("Energia", energyUnit) { showEnergyDialog = true }
                ChevronSettingRow("Temperatura", tempUnit) { showTempDialog = true }
            }

            // --- SEÇÃO 3: INTEGRAÇÃO IOT ---
            SettingsSectionCard(title = "Integração IoT", icon = { Icon(Icons.Rounded.WifiTethering, contentDescription = null, tint = PrimaryGreen) }) {
                IoTStatusRow("Sensor de cisterna", true)
                IoTStatusRow("Inversor solar", true)
                IoTStatusRow("Gateway principal", true)
            }

            // --- SEÇÃO 4: AJUDA E SUPORTE ---
            SettingsSectionCard(title = "Ajuda e Suporte", icon = { Icon(Icons.Rounded.HelpOutline, contentDescription = null, tint = Color.Gray) }) {
                // Linha clicável que dispara a navegação para a tela HelpSupportScreen
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToHelp() } // <-- Rota conectada aqui!
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Central de Ajuda", color = Color.Black)
                    Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }

            // --- SEÇÃO 5: BOTÃO SAIR ---
            OutlinedButton(
                onClick = { /* Lógica de Logout no futuro */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkBackground.copy(alpha = 0.5f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.2f))
            ) {
                Text("[→  Sair da Conta", color = Color.Red.copy(alpha = 0.8f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ==========================================
        //  POP-UPS (DIALOGS) DE SELEÇÃO DE UNIDADES
        // ==========================================

        // Dialog de Capacidade
        if (showCapacityDialog) {
            AlertDialog(
                onDismissRequest = { showCapacityDialog = false },
                containerColor = CardBackground,
                title = { Text("Capacidade", color = Color.White) },
                text = {
                    Column {
                        TextButton(onClick = { capacityUnit = "Litros (L)"; showCapacityDialog = false }) { Text("Litros (L)", color = PrimaryGreen) }
                        TextButton(onClick = { capacityUnit = "Metros Cúbicos (m³)"; showCapacityDialog = false }) { Text("Metros Cúbicos (m³)", color = PrimaryGreen) }
                    }
                },
                confirmButton = { TextButton(onClick = { showCapacityDialog = false }) { Text("Cancelar", color = Color.Gray) } }
            )
        }

        // Dialog de Energia
        if (showEnergyDialog) {
            AlertDialog(
                onDismissRequest = { showEnergyDialog = false },
                containerColor = CardBackground,
                title = { Text("Energia", color = Color.White) },
                text = {
                    Column {
                        TextButton(onClick = { energyUnit = "Kilowatt-hora (kWh)"; showEnergyDialog = false }) { Text("Kilowatt-hora (kWh)", color = PrimaryGreen) }
                        TextButton(onClick = { energyUnit = "Watt-hora (Wh)"; showEnergyDialog = false }) { Text("Watt-hora (Wh)", color = PrimaryGreen) }
                    }
                },
                confirmButton = { TextButton(onClick = { showEnergyDialog = false }) { Text("Cancelar", color = Color.Gray) } }
            )
        }

        // Dialog de Temperatura
        if (showTempDialog) {
            AlertDialog(
                onDismissRequest = { showTempDialog = false },
                containerColor = CardBackground,
                title = { Text("Temperatura", color = Color.White) },
                text = {
                    Column {
                        TextButton(onClick = { tempUnit = "Celsius (°C)"; showTempDialog = false }) { Text("Celsius (°C)", color = PrimaryGreen) }
                        TextButton(onClick = { tempUnit = "Fahrenheit (°F)"; showTempDialog = false }) { Text("Fahrenheit (°F)", color = PrimaryGreen) }
                    }
                },
                confirmButton = { TextButton(onClick = { showTempDialog = false }) { Text("Cancelar", color = Color.Gray) } }
            )
        }
    }
}

// ==========================================
//  COMPONENTES VISUAIS AUXILIARES (REUSÁVEIS)
// ==========================================

// Card base para as seções (Fundo branco, bordas arredondadas)
@Composable
fun SettingsSectionCard(title: String, icon: @Composable () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                icon()
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            content()
        }
    }
}

// Linha com botão "Liga/Desliga" (Switch)
@Composable
fun SwitchSettingRow(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color.DarkGray, fontSize = 14.sp)
        Switch(
            checked = isChecked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CardBackground)
        )
    }
}

// Linha com texto e uma seta para a direita, agora aceita cliques! (Chevron = Seta)
@Composable
fun ChevronSettingRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        // Modificador clickable adicionado para a linha inteira responder ao toque
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color.DarkGray, fontSize = 14.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(value, color = Color.Gray, fontSize = 12.sp)
            Icon(Icons.Rounded.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

// Linha para mostrar o status do IoT (Bolinha verde/vermelha)
@Composable
fun IoTStatusRow(deviceName: String, isConnected: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(if (isConnected) PrimaryGreen else Color.Red))
            Spacer(modifier = Modifier.width(12.dp))
            Text(deviceName, color = Color.DarkGray, fontSize = 14.sp)
        }
        Text(if (isConnected) "Conectado" else "Desconectado", color = if (isConnected) PrimaryGreen else Color.Red, fontSize = 12.sp)
    }
}