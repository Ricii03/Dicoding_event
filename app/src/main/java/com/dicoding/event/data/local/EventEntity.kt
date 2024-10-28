package com.dicoding.event.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "events")
@Parcelize
data class EventEntity(

    @PrimaryKey(false)
    @ColumnInfo(name = "id")
    var eventId: String,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,

    @ColumnInfo(name = "active")
    var isActive: Boolean = false,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean


    ) : Parcelable
