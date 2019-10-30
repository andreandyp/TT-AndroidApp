package com.apptt.axdecor.db.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TypeModel(
    @ColumnInfo(name = "id_type") @PrimaryKey @NonNull val idType:Int,
    @ColumnInfo(name = "name_type") val nameType:String
)