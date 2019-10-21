package com.apptt.axdecor.Entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Persona(val persona: String) { FISICA("FISICA"), MORAL("MORAL") }
enum class Tipo(val tipo:String) {ILUMINACION("ILUMNIACION"),PISO("PISO"),MUEBLES("MUEBLES"),PINTURA("PINTURA"),DECORACION("DECORACION")}
enum class Categoria(val categoria:String){BAJO("BAJO"),MEDIO("MEDIO"),ALTO("ALTO")}
@Entity
data class Provider(
    @ColumnInfo(name = "id_provider") @PrimaryKey @NonNull val idProvider: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rfc") val rfc: String,
    @ColumnInfo(name = "razon_social") val razonSocial: String,
    @ColumnInfo(name = "tipo") val tipo:Tipo,
    @ColumnInfo(name = "persona") val persona: Persona,
    @ColumnInfo(name = "categoria") val categoria: Categoria
)