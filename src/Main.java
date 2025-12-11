import model.GameModel;
import view.GameView;
import controller.GameController;

public class Main {
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                System.out.println(" Iniciando Juego de Cañón MVC...");
                System.out.println(" Modelo: GameModel");
                System.out.println(" Vista: GameView");
                System.out.println(" Controlador: GameController");
                
                // Crear componentes MVC
                GameModel model = new GameModel();
                GameView view = new GameView();
                GameController controller = new GameController(model, view);
                
                System.out.println(" Juego iniciado correctamente!");
                System.out.println(" Presiona ESPACIO para disparar");
                System.out.println(" Usa las flechas para ajustar ángulo y potencia");
                
            } catch (Exception e) {
                System.err.println(" Error al iniciar el juego:");
                e.printStackTrace();
            }
        });
    }
}