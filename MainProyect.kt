package mypackage

import java.io.File
import java.util.*

// Mapa de nombres de las paradas
val mapaNombres = mapOf(
    0 to "USB (sede Sartenejas)",
    1 to "McDonald's Trinidad",
    2 to "Chacaito"
)

data class GrafoDirigido(val numVertices: Int) {
    private var matrizCostos: Array<DoubleArray> = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }
    private var matrizDistancias: Array<DoubleArray> = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }

    fun agregarArista(origen: Int, destino: Int, costo: Double, distancia: Double) {
        matrizCostos[origen][destino] = costo
        matrizDistancias[origen][destino] = distancia
    }

    fun obtenerMatrizCostos(): Array<DoubleArray> = matrizCostos
    fun obtenerMatrizDistancias(): Array<DoubleArray> = matrizDistancias

    fun imprimirMatriz(matriz: Array<DoubleArray>, mensaje: String) {
        println("\n$mensaje:")
        for (i in 0 until numVertices) {
            for (j in 0 until numVertices) {
                print(if (matriz[i][j] == Double.POSITIVE_INFINITY) "∞\t" else String.format("%.2f\t", matriz[i][j]))
            }
            println()
        }
    }

    companion object {
        fun desdeArchivo(nombreArchivo: String): Pair<GrafoDirigido?, GrafoDirigido?> {
            try {
                val lineas = File(nombreArchivo).readLines()
                var rutaActual: GrafoDirigido? = null
                var grafoRuta1: GrafoDirigido? = null
                var grafoRuta2: GrafoDirigido? = null
                var leyendoRuta = false

                var i = 0
                while (i < lineas.size) {
                    val linea = lineas[i].trim()
                    if (linea.startsWith("# Ruta")) {
                        leyendoRuta = true
                        val rutaNum = linea.substringAfter("# Ruta ").toIntOrNull()
                        if (rutaNum == 1 || rutaNum == 2) {
                            i++
                            val numVertices = lineas[i].trim().toInt()
                            rutaActual = GrafoDirigido(numVertices)
                            i++
                            val numAristas = lineas[i].trim().toInt()
                            i++
                            for (j in 0 until numAristas) {
                                val datosArista = lineas[i].trim().split(" ").map { it.toDouble() }
                                val origen = datosArista[0].toInt()
                                val destino = datosArista[1].toInt()
                                val costo = datosArista[2]
                                val distancia = datosArista[3]
                                rutaActual.agregarArista(origen, destino, costo, distancia)
                                i++
                            }

                            when (rutaNum) {
                                1 -> grafoRuta1 = rutaActual
                                2 -> grafoRuta2 = rutaActual
                            }

                        } else {
                            println("Error: Número de ruta inválido: $rutaNum")
                            return Pair(null, null)
                        }
                    } else {
                        i++
                    }
                }
                return Pair(grafoRuta1, grafoRuta2)

            } catch (e: Exception) {
                println("Error al leer el archivo: ${e.message}")
                return Pair(null, null)
            }
        }
    }
}


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: Debes proporcionar el nombre del archivo de entrada.")
        return
    }

    val (grafoRuta1, grafoRuta2) = GrafoDirigido.desdeArchivo(args[0])

    if (grafoRuta1 == null || grafoRuta2 == null) {
        println("Error: No se pudieron cargar las rutas correctamente.")
        return
    }

    // Imprimir matrices de Ruta 1
    grafoRuta1.imprimirMatriz(grafoRuta1.obtenerMatrizCostos(), "Matriz de adyacencia de costos para la Ruta 1")
    grafoRuta1.imprimirMatriz(grafoRuta1.obtenerMatrizDistancias(), "Matriz de adyacencia de distancias para la Ruta 1")

    // Calcular el costo y la distancia total para la Ruta 1 (0 -> 1 -> 0)
    var costoTotalRuta1 = 0.0
    var distanciaTotalRuta1 = 0.0
    val rutaRuta1 = "0 -> 1 -> 0"
    val rutaNombresRuta1 = "USB (sede Sartenejas) -> McDonald's Trinidad -> USB (sede Sartenejas)"

    if (grafoRuta1.obtenerMatrizCostos()[0][1] != Double.POSITIVE_INFINITY && grafoRuta1.obtenerMatrizCostos()[1][0] != Double.POSITIVE_INFINITY) {
        costoTotalRuta1 = grafoRuta1.obtenerMatrizCostos()[0][1] + grafoRuta1.obtenerMatrizCostos()[1][0]
        distanciaTotalRuta1 = grafoRuta1.obtenerMatrizDistancias()[0][1] + grafoRuta1.obtenerMatrizDistancias()[1][0]
        println("\nRuta 1 Optima: $rutaNombresRuta1 Costo Total: $costoTotalRuta1 lts de gasoil, Distancia Recorrida: $distanciaTotalRuta1 km")
    } else {
        println("\nRuta 1: No existe ruta completa $rutaRuta1")
    }

    // Imprimir matrices de Ruta 2
    grafoRuta2.imprimirMatriz(grafoRuta2.obtenerMatrizCostos(), "Matriz de adyacencia de costos para la Ruta 2")
    grafoRuta2.imprimirMatriz(grafoRuta2.obtenerMatrizDistancias(), "Matriz de adyacencia de distancias para la Ruta 2")

    // Calcular el costo y la distancia total para la Ruta 2 (0 -> 2 -> 1 -> 0)
    var costoTotalRuta2 = 0.0
    var distanciaTotalRuta2 = 0.0
    val rutaRuta2 = "0 -> 2 -> 1 -> 0"
    val rutaNombresRuta2 = "USB (sede Sartenejas) -> Chacaito -> McDonald's Trinidad -> USB (sede Sartenejas)"

    if (grafoRuta2.obtenerMatrizCostos()[0][2] != Double.POSITIVE_INFINITY && grafoRuta2.obtenerMatrizCostos()[2][1] != Double.POSITIVE_INFINITY && grafoRuta2.obtenerMatrizCostos()[1][0] != Double.POSITIVE_INFINITY) {
        costoTotalRuta2 = grafoRuta2.obtenerMatrizCostos()[0][2] + grafoRuta2.obtenerMatrizCostos()[2][1] + grafoRuta2.obtenerMatrizCostos()[1][0]
        distanciaTotalRuta2 = grafoRuta2.obtenerMatrizDistancias()[0][2] + grafoRuta2.obtenerMatrizDistancias()[2][1] + grafoRuta2.obtenerMatrizDistancias()[1][0]
        println("\nRuta 2 Optima: $rutaNombresRuta2 Costo Total: $costoTotalRuta2 lts de gasoil, Distancia Recorrida: $distanciaTotalRuta2 km")
    } else {
        println("Ruta 2: No existe ruta completa $rutaRuta2")
    }
}