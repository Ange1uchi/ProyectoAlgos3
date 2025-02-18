// filepath: /home/seijas/Desktop/Laboratorio2/Arco.kt
package mypackage
import java.io.File
import java.util.Arrays


public open class Arco2(val inicio: Int, val fin: Int) : Lado(inicio, fin) {
    // Retorna el vértice inicial del arco
     fun fuente() : Int {
         return inicio
     }
    
     // Retorna el vértice final del arco
     fun sumidero() : Int {
         return fin
     }
    
     // Representación del arco
     override fun toString() : String {
         return """
         inicio: ${inicio}
         fin: ${fin}
         """
      }
    
    }
//ARCOCOSTO
public class ArcoCosto(val x: Int, val y: Int, val costo: Double) : Arco(x, y) {

    // Retorna el peso o costo asociado del arco
    fun costo() : Double {
	return costo
    }

    // Representación del arco
    override fun toString() : String {
	return """
	inicio: ${x}
	fin: ${y}
	peso: ${costo}
	"""
     }
} 
