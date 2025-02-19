package mypackage

import java.util.LinkedList
import java.util.Queue

class ComparadorDeRutas(private val grafo: GrafoDirigido, private val mapaNombres: Map<String, Int>) {

    fun encontrarMejorRutaBFS() {
        val origen = mapaNombres["USB"] ?: return println("Error: No se encontró el nodo de origen.")
        val destinos = mapaNombres.values.filter { it != origen }

        // Si solo hay 2 nodos, calcular directamente la ruta sin BFS
        if (grafo.numVertices == 2) {
            val costoIda = grafo.obtenerMatrizCostos()[0][1]
            val costoVuelta = grafo.obtenerMatrizCostos()[1][0]
            val distanciaIda = grafo.obtenerMatrizDistancias()[0][1]
            val distanciaVuelta = grafo.obtenerMatrizDistancias()[1][0]

            if (costoIda != Double.POSITIVE_INFINITY && costoVuelta != Double.POSITIVE_INFINITY) {
                val costoTotal = costoIda + costoVuelta
                val distanciaTotal = distanciaIda + distanciaVuelta

                println("\nRuta Óptima Encontrada (Caso Simple - 2 Nodos):")
                println("   USB -> TRINIDAD -> USB")
                println("   Costo Total: %.2f lts de gasoil, Distancia Recorrida: %.2f km"
                    .format(costoTotal, distanciaTotal))
                return
            }
        }

        // Si hay más de 2 nodos, aplicar BFS
        var mejorRuta: List<Int>? = null
        var mejorCosto = Double.POSITIVE_INFINITY
        var mejorDistancia = Double.POSITIVE_INFINITY

        val queue: Queue<List<Int>> = LinkedList()
        queue.add(listOf(origen))

        while (queue.isNotEmpty()) {
            val rutaActual = queue.poll()
            val nodoActual = rutaActual.last()

            // Si la ruta ya pasó por todos los nodos y regresa al origen, evalúa si es la mejor
            if (rutaActual.toSet().containsAll(destinos) && nodoActual == origen) {
                val (costoTotal, distanciaTotal) = calcularCostoDistancia(rutaActual)
                if (costoTotal < mejorCosto) {
                    mejorCosto = costoTotal
                    mejorDistancia = distanciaTotal
                    mejorRuta = rutaActual
                }
            }

            // Expandir rutas posibles
            for (vecino in grafo.obtenerMatrizCostos().indices) {
                if (grafo.obtenerMatrizCostos()[nodoActual][vecino] != Double.POSITIVE_INFINITY &&
                    vecino !in rutaActual.drop(1) // Evita ciclos
                ) {
                    queue.add(rutaActual + vecino)
                }
            }
        }

        if (mejorRuta != null) {
            val nombresRuta = mejorRuta.joinToString(" -> ") { index ->
                mapaNombres.entries.find { it.value == index }?.key ?: "Desconocido"
            }
            println("\nRuta Óptima Encontrada con BFS:")
            println("   $nombresRuta")
            println("   Costo Total: %.2f lts de gasoil, Distancia Recorrida: %.2f km".format(mejorCosto, mejorDistancia))
        } else {
            println("\nNo se encontró una ruta válida.")
        }
    }

    private fun calcularCostoDistancia(ruta: List<Int>): Pair<Double, Double> {
        var costoTotal = 0.0
        var distanciaTotal = 0.0

        for (i in 0 until ruta.size - 1) {
            val origen = ruta[i]
            val destino = ruta[i + 1]
            costoTotal += grafo.obtenerMatrizCostos()[origen][destino]
            distanciaTotal += grafo.obtenerMatrizDistancias()[origen][destino]
        }

        return Pair(costoTotal, distanciaTotal)
    }
}
