# Definir variables
KOTLINC = kotlinc
JAR_NAME = Main.jar
MAIN_FILE = Main.kt
INPUT_FILE = rutaOptUSB.txt

# Compilar el código
all: compile

compile:
	@echo "Compilando el programa..."
	$(KOTLINC) $(MAIN_FILE) -include-runtime -d $(JAR_NAME)
	@echo "Compilacion finalizada."

# Ejecutar el programa
run:
	@echo "Ejecutando el programa con la entrada: $(INPUT_FILE)"
	java -cp $(JAR_NAME) MainKt $(INPUT_FILE)

# Limpiar archivos generados
clean:
	@echo "Eliminando archivos generados..."
	@if exist $(JAR_NAME) del /F /Q $(JAR_NAME)
