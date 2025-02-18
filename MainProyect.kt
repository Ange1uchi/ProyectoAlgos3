package mypackage

import java.io.File

// Mapa de nombres de las paradas
val mapaNombres = mapOf(
    0 to "USB (sede Sartenejas)",
    1 to "McDonald's Trinidad",
    2 to "Chacaito"
)

// Función para procesar un archivo de entrada y obtener las aristas
fun procesarArchivo(filePath: String, nombreRuta: String): Pair<List<Arista>, Int> {
    val file = File(filePath)

    if (!file.exists()) {
        println("Error: El archivo '$filePath' no existe.")
        return Pair(emptyList(), 0)
    }

    val lines = file.readLines()
    if (lines.size < 2) {
        println("Error: El archivo debe contener al menos dos líneas (número de nodos y aristas).")
        return Pair(emptyList(), 0)
    }

    return try {
        val numNodos = lines[0].trim().toInt()
        val numAristas = lines[1].trim().toInt()
        val aristas = mutableListOf<Arista>()

        // Leer las aristas con valores de tipo Double
        for (i in 2 until 2 + numAristas) {
            val datos = lines[i].split(" ").map { it.toDoubleOrNull() }
            if (datos.size != 4 || datos.any { it == null }) {
                println("Advertencia: Línea inválida en el archivo: '${lines[i]}'. Se ignorará.")
                continue
            }

            val (origen, destino, distancia, consumo) = datos.filterNotNull()
            aristas.add(Arista(origen.toInt(), destino.toInt(), distancia, consumo))
        }

        Pair(aristas, numNodos)

    } catch (e: Exception) {
        println("Error: Ocurrió un problema al leer el archivo. Verifica el formato.")
        Pair(emptyList(), 0)
    }
}

// Función para construir matrices de adyacencia
fun construirMatrizAdyacencia(aristas: List<Arista>, numNodos: Int): Pair<Array<DoubleArray>, Array<DoubleArray>> {
    val matrizCostos = Array(numNodos) { DoubleArray(numNodos) { Double.POSITIVE_INFINITY } }
    val matrizDistancias = Array(numNodos) { DoubleArray(numNodos) { Double.POSITIVE_INFINITY } }

    for (arista in aristas) {
        matrizCostos[arista.v][arista.u] = arista.consumo
        matrizDistancias[arista.v][arista.u] = arista.distancia
    }

    return Pair(matrizCostos, matrizDistancias)
}

// Función para imprimir una matriz de adyacencia
fun imprimirMatriz(matriz: Array<DoubleArray>, nombre: String) {
    println("\n==============================")
    println(" $nombre ")

    print(" ") // Espacio para la esquina superior izquierda
    for (i in matriz.indices) {
        print("%6d".format(i)) // Imprime los índices de los nodos en la parte superior
    }
    println()

    for (i in matriz.indices) {
        print("%2d ".format(i)) // Índice de fila
        for (j in matriz[i].indices) {
            if (matriz[i][j] == Double.POSITIVE_INFINITY) {
                print("   0  ") // Imprime infinito en lugar de valores vacíos
            } else {
                print("%6.2f".format(matriz[i][j])) // Alineación con 2 decimales
            }
        }
        println()
    }
}


fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Error: Debes proporcionar los nombres de los dos archivos de entrada.")
        return
    }

    val (aristasRuta1, numNodosRuta1) = procesarArchivo(args[0], "Ruta 1")
    val (aristasRuta2, numNodosRuta2) = procesarArchivo(args[1], "Ruta 2")

    // Imprimir recorridos
    println("\nLista de Rutas:")

    println("\nRuta 1:")
    for (arista in aristasRuta1) {
        val nombreOrigen = mapaNombres[arista.v] ?: "Parada ${arista.v}"
        val nombreDestino = mapaNombres[arista.u] ?: "Parada ${arista.u}"
        println("  $nombreOrigen - $nombreDestino (Distancia: %.2f km, Consumo: %.2f L)".format(arista.distancia, arista.consumo))
    }

    println("\nRuta 2:")
    for (arista in aristasRuta2) {
        val nombreOrigen = mapaNombres[arista.v] ?: "Parada ${arista.v}"
        val nombreDestino = mapaNombres[arista.u] ?: "Parada ${arista.u}"
        println("  $nombreOrigen - $nombreDestino (Distancia: %.2f km, Consumo: %.2f L)".format(arista.distancia, arista.consumo))
    }

    // Construir matrices de adyacencia
    val (matrizCostosRuta1, matrizDistanciasRuta1) = construirMatrizAdyacencia(aristasRuta1, numNodosRuta1)
    val (matrizCostosRuta2, matrizDistanciasRuta2) = construirMatrizAdyacencia(aristasRuta2, numNodosRuta2)

    // Imprimir matrices de adyacencia
    imprimirMatriz(matrizCostosRuta1, "Matriz de costos (Litros) para la Ruta 1")
    imprimirMatriz(matrizDistanciasRuta1, "Matriz de distancias (Kilometros) para la Ruta 1")
    imprimirMatriz(matrizCostosRuta2, "Matriz de costos (Litros) para la Ruta 2")
    imprimirMatriz(matrizDistanciasRuta2, "Matriz de distancias (Kilometros) para la Ruta 2")

}
