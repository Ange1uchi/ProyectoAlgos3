package mypackage

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: Debes proporcionar el nombre del archivo de entrada.")
        return
    }

    // Cargar el archivo y obtener los grafos con el mapa de nombres
    val (mapaNombres, grafoRuta1, grafoRuta2) = GrafoDirigido.desdeArchivo(args[0])

    if (grafoRuta1 == null || grafoRuta2 == null) {
        println("Error: No se pudieron cargar las rutas correctamente.")
        return
    }

    //   MATRICES DE ADYACENCIA
    // Ruta 1: Imprimir matrices
    println("\n============= Ruta 1 =============")
    println("USB -> LA TRINIDAD")
    imprimirMatriz(grafoRuta1.obtenerMatrizCostos(), "Costos para Ruta 1 (Litros de gasoil)")
    imprimirMatriz(grafoRuta1.obtenerMatrizDistancias(), "Distancias para Ruta 1 (Kilómetros)")

    // Ruta 2: Imprimir matrices
    println("\n============= Ruta 2 =============")
    println("USB -> CHACAITO")
    println("CHACAITO  ->  LA TRINIDAD  ->  USB")
    println("LA TRINIDAD  ->  USB")
    println("USB -> LA TRINIDAD")
    imprimirMatriz(grafoRuta2.obtenerMatrizCostos(), "Costos para Ruta 2 (Litros de gasoil)")
    imprimirMatriz(grafoRuta2.obtenerMatrizDistancias(), "Distancias para Ruta 2 (Kilómetros)")



    //   ANÁLISIS DE RUTAS
    println("\n===================================")
    println("     Comparación de rutas (BFS)      ")
    println("===================================\n")

    // Encontrar mejor ruta en Ruta 1 (único camino posible)
    println("\n- Análisis de la Ruta 1:")
    val comparadorRuta1 = ComparadorDeRutas(grafoRuta1, mapaNombres)
    comparadorRuta1.encontrarMejorRutaBFS()

    // Encontrar mejor ruta en Ruta 2 (comparación de opciones)
    println("\n- Análisis de la Ruta 2:")
    val comparadorRuta2 = ComparadorDeRutas(grafoRuta2, mapaNombres)
    comparadorRuta2.encontrarMejorRutaBFS()
}

fun imprimirMatriz(matriz: Array<DoubleArray>, titulo: String) {
    println("\n====================================")
    println(" $titulo ")
    println("=====================================\n")

    // Imprimir encabezados
    print("   ")
    for (i in matriz.indices) {
        print("%6d".format(i))
    }
    println()

    for (i in matriz.indices) {
        print("%3d  ".format(i)) // Índice de la fila
        for (j in matriz[i].indices) {
            if (matriz[i][j] == Double.POSITIVE_INFINITY) {
                print("   x  ") // Representar infinito como "x"
            } else {
                print("%6.2f".format(matriz[i][j]))
            }
        }
        println()
    }
}
