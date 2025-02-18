// filepath: /home/seijas/Desktop/Laboratorio2/Lado.kt
package mypackage

//LADO
open class Lado(val v: Int, val u: Int) {

    // Retorna cualquiera de los dos vértices del grafo
    fun cualquieraDeLosVertices() : Int {
        return v
    }

    // Dado un vertice w, si w == a entonces retorna b, de lo contrario si w == b  entonces retorna a,  y si w no es igual a a ni a b, entonces se lanza una RuntimeExpception
    fun elOtroVertice(w: Int) : Int {
        if (w == v) {
            return u
        } else if (w == u) {
            return v
        } else {
            throw RuntimeException()
        }
    }
}