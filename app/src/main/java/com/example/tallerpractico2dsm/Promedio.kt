package com.example.tallerpractico2dsm

class Promedio {
    fun key(key: String?) {
    }

    var notaUno: Double? = null
    var notaDos: Double? = null
    var notaTres: Double? = null
    var notaCuatro: Double? = null
    var notaCinco: Double? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(notaUno: Double?, notaDos: Double?, notaTres: Double?, notaCuatro: Double?, notaCinco: Double?, nombre: String?) {
        this.notaUno = notaUno
        this.notaDos = notaDos
        this.notaTres = notaTres
        this.notaCuatro= notaCuatro
        this.notaCinco = notaCinco
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "notaUno" to notaUno,
            "notaDos" to notaDos,
            "notaTres" to notaTres,
            "notaCuatro" to notaCuatro,
            "notaCinco" to notaCinco,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}