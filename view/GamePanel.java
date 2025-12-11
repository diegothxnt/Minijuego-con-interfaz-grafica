package view;

import model.GameModel;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameModel model;
    
    public GamePanel() {
        setBackground(new Color(135, 206, 235));
        setPreferredSize(new Dimension(800, 550));
        setFocusable(true);
    }
    
    public void setModel(GameModel model) {
        this.model = model;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw sky with gradient
        GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 250),
            0, getHeight()/2, Color.WHITE
        );
        g2d.setPaint(skyGradient);
        g2d.fillRect(0, 0, getWidth(), getHeight()/2);
        
        // Draw ground
        g2d.setColor(new Color(120, 80, 40));
        g2d.fillRect(0, 450, getWidth(), 100);
        
        // Draw grass
        g2d.setColor(new Color(100, 180, 100));
        for (int i = 0; i < getWidth(); i += 15) {
            g2d.drawLine(i, 450, i, 440);
        }
        
        // Draw cannon
        GameModel.Cannon cannon = model.getCannon();
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(cannon.x, cannon.y, cannon.width, cannon.height);
        
        // Draw cannon barrel
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int barrelEndX = cannon.x + cannon.width + (int)(Math.cos(cannon.angle) * 50);
        int barrelEndY = cannon.y + cannon.height/2 - (int)(Math.sin(cannon.angle) * 50);
        g2d.drawLine(cannon.x + cannon.width, cannon.y + cannon.height/2, barrelEndX, barrelEndY);
        
        // Draw wheels
        g2d.setColor(Color.GRAY);
        g2d.fillOval(cannon.x - 10, cannon.y + cannon.height - 15, 20, 20);
        g2d.fillOval(cannon.x + cannon.width - 10, cannon.y + cannon.height - 15, 20, 20);
        
        // Draw projectiles
        for (GameModel.Projectile p : model.getProjectiles()) {
            if (p.active) {
                // Glow effect
                g2d.setColor(new Color(255, 200, 0, 100));
                g2d.fillOval(p.x - 8, p.y - 8, 16, 16);
                
                // Projectile
                g2d.setColor(Color.ORANGE);
                g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
                
                // Highlight
                g2d.setColor(Color.YELLOW);
                g2d.fillOval(p.x - 2, p.y - 2, 4, 4);
            }
        }
        
        // Draw targets
        for (GameModel.Target t : model.getTargets()) {
            // Shadow
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillOval(t.x + 3, t.y + 3, t.width, t.height);
            
            // Target
            g2d.setColor(t.color);
            g2d.fillOval(t.x, t.y, t.width, t.height);
            
            // Rings
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(t.x + 5, t.y + 5, t.width - 10, t.height - 10);
            
            // Points value
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            String points = String.valueOf(t.points);
            int textWidth = g2d.getFontMetrics().stringWidth(points);
            g2d.drawString(points, t.x + t.width/2 - textWidth/2, t.y + t.height/2 + 4);
        }
        
        // Draw trajectory preview
        if (model.getProjectiles().isEmpty()) {
            g2d.setColor(new Color(255, 0, 0, 100));
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int startX = cannon.x + cannon.width;
            int startY = cannon.y + cannon.height/2;
            
            for (int i = 0; i < 50; i += 2) {
                int x = startX + (int)(Math.cos(cannon.angle) * cannon.power * i / 5);
                int y = startY - (int)(Math.sin(cannon.angle) * cannon.power * i / 5) + (i * i) / 50;
                
                if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) break;
                
                g2d.fillOval(x - 1, y - 1, 2, 2);
            }
        }
    }
}