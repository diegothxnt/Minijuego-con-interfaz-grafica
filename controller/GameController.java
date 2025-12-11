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
    
    