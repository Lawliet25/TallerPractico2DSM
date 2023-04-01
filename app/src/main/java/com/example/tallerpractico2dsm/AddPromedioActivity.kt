package com.example.tallerpractico2dsm

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.tallerpractico2dsm.PromedioActivity.Companion.database
import com.example.tallerpractico2dsm.Promedio

class AddPromedioActivity : AppCompatActivity() {
    private var edtNotaUno: EditText? = null
    private var edtNotaDos: EditText? = null
    private var edtNotaTres: EditText? = null
    private var edtNotaCuatro: EditText? = null
    private var edtNotaCinco: EditText? = null
    private var edtNombre: EditText? = null
    private var key = ""
    private var nombre = ""
    private var notaUno = ""
    private var notaDos = ""
    private var notaTres = ""
    private var notaCuatro = ""
    private var notaCinco = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_promedio)
        inicializar()
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtNotaUno = findViewById<EditText>(R.id.edtNotaUno)
        edtNotaDos = findViewById<EditText>(R.id.edtNotaDos)
        edtNotaTres = findViewById<EditText>(R.id.edtNotaTres)
        edtNotaCuatro = findViewById<EditText>(R.id.edtNotaCuatro)
        edtNotaCinco = findViewById<EditText>(R.id.edtNotaCinco)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtNotaUno = findViewById<EditText>(R.id.edtNotaUno)
        val edtNotaDos = findViewById<EditText>(R.id.edtNotaDos)
        val edtNotaTres = findViewById<EditText>(R.id.edtNotaTres)
        val edtNotaCuatro = findViewById<EditText>(R.id.edtNotaCuatro)
        val edtNotaCinco = findViewById<EditText>(R.id.edtNotaCinco)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtNotaUno.setText(intent.getStringExtra("notaUno").toString())
        }
        if (datos != null) {
            edtNotaDos.setText(intent.getStringExtra("notaDos").toString())
        }
        if (datos != null) {
            edtNotaTres.setText(intent.getStringExtra("notaTres").toString())
        }
        if (datos != null) {
            edtNotaCuatro.setText(intent.getStringExtra("notaCuatro").toString())
        }
        if (datos != null) {
            edtNotaCinco.setText(intent.getStringExtra("notaCinco").toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }


    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val notaUno: Double = edtNotaUno?.text.toString().toDouble()
        val notaDos: Double = edtNotaDos?.text.toString().toDouble()
        val notaTres: Double = edtNotaTres?.text.toString().toDouble()
        val notaCuatro: Double = edtNotaCuatro?.text.toString().toDouble()
        val notaCinco: Double = edtNotaCinco?.text.toString().toDouble()

        database=FirebaseDatabase.getInstance().getReference("alumnos")

        // Se forma objeto persona
        val alumno = Promedio(notaUno, notaDos, notaTres, notaCuatro, notaCinco, nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(alumno).addOnSuccessListener {
                Toast.makeText(this,"Se guardó con éxito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val alumnosValues = alumno.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to alumnosValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizó con éxito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}