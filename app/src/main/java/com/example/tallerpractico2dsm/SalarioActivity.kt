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
import com.example.tallerpractico2dsm.SalarioActivity

class SalarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)
        setTitle("Calculadora salarial")
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
    var personas: MutableList<Salario>? = null
    var listaPersonas: ListView? = null

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaPersonas = findViewById<ListView>(R.id.ListaPersonas)
// Cuando el usuario haga clic en la lista (para editar registro)
        listaPersonas!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddSalarioActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", personas!![i].key)
                intent.putExtra("nombre", personas!![i].nombre)
                intent.putExtra("salBase", personas!![i].salBase)
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
                val ad = AlertDialog.Builder(this@SalarioActivity)
                ad.setMessage("¿Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    personas!![position].nombre?.let {
                        refPersonas.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@SalarioActivity,
                        "Registro borrado", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@SalarioActivity,
                            "Operación de borrado cancelada", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }
        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), AddSalarioActivity::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("salBase", "")
            startActivity(i)
        })
        personas = ArrayList<Salario>()
// Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
// Procedimiento que se ejecuta cuando hubo algun cambio
// en la base de datos
// Se actualiza la coleccion de personas
                personas!!.removeAll(personas!!)
                for (dato in dataSnapshot.getChildren()) {
                    val persona: Salario? = dato.getValue(Salario::class.java)
                    persona?.key(dato.key)
                    if (persona != null) {
                        personas!!.add(persona)
                    }
                }
                val adapter = AdaptadorSalario(
                    this@SalarioActivity,
                    personas as ArrayList<Salario>
                )
                listaPersonas!!.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refPersonas: DatabaseReference = database.getReference("personas")
    }
}

