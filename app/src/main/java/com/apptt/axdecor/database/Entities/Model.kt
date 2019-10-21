package com.apptt.axdecor.database.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model(
    @ColumnInfo(name = "id_model") @PrimaryKey @NonNull val idModel:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "type") val type:String,
    @ColumnInfo(name = "category") val category:String,
    @ColumnInfo(name = "fileAR") val fileAR:String,
    @ColumnInfo(name = "price") val price:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "file2D") val file2D:String
    //@ColumnInfo(name = "") val idProvider:Int,
    //@ColumnInfo(name = "id_model") val idARscene:Int
)