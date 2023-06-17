package com.example.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class ListadoCursos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cursoAdapter: CursoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_cursos)

        recyclerView = findViewById(R.id.listaCursos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cursoAdapter = CursoAdapter()
        recyclerView.adapter = cursoAdapter

        obtenerCursos()
    }

    private fun obtenerCursos() {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/inscripcion/"

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val cursos = ArrayList<AppCurso>()

                try {
                    for (i in 0 until response.length()) {
                        val cursoJson = response.getJSONObject(i)
                        val id = cursoJson.getInt("id")
                        val alumno = cursoJson.getInt("alumno")
                        val curso = cursoJson.getInt("curso")

                        // Crea un objeto Curso con los datos obtenidos
                        val cursoObj = AppCurso(id, alumno, curso)
                        cursos.add(cursoObj)
                    }

                    // Pasa la lista de cursos al adaptador
                    cursoAdapter.setCursos(cursos)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener los cursos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }
}
