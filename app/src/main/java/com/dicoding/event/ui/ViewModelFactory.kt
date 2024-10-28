package com.dicoding.event.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.event.data.repository.EventRepository
import com.dicoding.event.di.Injection
import com.dicoding.event.ui.settings.SettingsPreferences
import com.dicoding.event.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(
    private val eventRepository: EventRepository,
    private val pref: SettingsPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(eventRepository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
                Injection.providePreferences(context)
            )
        }.also { instance = it }
    }
}