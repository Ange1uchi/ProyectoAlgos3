package mypackage

// ARISTACOSTO: Clase que representa una arista con distancia y consumo de gasoil
class AristaCosto(
    x: Int,
    y: Int,
    val distancia: Double, // Nueva variable para la distancia en km
    val consumo: Double,  //Nueva variable para el consumo de gasoil en litros
) : Comparable<AristaCosto>, Arista(x, y) {

    // Representación en string de la arista
    override fun toString(): String {
        return """
        v: $u
        w: $v
        Distancia: $distancia km
        Consumo: $consumo L
        """
    }

    /*
     * Se compara dos aristas con respecto a su distancia por defecto.
     * - Si this.distancia > other.distancia retorna 1
     * - Si this.distancia < other.distancia retorna -1
     * - Si son iguales, puede compararse por consumo como criterio secundario
     */
    override fun compareTo(other: AristaCosto): Int {
        return when {
            distancia > other.distancia -> 1
            distancia < other.distancia -> -1
            else -> consumo.compareTo(other.consumo) // Si las distancias son iguales, comparar por consumo
        }
    }
}
