package graphics;

import core.LSystemEngine;
import core.RandomSingleton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import model.PlantController;

/**
 * An interactive panel that displays and animates L-System turtle graphics visualizations.
 * <p>
 * This panel provides a complete visualization environment for L-System fractals and plant
 * structures. It automatically animates through iterations of the L-System, displaying
 * progressively more complex patterns. The panel includes keyboard controls for navigation
 * between different plant models, animation control, and random seed regeneration.
 * </p>
 * <p>
 * <strong>Keyboard Controls:</strong>
 * <ul>
 * <li><strong>LEFT Arrow</strong> - Switch to the previous plant rule</li>
 * <li><strong>RIGHT Arrow</strong> - Switch to the next plant rule</li>
 * <li><strong>SPACE</strong> - Pause/unpause the animation</li>
 * <li><strong>S</strong> - Generate a new random seed and restart</li>
 * </ul>
 * </p>
 * <p>
 * The panel displays real-time information including the current iteration number,
 * random seed value, and plant name. The animation automatically cycles from iteration
 * 0 to the maximum iteration count, then restarts.
 * </p>
 *
 * @author Hytalo
 * @version 1.0
 * @since 1.0
 * @see LSystemEngine
 * @see Renderer
 * @see PlantController
 */
public class LSystemPanel extends JPanel {
    /** The background color of the panel. */
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    /** The delay between animation frames in milliseconds. */
    private static final int TIMER_DELAY_MS = 1000;

    /** The maximum number of iterations before restarting the animation. */
    private static final int MAX_ITERATIONS = 8;

    /** The L-System engine responsible for applying production rules. */
    private LSystemEngine lsystemEngine;

    private int currentIteration = 0;

    /** The current L-System instruction string to be rendered. */
    private String instructions;

    /** The timer that drives the animation by triggering periodic updates. */
    private final Timer animationTimer;

    /** The renderer responsible for converting L-System strings to graphics. */
    private final Renderer renderer;

    /** The controller managing the collection of plant models. */
    private final PlantController controller;

    /** Flag indicating whether the animation is currently paused. */
    private boolean paused = false;

    /**
     * Constructs a new LSystemPanel with the specified controller and renderer.
     * <p>
     * This constructor initializes all components, sets up the animation timer,
     * configures keyboard bindings, and starts the animation. The panel is
     * initialized with the current plant from the controller and begins at
     * iteration 0 with the rule's axiom.
     * </p>
     *
     * @param controller the plant controller managing plant models
     * @param renderer the renderer for converting L-System strings to graphics
     */
    public LSystemPanel(PlantController controller, Renderer renderer) {
        this.controller = controller;
        this.renderer = renderer;

        animationTimer = createAnimationTimer();
        this.lsystemEngine = new LSystemEngine(controller.getRule());
        instructions = lsystemEngine.getRule().getAxiom();

        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
        startAnimation();
        setupKeyBindings();
    }

    /**
     * Creates and configures the animation timer.
     * <p>
     * The timer is set to fire at regular intervals defined by {@link #TIMER_DELAY_MS},
     * triggering L-System updates on each tick.
     * </p>
     *
     * @return a configured Timer instance for animation
     */
    private Timer createAnimationTimer() {
        return new Timer(TIMER_DELAY_MS, e -> updateLSystem());
    }

    /**
     * Starts the animation timer.
     * <p>
     * This method begins the automatic iteration cycle, causing the L-System
     * to evolve and repaint at regular intervals.
     * </p>
     */
    private void startAnimation() {
        animationTimer.start();
    }

    /**
     * Restarts the animation from iteration 0.
     * <p>
     * This method resets the iteration counter to 0, reinitializes the instruction
     * string to the axiom, and resets the random number generator to ensure
     * deterministic reproduction of the initial pattern. This is essential for
     * stochastic L-Systems to produce consistent results when restarting.
     * </p>
     */
    private void restartAnimation() {
        currentIteration = 0;
        instructions = lsystemEngine.getRule().getAxiom();
        // In order to recreate the same tree as the first iteration
        // we need to reset the states of the SplittableRandom, otherwise
        // it will continue giving the next pseudo-random numbers in the sequence
        // instead of coming to the first number in the sequence.
        RandomSingleton.getInstance().reset();
    }

    /**
     * Updates the L-System to the next iteration and triggers a repaint.
     * <p>
     * This method is called by the animation timer. It advances the L-System by
     * one iteration by applying production rules to the current instruction string.
     * When the maximum iteration count is reached, the animation automatically
     * restarts from iteration 0.
     * </p>
     */
    private void updateLSystem() {
        if (currentIteration >= MAX_ITERATIONS) {
            restartAnimation();
        } else {
            currentIteration++;
            instructions = lsystemEngine.applyRules(instructions);
        }

        repaint();
    }

    /**
     * Renders informational text overlay on the panel.
     * <p>
     * This method draws the current iteration number, random seed value, and
     * plant name in the upper-left corner of the panel, providing real-time
     * feedback to the user.
     * </p>
     *
     * @param g2d the Graphics2D context for rendering
     */
    private void drawUIInfo(Graphics2D g2d) {
        long seed = RandomSingleton.getInstance().getSeed();
        g2d.setColor(Color.BLACK);
        g2d.drawString("Iteration: " + currentIteration, 10, 25);
        g2d.drawString("Seed: " + seed, 10, 50);
        g2d.drawString("Name: " + controller.getName(), 10, 75);
    }

    /**
     * Renders the L-System visualization.
     * <p>
     * This method calculates the starting point at the bottom center of the panel
     * and delegates to the renderer to convert the instruction string into
     * drawable geometry.
     * </p>
     *
     * @param g2d the Graphics2D context for rendering
     */
    private void drawLSystem(Graphics2D g2d) {
        Point2D startPoint = new Point2D.Double(getWidth() / 2, getHeight());

        renderer.render(g2d, instructions, startPoint, currentIteration);
    }

    /**
     * Toggles the animation between paused and running states.
     * <p>
     * When paused, the animation timer is stopped and the visualization remains
     * frozen at the current iteration. When unpaused, the timer restarts and
     * animation continues from where it was paused.
     * </p>
     */
    private void pauseAndUnpause() {
        paused = !paused;

        if (paused) animationTimer.stop();
        else animationTimer.start();
    }

     /**
     * Generates a new random seed and restarts the animation.
     * <p>
     * This method creates a new random seed for stochastic L-Systems, allowing
     * users to explore different variations of the same rule set. The animation
     * is restarted from iteration 0 with the new seed, and the UI is updated
     * to display the new seed value.
     * </p>
     */
    private void regenerateSeed() {
        RandomSingleton.getInstance().generateSeed();
        restartAnimation();
    }

    /**
     * Configures keyboard bindings for interactive control.
     * <p>
     * This method sets up the following key mappings:
     * <ul>
     * <li><strong>LEFT Arrow</strong> - Navigate to the previous plant rule</li>
     * <li><strong>RIGHT Arrow</strong> - Navigate to the next plant rule</li>
     * <li><strong>SPACE</strong> - Pause/unpause the animation</li>
     * <li><strong>S</strong> - Generate a new random seed and restart</li>
     * </ul>
     * All bindings are configured to work when the window is in focus.
     * </p>
     */
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "previousRule");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "nextRule");
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "pauseAndUnpause");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "regenerateSeed");

        actionMap.put(
                "previousRule",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchRule(controller.shiftLeft());
                    }
                });

        actionMap.put(
                "nextRule",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchRule(controller.shiftRight());
                    }
                });

        actionMap.put(
                "pauseAndUnpause",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseAndUnpause();
                    }
                });

        actionMap.put(
                "regenerateSeed",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        regenerateSeed();
                    }
                });
    }

    /**
     * Switches to a different L-System rule and resets the visualization.
     * <p>
     * This method updates the engine with a new rule from the controller,
     * reconfigures the renderer for the new plant parameters, resets the
     * iteration counter, and reinitializes the instruction string to the
     * new rule's axiom. The display is immediately updated to show the new
     * plant at iteration 0.
     * </p>
     *
     * @param updatedController the controller after navigation (same instance, updated state)
     */
    private void switchRule(PlantController updatedController) {
        lsystemEngine.setRule(updatedController.getRule());
        renderer.updatesInterpreter(controller.getPlant());
        currentIteration = 0;
        instructions = lsystemEngine.getRule().getAxiom();
        repaint();
    }

    /**
     * Configures antialiasing for smoother line rendering.
     * <p>
     * This method enables antialiasing and pure stroke control to improve
     * the visual quality of the rendered L-System graphics, producing
     * smoother lines and curves.
     * </p>
     *
     * @param g2 the Graphics2D context to configure
     */
    private void setAntialiasing(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    /**
     * Renders the panel contents.
     * <p>
     * This method is called automatically by Swing when the panel needs to be
     * redrawn. It configures rendering hints for quality, draws the informational
     * overlay, and renders the current L-System visualization.
     * </p>
     *
     * @param g the Graphics context provided by Swing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        setAntialiasing(g2d);

        drawUIInfo(g2d);
        drawLSystem(g2d);
    }
}
