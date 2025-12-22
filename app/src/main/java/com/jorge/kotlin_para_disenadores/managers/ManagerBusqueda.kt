package com.jorge.kotlin_para_disenadores.managers

import androidx.appcompat.widget.SearchView
import com.jorge.kotlin_para_disenadores.repositories.JsonRepository
import com.jorge.kotlin_para_disenadores.types.Identificable
import com.jorge.kotlin_para_disenadores.utils.flujoDeTextoDeBusqueda
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class ManagerBusqueda(
    private val searchView: SearchView,
    private val delayMilisegundos: Int
) {
    @OptIn(FlowPreview::class)
    suspend fun configurar(onResultado: (ResultadoBusqueda) -> Unit) {
        searchView
            .flujoDeTextoDeBusqueda()
            .filter { it.isNotEmpty() }
            .map { it.trim() }
            .debounce(delayMilisegundos.toLong())
            .distinctUntilChanged()
            .collect { query ->
                onResultado(buscar(query))
            }
    }

    private fun buscar(query: String): ResultadoBusqueda {
        // 1. Búsqueda exacta por ID (O(1))
        JsonRepository
            .getItemPorId(query.normalizar())
            ?.let {
                return ResultadoBusqueda.Encontrado(it)
            }

        // 2. Búsqueda por coincidencias parciales
        val coincidencias =
            JsonRepository
                .getTodosLosItems()
                .values
                .filter { item ->
                    item.titulo.normalizar().contains(query.normalizar()) ||
                            item.id.normalizar().contains(query.normalizar())
                }

        return when (coincidencias.size) {
            1 -> ResultadoBusqueda.Encontrado(coincidencias.first())
            in 2..Int.MAX_VALUE -> ResultadoBusqueda.Multiples(coincidencias.toList())
            else -> ResultadoBusqueda.NoEncontrado(query)
        }
    }

    fun limpiar() {
        searchView.setQuery("", false)
        searchView.clearFocus()
    }
}

// Resultado de la búsqueda (sealed class)
sealed class ResultadoBusqueda {
    data class Encontrado(val item: Identificable) : ResultadoBusqueda()
    data class Multiples(val items: List<Identificable>) : ResultadoBusqueda()
    data class NoEncontrado(val query: String) : ResultadoBusqueda()
}

private fun String.normalizar(): String {
    return lowercase().replace(" ", "").trim()
}
