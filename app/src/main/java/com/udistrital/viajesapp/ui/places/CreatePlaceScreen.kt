package com.udistrital.viajesapp.ui.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    tripId: String,
    onNavigateBack: () -> Unit,
    onPlaceCreated: () -> Unit,
    viewModel: CreatePlaceViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(tripId) {
        viewModel.loadData(tripId)
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onPlaceCreated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Lugar a Visitar", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoadingTrip || uiState.isLoadingCategories) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.trip?.let { trip ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Viaje: ${trip.name} (${trip.destination})",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "La fecha debe estar entre ${trip.startDate} y ${trip.endDate}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = { viewModel.onNameChanged(it) },
                        label = { Text("Nombre del Lugar *") },
                        placeholder = { Text("Ej: Coliseo Romano, Museo del Prado") },
                        isError = uiState.nameError != null,
                        supportingText = uiState.nameError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onDescriptionChanged(it) },
                        label = { Text("Descripción *") },
                        placeholder = { Text("Ej: Anfiteatro histórico principal...") },
                        isError = uiState.descriptionError != null,
                        supportingText = uiState.descriptionError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = uiState.city,
                        onValueChange = { viewModel.onCityChanged(it) },
                        label = { Text("Ciudad *") },
                        placeholder = { Text("Ej: Roma, Madrid, Medellín") },
                        isError = uiState.cityError != null,
                        supportingText = uiState.cityError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Category Dropdown
                    val selectedCategoryName = uiState.categories
                        .find { it.id == uiState.selectedCategoryId }?.name ?: "Seleccione una categoría"

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedCategoryName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Categoría *") },
                            isError = uiState.categoryError != null,
                            supportingText = uiState.categoryError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { categoryDropdownExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { categoryDropdownExpanded = true }
                        )

                        DropdownMenu(
                            expanded = categoryDropdownExpanded,
                            onDismissRequest = { categoryDropdownExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            uiState.categories.forEach { category ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(category.name, fontWeight = FontWeight.Bold)
                                            Text(category.description, style = MaterialTheme.typography.bodySmall)
                                        }
                                    },
                                    onClick = {
                                        viewModel.onCategorySelected(category.id)
                                        categoryDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.tentativeDate,
                        onValueChange = { viewModel.onTentativeDateChanged(it) },
                        label = { Text("Fecha Tentativa (AAAA-MM-DD) *") },
                        placeholder = { Text("2026-06-12") },
                        isError = uiState.tentativeDateError != null,
                        supportingText = uiState.tentativeDateError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    uiState.generalError?.let { genErr ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = genErr,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.createPlace() },
                        enabled = !uiState.isSubmitting,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (uiState.isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Guardar Lugar", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
