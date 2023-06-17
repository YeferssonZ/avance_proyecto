package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CursoAdapter : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    private val cursos = ArrayList<AppCurso>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_cursos, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursos[position]
        holder.bind(curso)
    }

    override fun getItemCount(): Int {
        return cursos.size
    }

    fun setCursos(cursos: List<AppCurso>) {
        this.cursos.clear()
        this.cursos.addAll(cursos)
        notifyDataSetChanged()
    }

    class CursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(curso: AppCurso) {
            // Aquí configura los valores de los elementos de vista en el ViewHolder
            // Usar itemView.findViewById para obtener las referencias a los elementos de vista

            // Ejemplo: Actualizar el TextView con el ID del curso
            val txtCursoId: TextView = itemView.findViewById(R.id.txtCursoId)
            val txtAlumnoId: TextView = itemView.findViewById(R.id.txtAlumnoId)
            val txtCursoNombre: TextView = itemView.findViewById(R.id.txtCursoNombre)

            txtCursoId.text = "ID del curso: ${curso.id}"
            txtAlumnoId.text = "ID del alumno: ${curso.alumno}"
            txtCursoNombre.text = "Nombre del curso: ${curso.curso}"
        }
    }
}