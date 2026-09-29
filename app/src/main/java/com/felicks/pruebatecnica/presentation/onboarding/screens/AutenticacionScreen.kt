package com.felicks.pruebatecnica.presentation.onboarding.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingUiState
import com.felicks.pruebatecnica.presentation.onboarding.components.StepIndicatorDots
import com.felicks.pruebatecnica.ui.theme.BilleGreenHeader
import com.felicks.pruebatecnica.ui.theme.BilleGreenPrimary
import com.felicks.pruebatecnica.ui.theme.BillePurpleInstruction
import com.felicks.pruebatecnica.ui.theme.BilleTextPrimary
import com.felicks.pruebatecnica.ui.theme.BilleTextSecondary

@Composable
fun AutenticacionScreen(
    uiState: OnboardingUiState,
    onPageChanged: (Int) -> Unit,
    onNextClicked: () -> Unit,
    onStartVerificationClicked: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = uiState.activeTipIndex,
        pageCount = { uiState.totalTipsCount }
    )

    // Sync pager swipe to ViewModel
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != uiState.activeTipIndex) {
            onPageChanged(pagerState.currentPage)
        }
    }

    // Sync ViewModel activeTipIndex to pager animation
    LaunchedEffect(uiState.activeTipIndex) {
        if (pagerState.currentPage != uiState.activeTipIndex) {
            pagerState.animateScrollToPage(uiState.activeTipIndex)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BilleGreenHeader)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Green Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                // Top App Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Autenticación",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Step info row: Circle Icon + PASO 2 / 6 + Title
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "PASO 2 / 6",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.85f),
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Autenticación",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Linear Progress bar (2 of 6 steps)
                LinearProgressIndicator(
                    progress = { 2f / 6f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f),
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            // White Rounded Bottom Card Container
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .navigationBarsPadding()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Audio speaker icon row on top right
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(BilleGreenPrimary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(22.dp)) {
                                val path = androidx.compose.ui.graphics.Path().apply {
                                    moveTo(size.width * 0.15f, size.height * 0.35f)
                                    lineTo(size.width * 0.35f, size.height * 0.35f)
                                    lineTo(size.width * 0.65f, size.height * 0.15f)
                                    lineTo(size.width * 0.65f, size.height * 0.85f)
                                    lineTo(size.width * 0.35f, size.height * 0.65f)
                                    lineTo(size.width * 0.15f, size.height * 0.65f)
                                    close()
                                }
                                drawPath(path, color = BilleGreenPrimary)
                                drawArc(
                                    color = BilleGreenPrimary,
                                    startAngle = -45f,
                                    sweepAngle = 90f,
                                    useCenter = false,
                                    topLeft = Offset(size.width * 0.45f, size.height * 0.25f),
                                    size = androidx.compose.ui.geometry.Size(size.width * 0.45f, size.height * 0.5f),
                                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Instruction headers matching mockup
                    Text(
                        text = "Antes de comenzar con la prueba de autenticación, te recomendamos:",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = BillePurpleInstruction
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Carousel Pager with tips and illustrations
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) { pageIndex ->
                        val tip = uiState.tips.getOrNull(pageIndex)
                        if (tip != null) {
                            CarouselTipPage(
                                pageIndex = pageIndex,
                                title = tip.title,
                                description = tip.description
                            )
                        }
                    }

                    // Pagination Dots Indicator
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        StepIndicatorDots(
                            pageCount = uiState.totalTipsCount,
                            activePageIndex = uiState.activeTipIndex
                        )
                    }

                    // Bottom Action Button ("Siguiente" or "Empezar verificación")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Button(
                            onClick = {
                                if (uiState.isLastTip) {
                                    onStartVerificationClicked()
                                } else {
                                    onNextClicked()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BilleGreenPrimary
                            )
                        ) {
                            Text(
                                text = if (uiState.isLastTip) "Empezar verificación" else "Siguiente",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CarouselTipPage(
    pageIndex: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = BilleTextPrimary
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Illustration Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (pageIndex == 0) {
                DualLightingIllustration()
            } else {
                CarnetDocumentIllustration(
                    tint = BilleGreenPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp,
                color = BilleTextSecondary
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DualLightingIllustration(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Person under Lamp
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Hanging lamp
                val lampColor = Color(0xFFFFB300)
                drawCircle(lampColor, radius = 10.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.4f))
                drawLine(
                    color = lampColor,
                    start = Offset(size.width * 0.5f, 0f),
                    end = Offset(size.width * 0.5f, size.height * 0.3f),
                    strokeWidth = 2.dp.toPx()
                )
                // Light beam cones
                drawCircle(Color(0xFF22C55E), radius = 6.dp.toPx(), center = Offset(size.width * 0.85f, size.height * 0.7f))
            }
            Canvas(modifier = Modifier.size(60.dp)) {
                // Face
                drawCircle(Color(0xFFFFCC80), radius = 16.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.45f))
                // Smile
                drawArc(
                    color = Color(0xFF5D4037),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.4f, size.height * 0.45f),
                    size = androidx.compose.ui.geometry.Size(12.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )
                // Torso
                drawArc(
                    color = Color(0xFF64B5F6),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.65f),
                    size = androidx.compose.ui.geometry.Size(36.dp.toPx(), 30.dp.toPx())
                )
            }
        }

        // Person under Sun
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(50.dp)) {
                // Sun
                val sunColor = Color(0xFFFF9800)
                drawCircle(sunColor, radius = 12.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.4f))
                for (i in 0 until 8) {
                    val angle = (i * 45.0 * Math.PI / 180.0).toFloat()
                    val startX = size.width * 0.5f + 14.dp.toPx() * kotlin.math.cos(angle)
                    val startY = size.height * 0.4f + 14.dp.toPx() * kotlin.math.sin(angle)
                    val endX = size.width * 0.5f + 18.dp.toPx() * kotlin.math.cos(angle)
                    val endY = size.height * 0.4f + 18.dp.toPx() * kotlin.math.sin(angle)
                    drawLine(sunColor, Offset(startX, startY), Offset(endX, endY), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
                }
                // Checkmark badge
                drawCircle(Color(0xFF22C55E), radius = 6.dp.toPx(), center = Offset(size.width * 0.85f, size.height * 0.7f))
            }
            Canvas(modifier = Modifier.size(60.dp)) {
                // Face
                drawCircle(Color(0xFFFFCC80), radius = 16.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.45f))
                // Smile
                drawArc(
                    color = Color(0xFF5D4037),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.4f, size.height * 0.45f),
                    size = androidx.compose.ui.geometry.Size(12.dp.toPx(), 8.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )
                // Torso
                drawArc(
                    color = Color(0xFF81C784),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.65f),
                    size = androidx.compose.ui.geometry.Size(36.dp.toPx(), 30.dp.toPx())
                )
            }
        }
    }
}

/**
 * Illustration: Carnet / ID Document card on a flat surface
 */
@Composable
private fun CarnetDocumentIllustration(
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val width = size.width
        val height = size.height

        // Outer card body
        val cardLeft = width * 0.10f
        val cardTop = height * 0.22f
        val cardWidth = width * 0.80f
        val cardHeight = height * 0.56f

        drawRoundRect(
            color = tint,
            topLeft = Offset(cardLeft, cardTop),
            size = androidx.compose.ui.geometry.Size(cardWidth, cardHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx(), 12.dp.toPx()),
            style = Stroke(width = 3.5.dp.toPx())
        )

        // User photo box inside card
        val photoLeft = cardLeft + width * 0.08f
        val photoTop = cardTop + height * 0.10f
        val photoWidth = width * 0.22f
        val photoHeight = height * 0.25f

        drawRoundRect(
            color = tint.copy(alpha = 0.3f),
            topLeft = Offset(photoLeft, photoTop),
            size = androidx.compose.ui.geometry.Size(photoWidth, photoHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6.dp.toPx(), 6.dp.toPx())
        )

        // Lines representing text in the carnet
        val lineStartX = photoLeft + photoWidth + width * 0.08f
        val lineEndX = cardLeft + cardWidth - width * 0.08f

        // Line 1 (Name)
        drawLine(
            color = tint,
            start = Offset(lineStartX, photoTop + height * 0.04f),
            end = Offset(lineEndX, photoTop + height * 0.04f),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Line 2 (CI Number)
        drawLine(
            color = tint,
            start = Offset(lineStartX, photoTop + height * 0.12f),
            end = Offset(lineEndX - width * 0.10f, photoTop + height * 0.12f),
            strokeWidth = 3.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Line 3 (Nationality / Date)
        drawLine(
            color = tint.copy(alpha = 0.6f),
            start = Offset(lineStartX, photoTop + height * 0.20f),
            end = Offset(lineEndX - width * 0.05f, photoTop + height * 0.20f),
            strokeWidth = 2.5.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Gold chip / seal on bottom left
        drawCircle(
            color = Color(0xFFFFB300),
            radius = width * 0.05f,
            center = Offset(photoLeft + photoWidth / 2f, cardTop + cardHeight - height * 0.10f)
        )
    }
}
