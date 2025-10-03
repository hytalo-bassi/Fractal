package graphics;

import javax.swing.JFrame;
import model.PlantController;

/**
 * A top-level window frame for displaying animated L-System turtle graphics visualizations.
 * <p>
 * This class serves as the main application window that hosts the L-System rendering
 * components. It integrates the {@link PlantController} for managing plant models and
 * the {@link Renderer} for generating visual output, wrapping them in an interactive
 * {@link LSystemPanel}.
 * </p>
 * <p>
 * The viewer automatically configures itself with appropriate window properties,
 * centers itself on the screen. It provides a complete environment for visualizing 
 * and interacting with L-System patterns and plant structures.
 * </p>
 * <p>
 * <strong>Usage example:</strong>
 * <pre>{@code
 * SwingUtilities.invokeLater(() -> {
 *     LSystemViewer viewer = new LSystemViewer();
 *     viewer.setVisible(true);
 * });
 * }</pre>
 * </p>
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see LSystemPanel
 * @see PlantController
 * @see Renderer
 */
public class LSystemViewer extends JFrame {

    /**
     * Constructs a new LSystemViewer and initializes all components.
     * <p>
     * This constructor performs the following initialization steps:
     * <ol>
     * <li>Retrieves the singleton PlantController instance</li>
     * <li>Creates a Renderer configured with the current plant</li>
     * <li>Initializes the frame properties (title, close operation)</li>
     * <li>Creates and adds an LSystemPanel with the controller and renderer</li>
     * <li>Packs the frame to fit the panel's preferred size</li>
     * <li>Centers the frame on the screen</li>
     * </ol>
     * The frame is created but not visible; call {@link #setVisible(boolean)} to display it.
     * </p>
     */
    public LSystemViewer() {
        PlantController controller = PlantController.getInstance();
        Renderer renderer = new Renderer(controller.getPlant());

        initializeFrame();
        add(new LSystemPanel(controller, renderer));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the basic properties of the frame.
     * <p>
     * This method configures:
     * <ul>
     * <li>Window title: "L-System"</li>
     * <li>Default close operation: Exit the application when the window is closed</li>
     * </ul>
     * </p>
     */
    private void initializeFrame() {
        setTitle("L-System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
