package com.jorge.kotlin_para_disenadores.types

import com.google.gson.annotations.SerializedName

interface Identificable {
    val id: String
    val titulo: String
    val descripcion: String
    val relacionados: List<String>
    val nivel: String
}

data class Descripciones(
    @SerializedName("activities_y_ciclo")
    val activitiesYCiclo: List<ActivitiesYCiclo>,
    @SerializedName("archivos_y_configuracion")
    val archivosYConfiguracion: List<ArchivosYConfiguracion>,
    val botones: List<Botones>,
    @SerializedName("componentes_ui")
    val componentesUi: List<ComponentesUi>,
    @SerializedName("conceptos_fundamentales")
    val conceptosFundamentales: List<ConceptosFundamentale>,
    @SerializedName("estructura_proyecto")
    val estructuraProyecto: List<EstructuraProyecto>,
    val interaccion: List<Interaccion>,
    @SerializedName("kotlin_avanzado_y_log")
    val kotlinAvanzadoYLog: List<KotlinAvanzadoYLog>,
    val layouts: List<Layout>,
    @SerializedName("notificaciones_ui")
    val notificacionesUi: List<NotificacionesUi>,
    @SerializedName("programacion_basica")
    val programacionBasica: List<ProgramacionBasica>,
    @SerializedName("programacion_poo")
    val programacionPoo: List<ProgramacionPoo>,
    @SerializedName("tipos_de_datos")
    val tiposDeDatos: List<TiposDeDato>
)

data class ActivitiesYCiclo(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class ArchivosYConfiguracion(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class Botones(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class ComponentesUi(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class ConceptosFundamentale(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class EstructuraProyecto(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class Interaccion(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class KotlinAvanzadoYLog(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class Layout(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class NotificacionesUi(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class ProgramacionBasica(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class ProgramacionPoo(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable

data class TiposDeDato(
    override val descripcion: String,
    override val id: String,
    override val nivel: String,
    override val relacionados: List<String>,
    override val titulo: String
) : Identificable