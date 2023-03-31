package com.example.tallerpractico2dsm

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.tallerpractico2dsm.SalarioActivity.Companion.database
import com.example.tallerpractico2dsm.Salario

class AddSalarioActivity : AppCompatActivity() {
    private var edtSalBase: EditText? = null
    private var edtNombre: EditText? = null
    private var key = ""
    private var nombre = ""
    private var salBase = ""
    private var accion = ""
    private lateinit var  database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_salario)
        inicializar()
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtSalBase = findViewById<EditText>(R.id.edtSalBase)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtSalBase = findViewById<EditText>(R.id.edtSalBase)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtSalBase.setText(intent.getStringExtra("salBase").toString())
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
        val salBase: Double = edtSalBase?.text.toString().toDouble()

        database=FirebaseDatabase.getInstance().getReference("personas")

        // Se forma objeto persona
        val persona = Salario(salBase, nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(persona).addOnSuccessListener {
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
            val personasValues = persona.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to personasValues
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