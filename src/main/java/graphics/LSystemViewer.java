package graphics;

import javax.swing.JFrame;
import model.PlantController;

/**
 * Frame for displaying animated L-System turtle graphics.
 */
public class LSystemViewer extends JFrame {
    public LSystemViewer() {
        PlantController controller = PlantController.getInstance();
        Renderer renderer = new Renderer(controller.getPlant());

        initializeFrame();
        add(new LSystemPanel(controller, renderer));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes basic frame properties.
     */
    private void initializeFrame() {
        setTitle("L-System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
