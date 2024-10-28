package com.dicoding.event.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.event.data.local.EventEntity
import com.dicoding.event.data.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    fun fetchEvent(isActive: Int) = repository.getEvents(isActive)

    fun fetchEventDetail(id: Int) = repository.fetchEventDetail(id)

    fun searchEvent(query: String) = repository.searchEvent(query)

    fun getFavoriteEvent() = repository.getFavoriteEvents()

    fun saveEvents(event: EventEntity) {
        viewModelScope.launch {
            repository.setEventFavorite(event, true)
        }
    }

    fun deleteEvents(event: EventEntity) {
        viewModelScope.launch {
            repository.setEventFavorite(event, false)
        }
    }

}