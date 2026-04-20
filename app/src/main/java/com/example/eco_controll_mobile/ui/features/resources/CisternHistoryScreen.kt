package com.example.eco_controll_mobile.ui.features.resources

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
// Imports OBRIGATÓRIOS para usar variáveis de estado (var isLiters by remember)
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_controll_mobile.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CisternHistoryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToRegistration: () -> Unit
) {
    // 1. Estado para controlar a unidade de medida (Verdadeiro = Litros, Falso = m³)
    var isLiters by remember { mutableStateOf(true) }

    // 2. Variáveis dinâmicas que mudam sozinhas baseadas no estado acima
    val displayCapacity = if (isLiters) "10.000 L" else "10.0 m³"
    val displayDaily = if (isLiters) "285 L" else "0.28 m³"
    val displayToday = if (isLiters) "120 L" else "0.12 m³"
    val buttonText = if (isLiters) "Mudar para m³" else "Mudar para Litros"

    Scaffold(
        containerColor = DarkBackground,
        // Barra Superior com botão de voltar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Histórico da Cisterna", color = WaterBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = TextPrimary) }
                }
            )
        },
        // Botão Flutuante Exclusivo para Adicionar nova Cisterna
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToRegistration,
                containerColor = WaterBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Cisterna", tint = Color.White)
            }
        }
    ) { paddingValues ->
        // Corpo da Tela (Rolável)
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Título do Gráfico
            Text("Nível da Água - Última Semana", color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Gráfico Animado
            SimpleLineChartMock(modifier = Modifier.fillMaxWidth().height(200.dp))

            Spacer(modifier = Modifier.height(24.dp))

            // Botão para trocar Unidade de Medida (Litros <-> m³)
            Button(
                onClick = { isLiters = !isLiters }, // Inverte o estado ao clicar
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = WaterBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(buttonText, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // CARDS DE MÉTRICAS (Responsivos)
            // ==========================================
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Primeira Linha: Divide o espaço pela metade (weight 1f para cada)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    MetricCard(
                        title = "Capacidade",
                        value = displayCapacity,
                        icon = { Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = WaterBlue) },
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Consumo Diário",
                        value = displayDaily,
                        icon = { Icon(Icons.Rounded.TrendingDown, contentDescription = null, tint = Color(0xFFE57373)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Segunda Linha: Ocupa a largura inteira (Card do "Consumo Hoje")
                MetricCard(
                    title = "Consumo Hoje",
                    value = displayToday,
                    // O ícone ganhou um Modifier.size(32.dp) para não esticar!
                    icon = { Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = WaterBlue, modifier = Modifier.size(32.dp)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ==========================================
// GRÁFICO ANIMADO
// ==========================================
@Composable
fun SimpleLineChartMock(modifier: Modifier = Modifier, lineColor: Color = WaterBlue) {
    // Controla o progresso da animação de 0.0 até 1.0 usando Animatable
    val progress = remember { Animatable(0f) }

    // Dispara a animação assim que o componente aparece na tela (1.5 segundos)
    LaunchedEffect(key1 = true) {
        progress.animateTo(targetValue = 1f, animationSpec = tween(1500))
    }

    // Dados fictícios do gráfico
    val mockData = listOf(60f, 65f, 62f, 70f, 85f, 80f, 95f)

    // Desenha a linha
    Canvas(modifier = modifier) {
        val path = Path()
        val stepX = size.width / (mockData.size - 1)

        mockData.forEachIndexed { index, value ->
            // A mágica acontece aqui: O valor real é multiplicado pelo progresso (0 a 1)
            val animatedValue = value * progress.value
            val y = size.height - (animatedValue / 100f * size.height)

            if (index == 0) {
                path.moveTo(index * stepX, y)
            } else {
                path.lineTo(index * stepX, y)
            }
        }
        drawPath(path = path, color = lineColor, style = Stroke(width = 8f))
    }
}

// ==========================================
// COMPONENTES VISUAIS AUXILIARES
// ==========================================

// Card reutilizável para mostrar métricas (Ex: Capacidade, Consumo)
@Composable
fun MetricCard(title: String, value: String, icon: @Composable () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Mantém tudo centralizado
        ) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, color = TextSecondary, fontSize = 10.sp)
                Text(value, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Item reutilizável para montar uma linha do tempo (Timeline)
@Composable
fun TimelineItem(date: String, description: String, dotColor: Color) {
    Row(verticalAlignment = Alignment.Top) {
        // Bolinha colorida da Timeline
        Box(modifier = Modifier.padding(top = 6.dp).size(8.dp).clip(CircleShape).background(dotColor))
        Spacer(modifier = Modifier.width(12.dp))
        // Textos
        Column {
            Text(date, color = Color.Gray, fontSize = 12.sp)
            Text(description, color = Color.Black, fontSize = 14.sp)
        }
    }
}