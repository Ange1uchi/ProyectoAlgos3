package mypackage

// Mapa de nombres de las paradas
val mapaNombres = mapOf(
    0 to "USB (sede Sartenejas)",
    1 to "McDonald's Trinidad",
    2 to "Chacaito"
)

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: Debes proporcionar el nombre del archivo de entrada.")
        return
    }

    val (grafoRuta1, grafoRuta2) = GrafoDirigido.desdeArchivo(args[0]) // ✅ ¡Ahora funciona!

    if (grafoRuta1 == null || grafoRuta2 == null) {
        println("Error: No se pudieron cargar las rutas correctamente.")
        return
    }

    println("\n===================================")
    println("            Ruta 1           ")
    println("===================================")
    for (i in 0 until grafoRuta1.numVertices) {
        for (j in 0 until grafoRuta1.numVertices) {
            if (grafoRuta1.obtenerMatrizDistancias()[i][j] != Double.POSITIVE_INFINITY) {
                val nombreOrigen = mapaNombres[i] ?: "Parada $i"
                val nombreDestino = mapaNombres[j] ?: "Parada $j"
                println("$nombreOrigen - $nombreDestino")
            }
        }
    }
    grafoRuta1.imprimirMatriz(grafoRuta1.obtenerMatrizCostos(), "Matriz de adyacencia de costos para la Ruta 1")
    grafoRuta1.imprimirMatriz(grafoRuta1.obtenerMatrizDistancias(), "Matriz de adyacencia de distancias para la Ruta 1")

    println("\n===================================")
    println("            Ruta 2           ")
    println("===================================")
    for (i in 0 until grafoRuta2.numVertices) {
        for (j in 0 until grafoRuta2.numVertices) {
            if (grafoRuta2.obtenerMatrizDistancias()[i][j] != Double.POSITIVE_INFINITY) {
                val nombreOrigen = mapaNombres[i] ?: "Parada $i"
                val nombreDestino = mapaNombres[j] ?: "Parada $j"
                println("$nombreOrigen - $nombreDestino")
            }
        }
    }
    grafoRuta2.imprimirMatriz(grafoRuta2.obtenerMatrizCostos(), "Matriz de adyacencia de costos para la Ruta 2")
    grafoRuta2.imprimirMatriz(grafoRuta2.obtenerMatrizDistancias(), "Matriz de adyacencia de distancias para la Ruta 2")
}
