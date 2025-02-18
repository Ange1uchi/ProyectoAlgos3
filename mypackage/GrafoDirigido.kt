package mypackage

// Grafo dirigido con soporte para costos
class GrafoDirigido(val numVertices: Int) {
    private val adyacencias = Array(numVertices) { mutableListOf<Arco>() }

    fun agregarArista(origen: Int, destino: Int, costo: Double = 0.0) {
        adyacencias[origen].add(Arco(origen, destino, costo))
    }

    fun obtenerAdyacentes(v: Int): List<Arco> = adyacencias[v]

    override fun toString(): String {
        return adyacencias.withIndex().joinToString("\n") { (i, lista) ->
            "$i -> " + lista.joinToString(", ") { it.toString() }
        }
    }
}
