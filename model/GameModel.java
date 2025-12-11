package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

public class GameModel {
    
    public static class Projectile {
        public int x, y;
        public int speedX, speedY;
        public boolean active;
        
        public Projectile(int x, int y, int speedX, int speedY) {
            this.x = x; this.y = y;
            this.speedX = speedX; this.speedY = speedY;
            this.active = true;
        }
        
        public void update() {
            x += speedX;
            y += speedY;
            if (x < 0 || x > 800 || y < 0 || y > 600) {
                active = false;
            }
        }
    }
    
    public static class Target {
        public int x, y, width, height;
        public int speedX;
        public boolean active;
        public Color color;
        public int points;
        
        public Target(int x, int y, int width, int height, int speedX, Color color, int points) {
            this.x = x; this.y = y;
            this.width = width; this.height = height;
            this.speedX = speedX;
            this.active = true;
            this.color = color;
            this.points = points;
        }
        
        public void update() {
            x += speedX;
            if (x < -width) {
                x = 800;
                y = new Random().nextInt(400) + 50;
            }
        }
        
        public boolean checkCollision(Projectile p) {
            return (p.x >= x && p.x <= x + width && 
                    p.y >= y && p.y <= y + height);
        }
    }
    
    public static class Cannon {
        public int x, y, width, height;
        public double angle;
        public int power;
        
        public Cannon(int x, int y) {
            this.x = x; this.y = y;
            this.width = 60; this.height = 40;
            this.angle = Math.PI / 4;
            this.power = 15;
        }
        
        public void moveUp() {
            angle += 0.1;
            if (angle > Math.PI / 2) angle = Math.PI / 2;
        }
        
        public void moveDown() {
            angle -= 0.1;
            if (angle < 0) angle = 0;
        }
        
        public void increasePower() {
            power += 1;
            if (power > 30) power = 30;
        }
        
        public void decreasePower() {
            power -= 1;
            if (power < 5) power = 5;
        }
        
        public Projectile shoot() {
            int speedX = (int) (Math.cos(angle) * power);
            int speedY = -(int) (Math.sin(angle) * power);
            return new Projectile(x + width/2, y, speedX, speedY);
        }
    }
    
    private Cannon cannon;
    private List<Projectile> projectiles;
    private List<Target> targets;
    private int score, shots, hits;
    private Random random;
    
    public GameModel() {
        initGame();
    }
    
    private void initGame() {
        cannon = new Cannon(50, 500);
        projectiles = new ArrayList<>();
        targets = new ArrayList<>();
        score = 0;
        shots = 0;
        hits = 0;
        random = new Random();
        createTargets();
    }
    
    private void createTargets() {
        for (int i = 0; i < 5; i++) {
            int y = random.nextInt(400) + 50;
            int speed = random.nextInt(3) + 1;
            Color color;
            int points;
            
            if (i % 3 == 0) {
                color = Color.RED;
                points = 30;
            } else if (i % 3 == 1) {
                color = Color.BLUE;
                points = 20;
            } else {
                color = Color.GREEN;
                points = 10;
            }
            
            targets.add(new Target(800 + i * 150, y, 40, 30, -speed, color, points));
        }

    }
    public void update() {
        // Update projectiles
        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile p : projectiles) {
            p.update();
            if (!p.active) projectilesToRemove.add(p);
        }
        projectiles.removeAll(projectilesToRemove);
        
        // Update targets
        for (Target t : targets) {
            t.update();
        }
        
        // Check collisions
        List<Target> targetsToRemove = new ArrayList<>();
        for (Projectile p : projectiles) {
            for (Target t : targets) {
                if (t.checkCollision(p)) {
                    targetsToRemove.add(t);
                    score += t.points;
                    hits++;
                    p.active = false;
                    break;
                }
            }
        }
        targets.removeAll(targetsToRemove);
        
        // Add new targets if needed
        if (targets.size() < 3) {
            int y = random.nextInt(400) + 50;
            int speed = random.nextInt(3) + 1;
            Color color = new Color(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            );
            targets.add(new Target(800, y, 40, 30, -speed, color, 15));
        }
    }
    
    public void shoot() {
        projectiles.add(cannon.shoot());
        shots++;
    }
    
    // Getters
    public Cannon getCannon() { return cannon; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<Target> getTargets() { return targets; }
    public int getScore() { return score; }
    public int getShots() { return shots; }
    public int getHits() { return hits; }
    
    // Control methods
    public void cannonUp() { cannon.moveUp(); }
    public void cannonDown() { cannon.moveDown(); }
    public void powerUp() { cannon.increasePower(); }
    public void powerDown() { cannon.decreasePower(); }
    public void restartGame() { initGame(); }
}
