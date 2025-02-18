package mypackage

import java.io.File

// Clase que representa un Grafo Dirigido con costos y distancias
class GrafoDirigido(val numVertices: Int) {
    private val matrizCostos = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }
    private val matrizDistancias = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }

    // Agrega una arista dirigida con costo y distancia
    fun agregarArista(origen: Int, destino: Int, distancia: Double, consumo: Double) {
        matrizCostos[origen][destino] = consumo
        matrizDistancias[origen][destino] = distancia
    }

    // Obtiene la matriz de costos (gasoil)
    fun obtenerMatrizCostos(): Array<DoubleArray> = matrizCostos

    // Obtiene la matriz de distancias
    fun obtenerMatrizDistancias(): Array<DoubleArray> = matrizDistancias
    companion object {
        fun desdeArchivo(filePath: String): Pair<GrafoDirigido?, GrafoDirigido?> {
            val file = File(filePath)
            if (!file.exists()) {
                println("Error: El archivo '$filePath' no existe.")
                return Pair(null, null)
            }
    
            val lines = file.readLines().filter { it.isNotBlank() && !it.startsWith("#") }
            if (lines.size < 4) {
                println("Error: El archivo debe contener suficientes líneas para ambas rutas.")
                return Pair(null, null)
            }
    
            return try {
                // Leer datos de la Ruta 1
                val numNodos1 = lines[0].trim().toInt()
                val numAristas1 = lines[1].trim().toInt()
                val grafo1 = GrafoDirigido(numNodos1)
    
                for (i in 2 until 2 + numAristas1) {
                    val datos = lines[i].split(" ").map { it.toDoubleOrNull() }
                    if (datos.size == 4 && datos.all { it != null }) {
                        val (origen, destino, distancia, consumo) = datos.filterNotNull()
                        grafo1.agregarArista(origen.toInt(), destino.toInt(), distancia, consumo)
                    }
                }
    
                // Leer datos de la Ruta 2
                val indexRuta2 = 2 + numAristas1
                val numNodos2 = lines[indexRuta2].trim().toInt()
                val numAristas2 = lines[indexRuta2 + 1].trim().toInt()
                val grafo2 = GrafoDirigido(numNodos2)
    
                for (i in indexRuta2 + 2 until indexRuta2 + 2 + numAristas2) {
                    val datos = lines[i].split(" ").map { it.toDoubleOrNull() }
                    if (datos.size == 4 && datos.all { it != null }) {
                        val (origen, destino, distancia, consumo) = datos.filterNotNull()
                        grafo2.agregarArista(origen.toInt(), destino.toInt(), distancia, consumo)
                    }
                }
    
                Pair(grafo1, grafo2)
            } catch (e: Exception) {
                println("Error: Ocurrió un problema al leer el archivo. Verifica el formato.")
                Pair(null, null)
            }
        }
    }
    

    // Imprimir la matriz en un formato legible
    fun imprimirMatriz(matriz: Array<DoubleArray>, nombre: String) {
        println("\n==============================")
        println(" $nombre ")

        print(" ") // Espacio para la esquina superior izquierda
        for (i in matriz.indices) {
            print("%6d".format(i)) // Índices superiores
        }
        println()

        for (i in matriz.indices) {
            print("%2d ".format(i)) // Índice de fila
            for (j in matriz[i].indices) {
                if (matriz[i][j] == Double.POSITIVE_INFINITY) {
                    print("   0  ") // Imprime infinito si no hay conexión
                } else {
                    print("%6.2f".format(matriz[i][j])) // Alinea los valores
                }
            }
            println()
        }
    }
}
