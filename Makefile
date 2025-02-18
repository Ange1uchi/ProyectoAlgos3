# Variables
KOTLINC=kotlinc
JAVA=java
SRC_DIR=mypackage
BUILD_DIR=build
JAR=libGrafoKt.jar
MAIN=MainProyect.kt
OUTPUT=MainProyect.jar

# Regla principal
all: clean lib compile

# Compilar la librería `libGrafoKt.jar`
lib:
	@echo Compilando la libreria...
	if not exist $(BUILD_DIR) mkdir $(BUILD_DIR)
	$(KOTLINC) $(SRC_DIR)/*.kt -d $(BUILD_DIR)
	jar -cvf $(JAR) -C $(BUILD_DIR) .

# Compilar `MainProyect.kt` usando la librería
compile: lib
	@echo Compilando el programa principal...
	$(KOTLINC) -cp $(JAR) $(MAIN) -include-runtime -d $(OUTPUT)

# Ejecutar el programa
run:
	@echo "Ejecutando el programa..."
	java -cp ".;libGrafoKt.jar;MainProyect.jar" mypackage.MainProyectKt rutaOPTUSB.txt

# Limpiar archivos generados
clean:
	@echo Limpiando archivos generados...
	if exist $(OUTPUT) del /F /Q $(OUTPUT)
	if exist $(JAR) del /F /Q $(JAR)
	if exist $(BUILD_DIR) rmdir /S /Q $(BUILD_DIR)
