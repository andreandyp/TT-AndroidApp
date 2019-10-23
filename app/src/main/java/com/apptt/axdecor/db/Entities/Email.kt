package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class Email(
    @ColumnInfo(name = "id_email") @PrimaryKey @NonNull val idEmail:Int,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "id_store")
    @ForeignKey(
        entity = Store::class,
        parentColumns = ["id_store"],
        childColumns = ["id_store"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )val idStore:Int
)