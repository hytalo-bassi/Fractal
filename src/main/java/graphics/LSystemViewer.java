package graphics;

import javax.swing.JFrame;

/**
 * Frame for displaying animated L-System turtle graphics.
 */
public class LSystemViewer extends JFrame {
    public LSystemViewer() {
        initializeFrame();
        add(new LSystemPanel());
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
