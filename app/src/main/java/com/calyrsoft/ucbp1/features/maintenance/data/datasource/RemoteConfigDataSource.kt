package com.calyrsoft.ucbp1.features.maintenance.data.datasource

import android.util.Log
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class RemoteConfigDataSource {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("maintenance" to false))

        Log.d("Maintenance", "Remote Config inicializado")
    }

    suspend fun fetchConfig(): Boolean {
        return try {
            Log.d("Maintenance", "🔍 Iniciando fetch de Remote Config...")
            val result = remoteConfig.fetchAndActivate().await()

            val currentValue = remoteConfig.getBoolean("maintenance")
            Log.d("Maintenance", "📊 Fetch completado. Resultado: $result, Valor: $currentValue")

            result
        } catch (e: Exception) {
            Log.e("Maintenance", "❌ Error en fetchConfig", e)
            false
        }
    }

    fun isMaintenanceMode(): Boolean {
        val value = remoteConfig.getBoolean("maintenance")
        Log.d("Maintenance", "🔎 Consultando maintenance mode: $value")
        return value
    }
}