package com.dicoding.event.di

import android.content.Context
import com.dicoding.event.data.local.EventDatabase
import com.dicoding.event.data.remote.ApiConfig
import com.dicoding.event.data.repository.EventRepository
import com.dicoding.event.ui.settings.SettingsPreferences
import com.dicoding.event.ui.settings.dataStore


object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }

    fun providePreferences(context: Context): SettingsPreferences {
        return SettingsPreferences.getInstance(context.dataStore)
    }

}