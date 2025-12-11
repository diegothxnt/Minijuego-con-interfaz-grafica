package view;

import model.GameModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView {
    private JFrame frame;
    private GamePanel gamePanel;
    private JLabel scoreLabel, shotsLabel, accuracyLabel, angleLabel, powerLabel;
    
    public GameView() {
        createGUI();
    }
    
    private void createGUI() {
        frame = new JFrame(" Juego de Cañón - Arquitectura MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Panel de información
        JPanel infoPanel = createInfoPanel();
        
        // Panel del juego
        gamePanel = new GamePanel();
        
        // Panel de controles
        JPanel controlPanel = createControlPanel();
        
        // Agregar componentes al frame
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        
        frame.pack();
        frame.setSize(850, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        scoreLabel = createStyledLabel("Puntuación: 0", Color.BLUE);
        shotsLabel = createStyledLabel("Disparos: 0", Color.RED);
        accuracyLabel = createStyledLabel("Precisión: 0%", Color.GREEN);
        angleLabel = createStyledLabel("Ángulo: 45°", Color.ORANGE);
        powerLabel = createStyledLabel("Potencia: 15", Color.MAGENTA);
        
        panel.add(scoreLabel);
        panel.add(shotsLabel);
        panel.add(accuracyLabel);
        panel.add(angleLabel);
        panel.add(powerLabel);
        
        return panel;
    }
    
    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(color);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return label;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(220, 220, 220));
        
        // Fila 1: Controles de ángulo y potencia
        JPanel row1 = new JPanel(new GridLayout(1, 4, 10, 0));
        row1.add(createControlButton("ANGULO +", Color.ORANGE, "ANGLE_UP"));
        row1.add(createControlButton("ANGULO -", Color.ORANGE, "ANGLE_DOWN"));
        row1.add(createControlButton("POTENCIA +", Color.RED, "POWER_UP"));
        row1.add(createControlButton("POTENCIA -", Color.RED, "POWER_DOWN"));
        
        // Fila 2: Acciones del juego
        JPanel row2 = new JPanel(new GridLayout(1, 4, 10, 0));
        row2.add(createControlButton("DISPARAR", Color.GREEN, "SHOOT"));
        row2.add(createControlButton("REINICIAR", Color.BLUE, "RESTART"));
        row2.add(createControlButton("PAUSA", Color.YELLOW, "PAUSE"));
        row2.add(createControlButton("SALIR", Color.RED, "EXIT"));
        
        panel.add(row1);
        panel.add(row2);
        
        return panel;
    }
    
    private JButton createControlButton(String text, Color color, String actionCommand) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setActionCommand(actionCommand);
        return button;
    }
    
    public void update(GameModel model) {
        GameModel.Cannon cannon = model.getCannon();
        
        // Actualizar estadísticas
        scoreLabel.setText("Puntuación: " + model.getScore());
        shotsLabel.setText("Disparos: " + model.getShots());
        
        // Calcular precisión
        double accuracy = 0;
        if (model.getShots() > 0) {
            accuracy = (double) model.getHits() / model.getShots() * 100;
        }
        accuracyLabel.setText(String.format("Precisión: %.1f%%", accuracy));
        
        // Actualizar ángulo y potencia
        int angleDegrees = (int) Math.toDegrees(cannon.angle);
        angleLabel.setText("Ángulo: " + angleDegrees + "°");
        powerLabel.setText("Potencia: " + cannon.power);
        
        // Refrescar gráficos
        gamePanel.repaint();
    }
    
    public void setModel(GameModel model) {
        gamePanel.setModel(model);
    }
    
    public void addButtonListener(ActionListener listener) {
        // Agregar listener a todos los paneles
        addListenerToContainer(frame.getContentPane(), listener);
    }
    
    private void addListenerToContainer(Container container, ActionListener listener) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                ((JButton) comp).addActionListener(listener);
            } else if (comp instanceof Container) {
                addListenerToContainer((Container) comp, listener);
            }
        }
    }
    
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    public void requestFocus() {
        gamePanel.requestFocusInWindow();
    }
}