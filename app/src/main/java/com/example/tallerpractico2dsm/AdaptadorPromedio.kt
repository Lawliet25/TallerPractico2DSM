package com.example.tallerpractico2dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.text.DecimalFormat
import com.example.tallerpractico2dsm.Promedio


class AdaptadorPromedio(private val context: Activity, var alumnos: List<Promedio>) :
    ArrayAdapter<Promedio?>(context, R.layout.promedio_layout, alumnos) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.promedio_layout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvNotaUno = rowview.findViewById<TextView>(R.id.tvNotaUno)
        val tvNotaDos = rowview.findViewById<TextView>(R.id.tvNotaDos)
        val tvNotaTres = rowview.findViewById<TextView>(R.id.tvNotaTres)
        val tvNotaCuatro = rowview.findViewById<TextView>(R.id.tvNotaCuatro)
        val tvNotaCinco = rowview.findViewById<TextView>(R.id.tvNotaCinco)
        val tvPromedio = rowview.findViewById<TextView>(R.id.tvPromedio)
        val tvResultado = rowview.findViewById<TextView>(R.id.tvResultado)
        //Formato de 2 decimales
        val formatoDecimal = DecimalFormat("0.00")

        tvNombre.text = "Nombre: " + alumnos[position].nombre
        tvNotaUno.text = "Nota 1: " + alumnos[position].notaUno
        tvNotaDos.text = "Nota 2: " + alumnos[position].notaDos
        tvNotaTres.text = "Nota 3: " + alumnos[position].notaTres
        tvNotaCuatro.text = "Nota 4: " + alumnos[position].notaCuatro
        tvNotaCinco.text = "Nota 5: " + alumnos[position].notaCinco


        val promedio= ((alumnos[position].notaUno.toString().toDouble())+(alumnos[position].notaDos.toString().toDouble())+(alumnos[position].notaTres.toString().toDouble())+(alumnos[position].notaCuatro.toString().toDouble())+(alumnos[position].notaCinco.toString().toDouble()))/5
        val resultadoRedondeado = formatoDecimal.format(promedio)
        tvPromedio.text="Promedio de notas: " + resultadoRedondeado

        if (resultadoRedondeado.toString().toDouble()>=6.0){
            tvResultado.text= "Felicidades, usted aprobó."
        } else{
            tvResultado.text= "Qué lástima, usted reprobó."
        }

        return rowview
    }
}