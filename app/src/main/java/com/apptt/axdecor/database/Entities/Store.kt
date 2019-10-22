package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Store(
    @ColumnInfo(name = "id_store") @PrimaryKey @NotNull val idStore:Int,
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