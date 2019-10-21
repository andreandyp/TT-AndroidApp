package com.apptt.axdecor.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity
data class Email(
    @ColumnInfo(name = "id_email") @PrimaryKey @NotNull val idEmail:Int,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "id_store") val idStore:Int
)