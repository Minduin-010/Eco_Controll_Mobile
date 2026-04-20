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

            // O SEU CARD BEGE ORIGINAL COM O GRÁFICO ANIMADO
            Card(
                modifier = Modifier.fillMaxWidth().height(220.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC5BAA1)) // Fundo Bege
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nível da Água - Última Semana", color = DarkBackground, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Gráfico preenchendo o espaço interno
                    SimpleLineChartMock(modifier = Modifier.fillMaxSize(), lineColor = WaterBlue)
                }
            }

            // SEUS CARDS DE MÉTRICAS (Lado a lado apenas)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Capacidade",
                    value = "10.000 L",
                    icon = { Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = WaterBlue) }
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Consumo Diário",
                    value = "285 L",
                    icon = { Icon(Icons.Rounded.TrendingDown, contentDescription = null, tint = Color(0xFFE57373)) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ==========================================
// GRÁFICO ANIMADO (MANTIDO E FUNCIONAL)
// ==========================================
@Composable
fun SimpleLineChartMock(modifier: Modifier = Modifier, lineColor: Color = WaterBlue) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        progress.animateTo(targetValue = 1f, animationSpec = tween(1500))
    }

    val mockData = listOf(60f, 65f, 62f, 70f, 85f, 80f, 95f)

    Canvas(modifier = modifier) {
        val path = Path()
        val stepX = size.width / (mockData.size - 1)

        mockData.forEachIndexed { index, value ->
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