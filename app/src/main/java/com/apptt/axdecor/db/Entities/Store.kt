package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Store(
    @ColumnInfo(name = "id_store") @PrimaryKey @NonNull val idStore:Int,
    @ColumnInfo(name = "address")  val address:String,
    @ColumnInfo(name = "id_provider")
    @ForeignKey(
        entity = Provider::class,
        parentColumns = ["id_provider"],
        childColumns = ["id_provider"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ) val idProvider: Int
)