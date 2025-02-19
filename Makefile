# Variables
KOTLINC=kotlinc
JAVA=java
SRC_DIR=mypackage
BUILD_DIR=build
JAR=libGrafoKt.jar
MAIN=MainProyect.kt
OUTPUT=MainProyect.jar

# Nombres de los archivos de entrada
INPUT1=RutaOptUSB.txt
# INPUT2=ruta2.txt

# Regla principal
all: clean lib compile

# Compilar la librería `libGrafoKt.jar`
lib:
	@echo "Compilando la librería..."
	mkdir -p $(BUILD_DIR)
	$(KOTLINC) $(SRC_DIR)/*.kt -d $(BUILD_DIR)
	jar -cvf $(JAR) -C $(BUILD_DIR) .

# Compilar `MainProyect.kt` usando la librería
compile: lib
	@echo "Compilando el programa principal..."
	$(KOTLINC) -cp $(JAR) $(MAIN) -include-runtime -d $(OUTPUT)

# Ejecutar el programa con dos archivos de entrada
run:
	@echo "Ejecutando el programa..."
	$(JAVA) -cp ".:$(JAR):$(OUTPUT)" mypackage.MainProyectKt $(INPUT1)

# Limpiar archivos generados
clean:
	@echo "Limpiando archivos generados..."
	rm -f $(OUTPUT)
	rm -f $(JAR)
	rm -rf $(BUILD_DIR)