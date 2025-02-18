import java.io.File

// PRUEBA DE ENTRADA DE DATOS
data class Arista(val origen: Int, val destino: Int, val distancia: Int, val consumo: Int)

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
        // Leer la cantidad de nodos y aristas
        val numNodos = lines[0].trim().toInt()
        val numAristas = lines[1].trim().toInt()

        val aristas = mutableListOf<Arista>()

        // Leer las aristas
        for (i in 2 until 2 + numAristas) {
            val datos = lines[i].split(" ").map { it.toIntOrNull() }
            if (datos.size != 4 || datos.any { it == null }) {
                println("Advertencia: Línea inválida en el archivo: '${lines[i]}'. Se ignorará.")
                continue
            }

            val (origen, destino, distancia, consumo) = datos.filterNotNull()
            aristas.add(Arista(origen, destino, distancia, consumo))
        }

        // Imprimir las aristas
        println("\nLista de Aristas:")
        for (arista in aristas) {
            println("HAY UNA ARISTA DE ${arista.origen} A ${arista.destino} CON DISTANCIA ${arista.distancia} Y CONSUMO ${arista.consumo}")
        }

    } catch (e: Exception) {
        println("Error: Ocurrió un problema al leer el archivo. Verifica el formato.")
    }
}
