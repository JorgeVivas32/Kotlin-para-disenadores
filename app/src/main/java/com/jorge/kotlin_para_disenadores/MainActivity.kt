package com.jorge.kotlin_para_disenadores

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
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
import androidx.lifecycle.lifecycleScope
import com.jorge.kotlin_para_disenadores.constants.ConstantesTiempo
import com.jorge.kotlin_para_disenadores.constants.IdsDescripciones
import com.jorge.kotlin_para_disenadores.databinding.ActivityMainBinding
import com.jorge.kotlin_para_disenadores.managers.ManagerBusqueda
import com.jorge.kotlin_para_disenadores.managers.ResultadoBusqueda
import com.jorge.kotlin_para_disenadores.repositories.JsonRepository
import com.jorge.kotlin_para_disenadores.types.Identificable
import com.jorge.kotlin_para_disenadores.utils.actualizarEstadoVistas
import com.jorge.kotlin_para_disenadores.utils.ocultarTeclado
import com.jorge.kotlin_para_disenadores.utils.scrollHastaArribaDelTodo
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var texto: SpannableStringBuilder
    private lateinit var managerBusqueda: ManagerBusqueda

    private val todasLasVistas by lazy {
        listOf(
            binding.mainActivity,
            binding.activityMain,
            binding.andoridManifest,
            binding.buildGradle,
            binding.constructor,
            binding.metodo,
            binding.instancia
        )
    }

    // ✅ Map para relacionar vista con su ID de concepto
    private val vistaAConcepto by lazy {
        mapOf(
            binding.mainActivity to IdsDescripciones.MAINACTIVITY,
            binding.activityMain to IdsDescripciones.ACTIVITY_MAIN_XML,
            binding.andoridManifest to IdsDescripciones.ANDROID_MANIFEST,
            binding.buildGradle to IdsDescripciones.GRADLE,
            binding.constructor to IdsDescripciones.CONSTRUCTOR,
            binding.metodo to IdsDescripciones.METODO,
            binding.instancia to IdsDescripciones.INSTANCIA
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        texto = SpannableStringBuilder()
        managerBusqueda = ManagerBusqueda(
            searchView = binding.busqueda,
            delayMilisegundos = ConstantesTiempo.DEBOUNCE_DELAY_EN_MILIEGUNDOS
        )
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar todos los elementos como "inactivos"
        actualizarEstadoVistas(inactivas = todasLasVistas)
        JsonRepository.init(this)

        cambiarColorSearchView(Color.WHITE, Color.LTGRAY)
        cambiarColorIconosSearchView(Color.WHITE)

        lifecycleScope.launch {
            managerBusqueda.configurar(::manejarResultadoBusqueda)
        }


        configurarClicksVistas()

        binding.limpiarPantalla.setOnClickListener {
            actualizarEstadoVistas(inactivas = todasLasVistas)
            managerBusqueda.limpiar()
            binding.pantallaDescripcion.text = ""
        }
    }

    fun configurarClicksVistas() {
        todasLasVistas.forEach { vista ->
            vista.setOnClickListener {
                // Obtener todas las vistas excepto la actual
                val inactivas = todasLasVistas.filter { it != vista }
                actualizarEstadoVistas(activa = vista, inactivas = inactivas)

                // Obtener el ID del concepto desde el map
                val conceptoId = vistaAConcepto[vista] ?: return@setOnClickListener
                mostrarDescripcion(conceptoId, vista)
            }
        }
    }

    fun mostrarDescripcion(id: String, view: View) {
        view.ocultarTeclado()
        managerBusqueda.limpiar()

        val item = JsonRepository.getItemPorId(id)

        if (item == null) {
            Log.e("MainActivity", "Elemento con id \"${id}\" no encontrado")
            binding.pantallaDescripcion.text = "Tu búsqueda no ha obtnenido resultados"
            return
        }

        binding.scrollDescripcion.scrollHastaArribaDelTodo()
        mostrarConcepto(item)
    }

    fun mostrarConcepto(item: Identificable) {
        binding.scrollDescripcion.scrollHastaArribaDelTodo()
        binding.busqueda.ocultarTeclado()

        texto.clear()

        texto.bold { appendLine(item.titulo) }
        texto.scale(0.9f) { italic { appendLine("Dificultad: ${item.nivel}") } }
        texto.appendLine()
        texto.scale(0.8f) { appendLine(item.descripcion) }
        texto.appendLine()
        texto.scale(0.75f) { appendLine("Relacionados: ${item.relacionados.joinToString(", ")}") }

        binding.pantallaDescripcion.text = texto
    }

    fun mostrarMultiplesResultados(items: Collection<Identificable>) {
        // TODO: "Añadir la lógica para manejar múltimples resultados"

        binding.scrollDescripcion.scrollHastaArribaDelTodo()

        Log.i("MainActivity", "Añadir la lógica para manejar múltimples resultados")
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

    fun manejarResultadoBusqueda(resultado: ResultadoBusqueda) {
        when (resultado) {
            is ResultadoBusqueda.Encontrado -> {
                binding.busqueda.ocultarTeclado()
                mostrarConcepto(resultado.item)
                actualizarEstadoVistas(inactivas = todasLasVistas)
            }

            is ResultadoBusqueda.Multiples -> {
                mostrarMultiplesResultados(resultado.items)
                actualizarEstadoVistas(inactivas = todasLasVistas)
            }

            is ResultadoBusqueda.NoEncontrado -> {
                binding.pantallaDescripcion.text =
                    "Tu búsqueda \"${resultado.query}\" no ha obtnenido resultados"
                actualizarEstadoVistas(inactivas = todasLasVistas)
            }
        }
    }
}