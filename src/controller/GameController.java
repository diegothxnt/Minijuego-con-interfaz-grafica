package controller;

import model.GameModel;
import view.GameView;
import javax.swing.*;
import java.awt.event.*;

public class GameController {
    private GameModel model;
    private GameView view;
    private Timer gameTimer;
    private boolean gamePaused = false;
    
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        
        // configuración inicial
        initialize();
    }
    
    private void initialize() {
        // 1. Vincular modelo y vista   
        view.setModel(model);
        
        // 2.  Configurar controles de teclado
        setupKeyboardControls();
        
        // 3. Configurar controles de botones
        setupButtonControls();
        
        // 4. Iniciar el juego
        startGameLoop();
        
        // 5. Mostrar instrucciones
        showInstructions();
        
        // 6. Dar foco al panel del juego
        view.requestFocus();
    }
    private void setupKeyboardControls() {
       
        view.getGamePanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
    }
    
    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                model.cannonUp();
                break;
                
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                model.cannonDown();
                break;
                
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                model.powerUp();
                break;
                
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                model.powerDown();
                break;
                
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                model.shoot();
                break;
                
            case KeyEvent.VK_R:
                model.restartGame();
                break;
                
            case KeyEvent.VK_P:
                togglePause();
                break;
                
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
        
        // Actualizar la vista
        view.update(model);
    }
    
    private void setupButtonControls() {
        view.addButtonListener(e -> {
            String command = e.getActionCommand();
            
            switch (command) {
                case "ANGLE_UP":
                    model.cannonUp();
                    break;
                    
                case "ANGLE_DOWN":
                    model.cannonDown();
                    break;
                    
                case "POWER_UP":
                    model.powerUp();
                    break;
                    
                case "POWER_DOWN":
                    model.powerDown();
                    break;
                    
                case "SHOOT":
                    model.shoot();
                    break;
                    
                case "RESTART":
                    model.restartGame();
                    break;
                    
                case "PAUSE":
                    togglePause();
                    break;
                    
                case "EXIT":
                    System.exit(0);
                    break;
            }
            
            view.update(model);
        });
    }
    
    private void startGameLoop() {
        gameTimer = new Timer(16, e -> { // ≈ 60 FPS
            if (!gamePaused) {
                model.update();
                view.update(model);
            }
        });
        gameTimer.start();
    }
    
    private void togglePause() {
        gamePaused = !gamePaused;
        if (gamePaused) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(view.getFrame(), 
                "Juego en pausa\nPresiona P para continuar",
                "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            gameTimer.start();
        }
    }
    
    private void showInstructions() {
        SwingUtilities.invokeLater(() -> {
            String message = """
                 JUEGO DE CAÑÓN - ARQUITECTURA MVC 
                
                 CONTROLES:
                
                TECLADO:
                • Flechas ↑↓ o W/S: Ajustar ángulo
                • Flechas ←→ o A/D: Ajustar potencia
                • ESPACIO o ENTER: Disparar
                • R: Reiniciar juego
                • P: Pausar/Continuar
                • ESC: Salir del juego
                
                BOTONES:
                • Usa los botones en la parte inferior
                
                 OBJETIVO:
                • Dispara a los objetivos voladores
                • Rojo = 30 puntos
                • Azul = 20 puntos
                • Verde = 10 puntos
                • Mezcla = 15 puntos
                
                ¡DIVIÉRTETE Y CONSIGUE LA MAYOR PUNTUACIÓN!
                """;
                
            JOptionPane.showMessageDialog(view.getFrame(), message,
                "Instrucciones del Juego", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}

    
