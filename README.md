# Minijuego-con-interfaz-grafica
📋 Descripción
Juego interactivo donde controlas un cañón para disparar a objetivos en movimiento. Implementado con Java Swing y arquitectura MVC.


# Estructura de este proyecto
CannonGame/
├── src/
│   ├── Main.java                    # Punto de entrada

│   ├── model/GameModel.java         # Lógica del juego

│   ├── view/GamePanel.java          # Panel gráfico

│   ├── view/GameView.java           # Interfaz principal

│   └── controller/GameController.java # Controlador

├── bin/                            # Archivos compilados

└── README.md                       # Este archivo


🚀 Cómo Ejecutar
Compilación:
bash


# Compilar todo
javac -d bin src/model/GameModel.java src/view/GamePanel.java src/view/GameView.java src/controller/GameController.java src/Main.java



# Ejecutar
java -cp bin Main
