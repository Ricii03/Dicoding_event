package com.dicoding.event.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE Active = :isActive")
    fun getEvent(isActive: Int): LiveData<List<EventEntity>>

    @Query("SELECT EXISTS(SELECT * FROM events WHERE id = :eventId AND favorite = 1)")
    suspend fun isEventFavorite(eventId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("SELECT * FROM events WHERE favorite = 1")
    fun getFavoriteEvent(): LiveData<List<EventEntity>>

    @Update
    suspend fun updateEvents(events: EventEntity)

    @Query("DELETE FROM events WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT * FROM events WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<EventEntity>

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventDetail(eventId: String): LiveData<List<EventEntity>>


}