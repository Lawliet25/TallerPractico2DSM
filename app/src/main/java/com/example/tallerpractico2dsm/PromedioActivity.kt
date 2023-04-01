package com.example.tallerpractico2dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class PromedioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)
        setTitle("Promedio de un estudiante")
        inicializar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_muestra, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id== R.id.opcion1)
        {
            Toast.makeText(this, "Cálculo de promedio de un estudiante", Toast.LENGTH_LONG).show()
            val intent=
                Intent(this,PromedioActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        if(id== R.id.opcion2)
        {
            Toast.makeText(this, "Calculadora salarial", Toast.LENGTH_LONG).show()
            val intent=
                Intent(this,SalarioActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        if(id== R.id.menu)
        {
            Toast.makeText(this, "Inicio", Toast.LENGTH_LONG).show()
            val intent=
                Intent(this,MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    // Ordenamiento para hacer la consultas a los datos
    var consultaOrdenada: Query = refPersonas.orderByChild("nombre")
    var alumnos: MutableList<Promedio>? = null
    var listaPersonas: ListView? = null

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaPersonas = findViewById<ListView>(R.id.ListaPersonas)
// Cuando el usuario haga clic en la lista (para editar registro)
        listaPersonas!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddPromedioActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", alumnos!![i].key)
                intent.putExtra("nombre", alumnos!![i].nombre)
                intent.putExtra("notaUno", alumnos!![i].notaUno)
                intent.putExtra("notaDos", alumnos!![i].notaDos)
                intent.putExtra("notaTres", alumnos!![i].notaTres)
                intent.putExtra("notaCuatro", alumnos!![i].notaCuatro)
                intent.putExtra("notaCinco", alumnos!![i].notaCinco)
                startActivity(intent)
            }
        })
// Cuando el usuario hace un LongClic (clic sin soltar elemento por más de 2 segundos)
// Es por que el usuario quiere eliminar el registro
        listaPersonas!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,l: Long
            ): Boolean {
// Preparando cuadro de dialogo para preguntar al usuario
// Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(this@PromedioActivity)
                ad.setMessage("¿Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    alumnos!![position].nombre?.let {
                        refPersonas.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@PromedioActivity,
                        "Registro borrado", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@PromedioActivity,
                            "Operación de borrado cancelada", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), AddPromedioActivity::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("notaUno", "")
            i.putExtra("notaDos", "")
            i.putExtra("notaTres", "")
            i.putExtra("notaCuatro", "")
            i.putExtra("notaCinco", "")
            startActivity(i)
        })
        alumnos = ArrayList<Promedio>()
// Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
// Procedimiento que se ejecuta cuando hubo algun cambio
// en la base de datos
// Se actualiza la coleccion de personas
                alumnos!!.removeAll(alumnos!!)
                for (dato in dataSnapshot.getChildren()) {
                    val alumno: Promedio? = dato.getValue(Promedio::class.java)
                    alumno?.key(dato.key)
                    if (alumno != null) {
                        alumnos!!.add(alumno)
                    }
                }
                val adapter = AdaptadorPromedio(
                    this@PromedioActivity,
                    alumnos as ArrayList<Promedio>
                )
                listaPersonas!!.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refPersonas: DatabaseReference = database.getReference("alumnos")
    }
}