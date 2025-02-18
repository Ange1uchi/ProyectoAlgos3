package mypackage

// Grafo no dirigido con soporte para costos
class GrafoNoDirigido(val numVertices: Int) {
    private val adyacencias = Array(numVertices) { mutableListOf<Arista>() }

    fun agregarArista(origen: Int, destino: Int, distancia: Double, consumo: Double) {
        adyacencias[origen].add(Arista(origen, destino, distancia, consumo))
        adyacencias[destino].add(Arista(destino, origen, distancia, consumo))
    }

    fun obtenerAdyacentes(v: Int): List<Arista> = adyacencias[v]

    override fun toString(): String {
        return adyacencias.withIndex().joinToString("\n") { (i, lista) ->
            "$i -> " + lista.joinToString(", ") { it.toString() }
        }
    }
}
