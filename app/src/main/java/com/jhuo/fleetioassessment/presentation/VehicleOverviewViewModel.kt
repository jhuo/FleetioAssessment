package com.jhuo.fleetioassessment.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhuo.fleetioassessment.domain.repository.VehicleRepository
import com.jhuo.fleetioassessment.presentation.components.VehicleOverviewEvent
import com.jhuo.fleetioassessment.util.Resource
import com.jhuo.fleetioassessment.presentation.components.VehicleOverviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleOverviewViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val vehicleId = savedStateHandle.get<Int>("vehicleId")

    private val _overviewState = MutableStateFlow(VehicleOverviewState())
    val overviewState = _overviewState.asStateFlow()

    private val _vinError = MutableStateFlow<String?>(null)
    val vinError = _vinError.asStateFlow()

    private val _licensePlateError = MutableStateFlow<String?>(null)
    val licensePlateError = _licensePlateError.asStateFlow()

    fun validateInputs(vin: String, licensePlate: String) {
        _vinError.value = validateVin(vin)
        _licensePlateError.value = validateLicensePlate(licensePlate)
    }

    init {
        getVehicle(vehicleId ?: -1)
    }

    private fun getVehicle(id: Int) {
        viewModelScope.launch {
            _overviewState.update { it.copy(isLoading = true) }

            vehicleRepository.getVehicleOverview(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _overviewState.update { it.copy(isLoading = false, error = result.message) }
                    }
                    is Resource.Loading -> {
                        _overviewState.update { it.copy(isLoading = result.isLoading) }
                    }
                    is Resource.Success -> {
                        result.data?.let { vehicle ->
                            _overviewState.update { it.copy(vehicle = vehicle, isLoading = false) }
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: VehicleOverviewEvent) {
        when (event) {
            is VehicleOverviewEvent.UpdateVehicleDetails -> {
                updateVehicleDetails( event.vin, event.licensePlate)
            }
        }
    }

    private fun updateVehicleDetails(vin: String, licensePlate: String) {
        validateInputs(vin, licensePlate) // Validate before updating

        if (_vinError.value != null || _licensePlateError.value != null) {
            return // Stop execution if there are validation errors
        }

        viewModelScope.launch {
            _overviewState.update { it.copy(isLoading = true) }

            val updateRequest = mapOf(
                "vin" to vin,
                "license_plate" to licensePlate
            )

            val result = vehicleRepository.updateVehicleDetails(vehicleId ?: -1, updateRequest)
            when (result) {
                is Resource.Error -> {
                    _overviewState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Success -> {
                    result.data?.let { vehicle ->
                        _overviewState.update { it.copy(vehicle = vehicle, isLoading = false) }
                    }
                }

                is Resource.Loading -> {
                    _overviewState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun validateVin(vin: String): String? {
        return when {
            vin.isEmpty() -> "VIN cannot be empty"
            vin.length != 17 -> "VIN must be exactly 17 characters"
            !vin.matches(Regex("^[A-Za-z0-9]+$")) -> "VIN must contain only letters and numbers"
            else -> null
        }
    }

    fun validateLicensePlate(plate: String): String? {
        return when {
            plate.isEmpty() -> "License Plate cannot be empty"
            !plate.matches(Regex("^[A-Za-z0-9 ]+$")) -> "License Plate must be alphanumeric"
            else -> null
        }
    }
}