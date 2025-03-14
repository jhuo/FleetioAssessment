package com.jhuo.fleetioassessment.di

import com.jhuo.fleetioassessment.data.repository.VehicleRepositoryImpl
import com.jhuo.fleetioassessment.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindVehicleRepository(
        vehicleRepositoryImpl: VehicleRepositoryImpl
    ): VehicleRepository
}