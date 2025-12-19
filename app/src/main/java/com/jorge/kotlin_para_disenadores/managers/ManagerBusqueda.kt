package com.jorge.kotlin_para_disenadores.managers

import androidx.appcompat.widget.SearchView

class ManagerBusqueda(private val searchView: SearchView) {
    fun configurarOnSubmit() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("Texto enviado por el usuario \"$query\"")
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }
}