package com.jhuo.fleetioassessment.di

import android.app.Application
import androidx.room.Room
import com.jhuo.fleetioassessment.data.model.local.VehicleDatabase
import com.jhuo.fleetioassessment.data.remote.VehicleApiService
import com.jhuo.fleetioassessment.util.Constants.BASE_URL
import com.jhuo.fleetioassessment.util.Constants.VEHICLE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideVehicleApiService(retrofit: Retrofit): VehicleApiService {
        return retrofit.create(VehicleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVehicleDatabase(app: Application): VehicleDatabase {
        return Room.databaseBuilder(
            app,
            VehicleDatabase::class.java,
            VEHICLE_DATABASE
        ).fallbackToDestructiveMigration().build()
    }

}