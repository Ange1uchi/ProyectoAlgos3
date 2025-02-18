package mypackage

import java.io.File
import mypackage.AristaCosto

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

    val filePath = args[0]
    val file = File(filePath)

    if (!file.exists()) {
        println("Error: El archivo '$filePath' no existe.")
        return
    }

    val lines = file.readLines()
    if (lines.size < 2) {
        println("Error: El archivo debe contener al menos dos líneas (número de nodos y aristas).")
        return
    }

    try { 
        val numNodos = lines[0].trim().toInt()
        val numAristas = lines[1].trim().toInt()
        val aristas = mutableListOf<AristaCosto>()

        // Leer las aristas con valores de tipo Double
        for (i in 2 until 2 + numAristas) {
            val datos = lines[i].split(" ").map { it.toDoubleOrNull() }
            if (datos.size != 4 || datos.any { it == null }) {
                println("Advertencia: Línea inválida en el archivo: '${lines[i]}'. Se ignorará.")
                continue
            }

            val (origen, destino, distancia, consumo) = datos.filterNotNull()
            aristas.add(AristaCosto(origen.toInt(), destino.toInt(), distancia, consumo))
        }

        // Agrupar recorridos por rutas
        val rutas = mutableMapOf<Int, MutableList<AristaCosto>>()

        for ((index, arista) in aristas.withIndex()) {
            rutas.getOrPut(index) { mutableListOf() }.add(arista)
        }

        // Imprimir las rutas con recorridos
        println("\nLista de Rutas:")
        for ((rutaId, recorridos) in rutas) {
            println("\nRuta ${rutaId + 1}:")
            for ((recorridoId, arista) in recorridos.withIndex()) {
                val nombreOrigen = mapaNombres[arista.v] ?: "Parada ${arista.v}"
                val nombreDestino = mapaNombres[arista.u] ?: "Parada ${arista.u}"
                println(
                    "  Recorrido $recorridoId: $nombreOrigen - $nombreDestino " +
                            "(Distancia: %.2f km, Consumo: %.2f L)".format(arista.distancia, arista.consumo)
                )
            }
        }

    } catch (e: Exception) {
        println("Error: Ocurrió un problema al leer el archivo. Verifica el formato.")
    }
}
