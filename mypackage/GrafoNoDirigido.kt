package mypackage

import java.io.File

// GRAFOS_NO_DIRIGIDOS
public class GrafoNoDirigido : Grafo {
    var numeroDeLados = 0
    var numDeVertices = 0
    var adj = ArrayList<ArrayList<Arista>>()
    val aristas = mutableListOf<Arista>()

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        this.numDeVertices = numDeVertices
        for (i in 0 until numDeVertices) {
            adj.add(ArrayList())
        }
    }

    // Se construye un grafo a partir de un archivo
    constructor(nombreArchivo: String) {
        var cont = 0
        var E = 0
        File(nombreArchivo).forEachLine {
            if (cont == 0) {
                numDeVertices = it.toInt()
                println("Numero de vertices $numDeVertices")
                cont++
                for (i in 0 until numDeVertices) {
                    adj.add(ArrayList())
                }
            } else if (cont == 1) {
                println("Numero de lados $it")
                E = it.toInt()
                cont++
            } else {
                if (E > (cont - 2)) {
                    val tok = it.split(" ")
                    if (tok.size == 3) {
                        // Caso con peso
                        agregarArista(AristaCosto(tok[0].toInt(), tok[1].toInt(), tok[2].toDouble(), tok[3].toDouble()))
                    } else if (tok.size == 2) {
                        // Caso sin peso
                        agregarArista(Arista(tok[0].toInt(), tok[1].toInt()))
                    } else {
                        println("ADVERTENCIA: Línea inválida en el archivo: '$it'. Se ignorará.")
                    }
                    cont++
                }
            }
        }
    }

    // Agrega un lado al grafo no dirigido
    fun agregarArista(a: Arista) {
        adj[a.v].add(a)
        adj[a.u].add(a)
        aristas.add(a)
        numeroDeLados++
    }

    fun agregarArista(a: AristaCosto) {
        adj[a.v].add(a)
        adj[a.u].add(a)
        aristas.add(a)
        numeroDeLados++
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados(): Int {
        return numeroDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices(): Int {
        return numDeVertices
    }

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int): Iterable<Arista> {
        return adj[v]
    }

    // Retorna todos los lados del grafo no dirigido
    override operator fun iterator(): Iterator<Arista> {
        return aristas.iterator()
    }

    // Grado del grafo
    override fun grado(v: Int): Int {
        return adj[v].size
    }

    // Retorna un string con una representación del grafo, en donde se muestra todo su contenido
    override fun toString(): String {
        return """
        V: $numDeVertices
        E: $numeroDeLados
        """
    }
}

fun GrafoNoDirigido.toAdjacencyMap(): Map<Int, List<Int>> {
    val adjacencyMap = mutableMapOf<Int, MutableList<Int>>()
    for (arista in this.aristas) {
        adjacencyMap.getOrPut(arista.v) { mutableListOf() }.add(arista.u)
        adjacencyMap.getOrPut(arista.u) { mutableListOf() }.add(arista.v)
    }
    return adjacencyMap
}