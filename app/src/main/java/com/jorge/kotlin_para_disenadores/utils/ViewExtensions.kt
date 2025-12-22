package com.jorge.kotlin_para_disenadores.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.ocultarTeclado() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun SearchView.ocultarTeclado() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun SearchView.flujoDeTextoDeBusqueda(): Flow<String> = callbackFlow {
    val listener = object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            trySend(query.orEmpty())
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            trySend(newText.orEmpty())
            return true
        }
    }

    setOnQueryTextListener(listener)

    awaitClose {
        setOnQueryTextListener(null)
    }
}