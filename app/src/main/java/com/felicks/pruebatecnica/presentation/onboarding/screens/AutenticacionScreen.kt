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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingUiState
import com.felicks.pruebatecnica.presentation.onboarding.components.StepIndicatorDots

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

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
        ) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }

                // Step pill badge
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape
                        )
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Paso 2 de 6",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Screen Titles
            Text(
                text = "Autenticación",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Sigue estas recomendaciones para validar tu identidad de forma rápida y segura.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Carousel Pager
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
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                StepIndicatorDots(
                    pageCount = uiState.totalTipsCount,
                    activePageIndex = uiState.activeTipIndex
                )
            }

            // Bottom Action Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
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
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = if (uiState.isLastTip) "Empezar verificación" else "Siguiente",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
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
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Dynamic Illustration based on page
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(28.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (pageIndex == 0) {
                SelfieLightingIllustration(
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                CarnetDocumentIllustration(
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 22.sp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Illustration 1: Selfie portrait with lamp / sun lighting guidance
 */
@Composable
private fun SelfieLightingIllustration(
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(120.dp)) {
        val width = size.width
        val height = size.height

        // Sun / Lamp rays (top right)
        val sunCenter = Offset(width * 0.78f, height * 0.22f)
        drawCircle(
            color = Color(0xFFFFB300),
            radius = width * 0.10f,
            center = sunCenter
        )
        // Rays
        for (i in 0 until 6) {
            val angle = (i * 60.0 * Math.PI / 180.0).toFloat()
            val startX = sunCenter.x + (width * 0.13f) * kotlin.math.cos(angle)
            val startY = sunCenter.y + (width * 0.13f) * kotlin.math.sin(angle)
            val endX = sunCenter.x + (width * 0.19f) * kotlin.math.cos(angle)
            val endY = sunCenter.y + (width * 0.19f) * kotlin.math.sin(angle)
            drawLine(
                color = Color(0xFFFFB300),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Face outline
        val faceCenter = Offset(width * 0.44f, height * 0.45f)
        drawCircle(
            color = tint,
            radius = width * 0.20f,
            center = faceCenter,
            style = Stroke(width = 3.5.dp.toPx())
        )

        // Eyes
        drawCircle(
            color = tint,
            radius = 3.dp.toPx(),
            center = Offset(faceCenter.x - width * 0.07f, faceCenter.y - height * 0.03f)
        )
        drawCircle(
            color = tint,
            radius = 3.dp.toPx(),
            center = Offset(faceCenter.x + width * 0.07f, faceCenter.y - height * 0.03f)
        )

        // Smile
        val smilePath = Path().apply {
            moveTo(faceCenter.x - width * 0.07f, faceCenter.y + height * 0.06f)
            quadraticTo(
                faceCenter.x, faceCenter.y + height * 0.12f,
                faceCenter.x + width * 0.07f, faceCenter.y + height * 0.06f
            )
        }
        drawPath(
            path = smilePath,
            color = tint,
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
        )

        // Torso / shoulders
        val torsoPath = Path().apply {
            moveTo(faceCenter.x - width * 0.32f, height * 0.90f)
            quadraticTo(
                faceCenter.x, faceCenter.y + height * 0.25f,
                faceCenter.x + width * 0.32f, height * 0.90f
            )
        }
        drawPath(
            path = torsoPath,
            color = tint,
            style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * Illustration 2: Carnet / ID Document card on a flat surface
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
            size = Size(cardWidth, cardHeight),
            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
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
            size = Size(photoWidth, photoHeight),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
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
