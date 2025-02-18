# Variables
KOTLINC=kotlinc
JAVA=java
SRC_DIR=mypackage
BUILD_DIR=build
JAR=libGrafoKt.jar
MAIN=MainProyect.kt
OUTPUT=MainProyect.jar

# Nombres de los archivos de entrada
INPUT1=ruta1.txt
INPUT2=ruta2.txt

# Regla principal
all: clean lib compile

# Compilar la librería `libGrafoKt.jar`
lib:
	@echo "Compilando la libreria..."
	if not exist $(BUILD_DIR) mkdir $(BUILD_DIR)
	$(KOTLINC) $(SRC_DIR)/*.kt -d $(BUILD_DIR)
	jar -cvf $(JAR) -C $(BUILD_DIR) .

# Compilar `MainProyect.kt` usando la librería
compile: lib
	@echo "Compilando el programa principal..."
	$(KOTLINC) -cp $(JAR) $(MAIN) -include-runtime -d $(OUTPUT)

# Ejecutar el programa con dos archivos de entrada
run:
	@echo "Ejecutando el programa..."
	$(JAVA) -cp ".;$(JAR);$(OUTPUT)" mypackage.MainProyectKt $(INPUT1) $(INPUT2)

# Limpiar archivos generados
clean:
	@echo "Limpiando archivos generados..."
	if exist $(OUTPUT) del /F /Q $(OUTPUT)
	if exist $(JAR) del /F /Q $(JAR)
	if exist $(BUILD_DIR) rmdir /S /Q $(BUILD_DIR)
