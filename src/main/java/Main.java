import graphics.LSystemViewer;
import javax.swing.*;

/**
 * Main entry point for the L-System visualization application.
 * Creates and displays the L-System viewer window on the Event Dispatch Thread.
 */
public class Main {
    /**
     * Application entry point.
     * @param args command line arguments (currently unused)
     */
    public static void main(String[] args) {
        // Opens L system window.
        SwingUtilities.invokeLater(() -> {
            try {
                new LSystemViewer().setVisible(true);
                
            } catch (Exception e) {
                System.err.println("Failed to start L-System viewer: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
