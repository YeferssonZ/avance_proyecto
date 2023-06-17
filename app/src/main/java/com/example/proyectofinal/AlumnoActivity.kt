package com.example.proyectofinal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.fragment_opciones_alumno.txtEmailLogin
import kotlinx.android.synthetic.main.fragment_opciones_alumno.txtUserLogin
import org.json.JSONArray

class AlumnoActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno)

        viewPager = findViewById(R.id.viewPagerAlumno)
        tabLayout = findViewById(R.id.tabLayoutAlumno)

        val adapter = AlumnoPagerAdapter(supportFragmentManager)
        adapter.addFragment(InicioFragmentAlumno(), "Inicio")
        adapter.addFragment(OpcionesFragmentAlumno(), "Opciones")

        adapter.addFragment(KeyFragmentAlumno(), "Key")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        val cuentaId = intent.getIntExtra("cuentaId", -1)
        if (cuentaId != -1) {
            obtenerUsuario(cuentaId)
        }


    }
    private fun obtenerUsuario(cuentaId: Int) {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/alumno/"

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val jsonArray = JSONArray(response)



                for (i in 0 until jsonArray.length()) {
                    val alumno = jsonArray.getJSONObject(i)
                    val id = alumno.getInt("cuenta")
                    if (id == cuentaId) {
                        val nombre = alumno.getString("nombre")
                        val email = alumno.getString("email")

                        // Actualizar los TextView con el nombre y el correo del usuario

                        txtUserLogin.text = nombre
                        txtEmailLogin.text = email
                        val alumnoId = alumno.getInt("id")
                        obtenerClave(alumnoId)
                        break
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener el usuario: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {}

        queue.add(request)
    }private fun obtenerClave(alumnoId: Int) {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/key/"

        val request = object : StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val key = jsonArray.getJSONObject(i)
                    val id = key.getInt("alumno")
                    if (id == alumnoId) {
                        val claveId = key.getInt("id")
                        AppData.claveId = claveId
                        break
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al obtener la clave: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {}

        queue.add(request)
    }







    private inner class AlumnoPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val fragmentList = mutableListOf<Fragment>()
        private val fragmentTitleList = mutableListOf<String>()


        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }
    }
}
