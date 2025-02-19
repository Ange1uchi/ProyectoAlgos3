package mypackage

import java.io.File

class GrafoDirigido(val numVertices: Int) {
    private val matrizCostos = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }
    private val matrizDistancias = Array(numVertices) { DoubleArray(numVertices) { Double.POSITIVE_INFINITY } }

    fun agregarArista(origen: Int, destino: Int, distancia: Double, costo: Double) {
        matrizCostos[origen][destino] = costo
        matrizDistancias[origen][destino] = distancia
    }

    fun obtenerMatrizCostos(): Array<DoubleArray> = matrizCostos
    fun obtenerMatrizDistancias(): Array<DoubleArray> = matrizDistancias

    companion object {
        fun desdeArchivo(nombreArchivo: String): Triple<Map<String, Int>, GrafoDirigido?, GrafoDirigido?> {
            try {
                val lineas = File(nombreArchivo).readLines()
                val mapaNombres = mutableMapOf<String, Int>()
                var grafoRuta1: GrafoDirigido? = null
                var grafoRuta2: GrafoDirigido? = null
                var leyendoRuta = false
                var rutaActual: GrafoDirigido? = null
    
                var i = 0
                while (i < lineas.size) {
                    val linea = lineas[i].trim()
                    if (linea.startsWith("# Ruta")) {
                        val rutaNum = linea.substringAfter("# Ruta ").toIntOrNull()
                        if (rutaNum == 1 || rutaNum == 2) {
                            i++
                            val numVertices = lineas[i].trim().toInt()
                            rutaActual = GrafoDirigido(numVertices)
                            i++
                            val numAristas = lineas[i].trim().toInt()
                            i++
                            for (j in 0 until numAristas) {
                                val datosArista = lineas[i].trim().split(" ")
                                val origen = datosArista[0]
                                val destino = datosArista[1]
                                val costo = datosArista[2].toDouble()
                                val distancia = datosArista[3].toDouble()
    
                                // Mapear nombres a índices numéricos
                                if (!mapaNombres.containsKey(origen)) {
                                    mapaNombres[origen] = mapaNombres.size
                                }
                                if (!mapaNombres.containsKey(destino)) {
                                    mapaNombres[destino] = mapaNombres.size
                                }
    
                                val origenIdx = mapaNombres[origen]!!
                                val destinoIdx = mapaNombres[destino]!!
                                rutaActual.agregarArista(origenIdx, destinoIdx, costo, distancia)
    
                                i++
                            }
    
                            when (rutaNum) {
                                1 -> grafoRuta1 = rutaActual
                                2 -> grafoRuta2 = rutaActual
                            }
                        } else {
                            println("Error: Número de ruta inválido: $rutaNum")
                            return Triple(emptyMap(), null, null)
                        }
                    } else {
                        i++
                    }
                }
                return Triple(mapaNombres, grafoRuta1, grafoRuta2)
    
            } catch (e: Exception) {
                println("Error al leer el archivo: ${e.message}")
                return Triple(emptyMap(), null, null)
            }
        }
    }
    
}
