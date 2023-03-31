package com.example.tallerpractico2dsm

class Salario {
    fun key(key: String?) {
    }

    var salBase: Double? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(salBase: Double?, nombre: String?) {
        this.salBase = salBase
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "salBase" to salBase,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}