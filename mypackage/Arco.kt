package mypackage

// Clase Arco que maneja costos
open class Arco(val inicio: Int, val fin: Int, val costo: Double = 0.0) : Lado(inicio, fin) {

    fun fuente(): Int = inicio
    fun sumidero(): Int = fin

    override fun toString(): String {
        return """
        inicio: $inicio
        fin: $fin
        Costo: ${"%.2f".format(costo)}
        """
    }
}
