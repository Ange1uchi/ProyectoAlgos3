package mypackage

// Clase Arista que representa conexiones con distancia y consumo
open class Arista(v: Int, u: Int, val distancia: Double = 0.0, val consumo: Double = 0.0) : Lado(v, u), Comparable<Arista> {

    // Comparación basada en distancia
    override fun compareTo(other: Arista): Int {
        return when {
            this.distancia > other.distancia -> 1
            this.distancia == other.distancia -> this.consumo.compareTo(other.consumo) // Si la distancia es igual, comparar por consumo
            else -> -1
        }
    }

    // Representación en string
    override fun toString(): String {
        return """
        v: $v
        w: $u
        Distancia: ${"%.2f".format(distancia)} km
        Consumo: ${"%.2f".format(consumo)} L
        """.trimIndent()
    }
}
