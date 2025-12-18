package com.jorge.kotlin_para_disenadores

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jorge.kotlin_para_disenadores.constants.Ids
import com.jorge.kotlin_para_disenadores.databinding.ActivityMainBinding
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

        binding.mainActivity.setOnClickListener {
            mostrarDescripcion(Ids.ACTIVITY_MAIN_XML)
        }

        binding.activityMain.setOnClickListener {
            mostrarDescripcion(Ids.MAIN_ACTIVITY_KT)
        }
    }

    fun mostrarDescripcion(id: String) {
        val item = JsonRepository.getItemPorId(id)

        if (item == null) {
            Log.e("MainActivity", "Elemento con id \"${Ids.MAIN_ACTIVITY_KT}\" no encontrado")
            binding.pantallaDescripcion.setText("Tu búsqueda no ha obtnenido resultados")
            return
        }

        binding.pantallaDescripcion.setText(item.descripcion)
    }
}