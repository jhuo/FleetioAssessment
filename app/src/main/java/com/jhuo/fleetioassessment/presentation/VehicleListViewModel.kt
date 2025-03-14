package com.jhuo.fleetioassessment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.jhuo.fleetioassessment.data.mappers.toVehicle
import com.jhuo.fleetioassessment.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _filterName = MutableStateFlow<String?>(null)

    val vehiclePagingFlow = _filterName.flatMapLatest { status ->
        vehicleRepository.getVehiclesPaged(status).map { pagingData ->
            pagingData.map { it.toVehicle() }
        }.cachedIn(viewModelScope)
    }

    fun setNameFilter(nameFilter: String?) {
        _filterName.update { nameFilter }
    }
}