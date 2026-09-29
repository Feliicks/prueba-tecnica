package com.felicks.pruebatecnica.presentation.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felicks.pruebatecnica.presentation.onboarding.OnboardingUiState
import com.felicks.pruebatecnica.ui.theme.BilleBorderColor
import com.felicks.pruebatecnica.ui.theme.BilleGreenHeader
import com.felicks.pruebatecnica.ui.theme.BilleGreenPrimary
import com.felicks.pruebatecnica.ui.theme.BilleTextPrimary

@Composable
fun InformacionScreen(
    uiState: OnboardingUiState,
    onPhoneChanged: (String) -> Unit,
    onCarnetChanged: (String) -> Unit,
    onComplementChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClicked: (() -> Unit)? = null
) {
    var hasComplementChecked by remember { mutableStateOf(uiState.complement.isNotBlank()) }

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
                // Top Navigation Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (onBackClicked != null) {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(48.dp))
                    }

                    Text(
                        text = "Información",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Step info row: Circle Icon + PASO 1 / 6 + Title
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
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "PASO 1 / 6",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.85f),
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Información",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Linear Progress bar (1 of 6 steps)
                LinearProgressIndicator(
                    progress = { 1f / 6f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f),
                    strokeCap = StrokeCap.Round
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Banner title and subtitle
                Text(
                    text = "Ingresa tus datos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "¡Únete a nuestra app hoy!\nCompleta los siguientes datos para comenzar a disfrutar de tu Bille.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color.White.copy(alpha = 0.9f)
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
                        .imePadding()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(top = 28.dp)
                    ) {
                        // Número de celular input
                        OutlinedTextField(
                            value = uiState.phone,
                            onValueChange = onPhoneChanged,
                            label = { Text("Número de celular:", fontWeight = FontWeight.Medium) },
                            placeholder = { Text("71234567") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = uiState.phoneError != null,
                            supportingText = {
                                if (uiState.phoneError != null) {
                                    Text(
                                        text = uiState.phoneError,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } else {
                                    Text(
                                        text = "Máximo 8 dígitos numéricos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BilleGreenPrimary,
                                unfocusedBorderColor = BilleBorderColor,
                                focusedLabelColor = BilleGreenPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Número de carnet input
                        OutlinedTextField(
                            value = uiState.carnet,
                            onValueChange = onCarnetChanged,
                            label = { Text("Número de carnet:", fontWeight = FontWeight.Medium) },
                            placeholder = { Text("412345") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            isError = uiState.carnetError != null,
                            supportingText = {
                                if (uiState.carnetError != null) {
                                    Text(
                                        text = uiState.carnetError,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } else {
                                    Text(
                                        text = "Hasta 10 dígitos numéricos",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BilleGreenPrimary,
                                unfocusedBorderColor = BilleBorderColor,
                                focusedLabelColor = BilleGreenPrimary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Complement toggle checkbox
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    hasComplementChecked = !hasComplementChecked
                                    if (!hasComplementChecked) {
                                        onComplementChanged("")
                                    }
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = hasComplementChecked,
                                onCheckedChange = { checked ->
                                    hasComplementChecked = checked
                                    if (!checked) {
                                        onComplementChanged("")
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = BilleGreenPrimary
                                )
                            )
                            Text(
                                text = "¿Tiene complemento?",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = BilleTextPrimary
                            )
                        }

                        // Complemento (opcional) input field
                        if (hasComplementChecked || uiState.complement.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = uiState.complement,
                                onValueChange = onComplementChanged,
                                label = { Text("Complemento (opcional):", fontWeight = FontWeight.Medium) },
                                placeholder = { Text("Ej. 1D") },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    capitalization = KeyboardCapitalization.Characters
                                ),
                                singleLine = true,
                                isError = uiState.complementError != null,
                                supportingText = {
                                    if (uiState.complementError != null) {
                                        Text(
                                            text = uiState.complementError,
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    } else {
                                        Text(
                                            text = "Máximo 2 caracteres (letras y números, sin símbolos)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BilleGreenPrimary,
                                    unfocusedBorderColor = BilleBorderColor,
                                    focusedLabelColor = BilleGreenPrimary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    // Sticky Bottom Submit Button ("Siguiente")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Button(
                            onClick = onSubmitClicked,
                            enabled = uiState.isSubmitEnabled && !uiState.isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BilleGreenPrimary,
                                disabledContainerColor = BilleGreenPrimary.copy(alpha = 0.5f)
                            )
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.5.dp
                                )
                            } else {
                                Text(
                                    text = "Siguiente",
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
}

