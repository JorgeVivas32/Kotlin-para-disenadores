package com.jorge.kotlin_para_disenadores

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.italic
import androidx.core.text.scale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jorge.kotlin_para_disenadores.constants.IdsDescripciones
import com.jorge.kotlin_para_disenadores.databinding.ActivityMainBinding
import com.jorge.kotlin_para_disenadores.managers.ManagerBusqueda
import com.jorge.kotlin_para_disenadores.repositories.JsonRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var texto: SpannableStringBuilder

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

        texto = SpannableStringBuilder()
        JsonRepository.init(this)
        ManagerBusqueda(binding.busqueda).configurarOnSubmit()

        cambiarColorSearchView(Color.WHITE, Color.LTGRAY)
        cambiarColorIconosSearchView(Color.WHITE)

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
        texto.clear()

        texto.bold { appendLine(item.titulo) }
        texto.scale(0.9f) { italic { appendLine("Dificultad: ${item.nivel}") } }
        texto.appendLine()
        texto.scale(0.8f) { appendLine(item.descripcion) }
        texto.appendLine()
        texto.scale(0.75f) { appendLine("Relacionados: ${item.relacionados.joinToString(", ")}") }

        binding.pantallaDescripcion.text = texto
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

    fun cambiarColorSearchView(@ColorInt colorTexto: Int, @ColorInt colorHint: Int) {
        val editText =
            binding
                .busqueda
                .findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        editText.setTextColor(colorTexto)
        editText.setHintTextColor(colorHint)
    }

    fun cambiarColorIconosSearchView(@ColorInt colorIconos: Int) {
        // Cambiar color del ícono de búsqueda (lupa)
        val searchIcon =
            binding
                .busqueda.findViewById<ImageView>(androidx.appcompat.R.id.search_button)

        // Cambiar color del ícono de cierre (X)
        val closeIcon =
            binding
                .busqueda
                .findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        // Cambiar color del ícono de la lupa dentro del campo (search_mag_icon)
        val magIcon =
            binding
                .busqueda
                .findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)

        searchIcon.setColorFilter(colorIconos)
        closeIcon.setColorFilter(colorIconos)
        magIcon.setColorFilter(colorIconos)
    }
}