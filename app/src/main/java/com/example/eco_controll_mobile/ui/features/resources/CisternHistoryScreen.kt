package com.example.eco_controll_mobile.ui.features.resources

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.TrendingDown
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    Scaffold(
        containerColor = DarkBackground,
        // CABEÇALHO ORIGINAL "Lincoln"
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground),
                title = { Text("Lincoln", color = PrimaryGreen, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = TextPrimary) }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToRegistration,
                containerColor = WaterBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Cisterna", tint = Color.White)
            }
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
            // TÍTULO DA PÁGINA (Com o ícone azul do lado)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(WaterBlue), contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Cisterna - Histórico", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            // CARD DO GRÁFICO (Agora com X e Y)
            Card(
                modifier = Modifier.fillMaxWidth().height(240.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC5BAA1)) // Fundo Bege
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nível da Água - Última Semana", color = DarkBackground, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Chamada do Gráfico Modificado
                    SimpleLineChartWithLabels(modifier = Modifier.fillMaxSize(), lineColor = WaterBlue)
                }
            }

            // 3. NOVO CONTEÚDO PARA PREENCHER O ESPAÇO VAZIO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryGreen.copy(alpha = 0.1f)) // Fundo verde transparente
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Info, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Previsão de Duração", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Neste ritmo de consumo, a água da sua cisterna durará aproximadamente 35 dias.", color = TextPrimary, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ==========================================
// GRÁFICO ANIMADO (MANTIDO E FUNCIONAL)
// ==========================================
@Composable
fun SimpleLineChartWithLabels(modifier: Modifier = Modifier, lineColor: Color = WaterBlue) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        progress.animateTo(targetValue = 1f, animationSpec = tween(1500))
    }

    val mockData = listOf(60f, 65f, 62f, 70f, 85f, 80f, 95f)
    val daysOfWeek = listOf("S", "T", "Q", "Q", "S", "S", "D")

    Row(modifier = modifier) {
        // Eixo Y (Valores na vertical)
        Column(
            modifier = Modifier.fillMaxHeight().padding(end = 8.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text("100%", fontSize = 12.sp, color = DarkBackground, fontWeight = FontWeight.Medium)
            Text("50%", fontSize = 12.sp, color = DarkBackground, fontWeight = FontWeight.Medium)
            Text("0%", fontSize = 12.sp, color = DarkBackground, fontWeight = FontWeight.Medium)
        }

        // Gráfico e Eixo X (Dias da semana na horizontal)
        Column(modifier = Modifier.fillMaxSize()) {
            // A linha do gráfico
            Canvas(modifier = Modifier.weight(1f).fillMaxWidth()) {
                val path = Path()
                val stepX = size.width / (mockData.size - 1)

                // Reduzindo um pouco a altura máxima para a linha não cortar no topo
                val usableHeight = size.height * 0.9f

                mockData.forEachIndexed { index, value ->
                    val animatedValue = value * progress.value
                    // Calcula a posição invertendo o Y (0 é no topo do Canvas)
                    val y = size.height - (animatedValue / 100f * usableHeight)

                    if (index == 0) {
                        path.moveTo(index * stepX, y)
                    } else {
                        path.lineTo(index * stepX, y)
                    }
                }
                drawPath(path = path, color = lineColor, style = Stroke(width = 8f))
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Eixo X
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                daysOfWeek.forEach { day ->
                    Text(day, fontSize = 12.sp, color = DarkBackground, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ==========================================
// COMPONENTES VISUAIS AUXILIARES (MANTIDOS)
// ==========================================
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
            horizontalArrangement = Arrangement.Center
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