package com.example.tallerpractico2dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpractico2dsm.Salario
import java.text.DecimalFormat


class AdaptadorSalario(private val context: Activity, var personas: List<Salario>) :
    ArrayAdapter<Salario?>(context, R.layout.salario_layout, personas) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.salario_layout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvSalBase = rowview.findViewById<TextView>(R.id.tvSalBase)
        val tvSalNeto = rowview.findViewById<TextView>(R.id.tvSalNeto)

        //Formato de 2 decimales
        val formatoDecimal = DecimalFormat("0.00")

        tvNombre.text = "Nombre: " + personas[position].nombre
        tvSalBase.text = "Salario base: " + personas[position].salBase
        val resultado= (personas[position].salBase.toString().toDouble())-(personas[position].salBase.toString().toDouble()*0.03)-(personas[position].salBase.toString().toDouble()*0.04)-(personas[position].salBase.toString().toDouble()*0.05)
        val resultadoRedondeado = formatoDecimal.format(resultado)
        tvSalNeto.text="Salario neto: " + resultadoRedondeado

        return rowview
    }
}