package com.jorge.kotlin_para_disenadores.repositories

import android.content.Context
import com.jorge.kotlin_para_disenadores.types.Descripciones
import com.jorge.kotlin_para_disenadores.types.Identificable
import com.jorge.kotlin_para_disenadores.utils.cargarJsonDesdeAssets
import com.jorge.kotlin_para_disenadores.utils.todosLosIdentificables

object JsonRepository {
    private val lookupMap = mutableMapOf<String, Identificable>()
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        val data = appContext.cargarJsonDesdeAssets("descripciones.json")
        construirMapaBusqueda(data)
    }

    fun getItemPorId(id: String) = lookupMap[id]

    private fun construirMapaBusqueda(data: Descripciones) {
        data.todosLosIdentificables().forEach { elemento ->
            lookupMap[elemento.id] = elemento
        }
    }
}
