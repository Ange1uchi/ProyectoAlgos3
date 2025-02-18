package mypackage

import java.util.PriorityQueue

// Clase para representar un nodo en la cola de prioridad
data class Nodo(val id: Int, val distancia: Double) : Comparable<Nodo> {
    override fun compareTo(other: Nodo): Int = distancia.compareTo(other.distancia)
}

class Dijkstra(private val numNodos: Int) {
    private val grafo = mutableMapOf<Int, MutableList<Pair<Int, Double>>>()

    fun agregarArista(origen: Int, destino: Int, peso: Double) {
        grafo.getOrPut(origen) { mutableListOf() }.add(Pair(destino, peso))
        grafo.getOrPut(destino) { mutableListOf() }.add(Pair(origen, peso)) // Si es no dirigido
    }

    fun encontrarRutaMasCorta(inicio: Int, destino: Int): Pair<List<Int>, Double> {
        val distancias = DoubleArray(numNodos) { Double.MAX_VALUE }
        val previos = IntArray(numNodos) { -1 }
        val pq = PriorityQueue<Nodo>()
        
        distancias[inicio] = 0.0
        pq.add(Nodo(inicio, 0.0))
        
        while (pq.isNotEmpty()) {
            val actual = pq.poll()
            
            if (actual.id == destino) break
            
            for ((vecino, peso) in grafo[actual.id] ?: emptyList()) {
                val nuevaDistancia = distancias[actual.id] + peso
                if (nuevaDistancia < distancias[vecino]) {
                    distancias[vecino] = nuevaDistancia
                    previos[vecino] = actual.id
                    pq.add(Nodo(vecino, nuevaDistancia))
                }
            }
        }
        
        // Reconstruir el camino
        val ruta = mutableListOf<Int>()
        var nodoActual = destino
        while (nodoActual != -1) {
            ruta.add(nodoActual)
            nodoActual = previos[nodoActual]
        }
        ruta.reverse()
        
        return Pair(ruta, distancias[destino])
    }
}
