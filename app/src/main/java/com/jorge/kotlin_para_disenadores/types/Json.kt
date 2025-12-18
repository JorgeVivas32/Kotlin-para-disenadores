package com.jorge.kotlin_para_disenadores.types

import com.google.gson.annotations.SerializedName

interface Identificable {
    val id: String
    val titulo: String
    val descripcion: String
}

data class Descripciones(
    val layouts: List<ArchivosYCarpeta>,

    @SerializedName("componentes_ui")
    val componentesUI: List<ArchivosYCarpeta>,

    @SerializedName("archivos_y_carpetas")
    val archivosYCarpetas: List<ArchivosYCarpeta>,

    @SerializedName("conceptos_clave")
    val conceptosClave: List<ArchivosYCarpeta>
)

data class ArchivosYCarpeta(
    override val id: String,
    override val titulo: String,
    override val descripcion: String
) : Identificable
