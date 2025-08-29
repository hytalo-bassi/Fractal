package graphics;

import javax.swing.JFrame;

import core.RuleController;

/**
 * Frame for displaying animated L-System turtle graphics.
 */
public class LSystemViewer extends JFrame {
    public LSystemViewer() {
        RuleController controller = new RuleController();
        Renderer renderer = new Renderer();

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
