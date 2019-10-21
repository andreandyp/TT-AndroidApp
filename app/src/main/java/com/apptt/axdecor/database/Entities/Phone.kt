package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Phone(
    @ColumnInfo(name = "id_phone") @PrimaryKey @NotNull val idPhone:Int,
    @ColumnInfo(name = "phone") val phone:String,
    @ColumnInfo(name = "id_store") val idStore:Int
)