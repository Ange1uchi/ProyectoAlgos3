// filepath: /home/seijas/Desktop/Laboratorio2/Arco.kt
package mypackage
import java.io.File
import java.util.Arrays


//ARISTA
public open class Arista(val v: Int, val u: Int) : Lado(v, u) {

    // Representación en string de la arista
    override fun toString() : String {
	return """
	v: ${v}
	w: ${u}
	"""
    }

} 