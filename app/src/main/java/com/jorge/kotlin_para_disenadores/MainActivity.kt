package com.jorge.kotlin_para_disenadores

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jorge.kotlin_para_disenadores.constants.IdsDescripciones
import com.jorge.kotlin_para_disenadores.databinding.ActivityMainBinding
import com.jorge.kotlin_para_disenadores.managers.ManagerBusqueda
import com.jorge.kotlin_para_disenadores.repositories.JsonRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        JsonRepository.init(this)
        ManagerBusqueda(binding.busqueda).configurarOnSubmit()

        binding.mainActivity.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.MAINACTIVITY, it)
        }

        binding.activityMain.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.ACTIVITY_MAIN_XML, it)
        }

        binding.andoridManifest.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.ANDROID_MANIFEST, it)
        }

        binding.buildGradle.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.GRADLE, it)
        }

        binding.constructor.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.CONSTRUCTOR, it)
        }

        binding.metodo.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.METODO, it)
        }

        binding.instancia.setOnClickListener {
            mostrarDescripcion(IdsDescripciones.INSTANCIA, it)
        }

        binding.limpiarPantalla.setOnClickListener {
            binding.pantallaDescripcion.text = ""
        }
    }

    fun mostrarDescripcion(id: String, view: View) {
        view.ocultarTeclado()

        val item = JsonRepository.getItemPorId(id)

        if (item == null) {
            Log.e("MainActivity", "Elemento con id \"${id}\" no encontrado")
            binding.pantallaDescripcion.text = "Tu búsqueda no ha obtnenido resultados"
            return
        }

        scrollHastaArribaDelTodo()
        binding.pantallaDescripcion.text = item.descripcion
    }

    fun scrollHastaArribaDelTodo() {
        binding.scrollDescripcion.post {
            binding.scrollDescripcion.fullScroll(View.FOCUS_UP)
            binding.scrollDescripcion.smoothScrollTo(0, 0)
        }
    }

    fun View.ocultarTeclado() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}