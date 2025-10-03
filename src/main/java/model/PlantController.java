package model;

import core.LSystemRule;
import core.impl.trees.BinaryTree;
import core.impl.trees.KochSnowflake;
import core.impl.trees.SimplePlant;
import core.impl.trees.StochasticBinaryTree;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A singleton controller for managing a collection of plant models and navigating between them.
 * <p>
 * This class maintains a registry of {@link Plant} instances and provides navigation functionality
 * to cycle through them. It follows the Singleton pattern to ensure a single, globally accessible
 * instance exists throughout the application lifecycle.
 * </p>
 * <p>
 * The controller is initialized with a set of default plants including various tree structures
 * and fractal patterns. Clients can navigate between plants using {@link #shiftLeft()} and
 * {@link #shiftRight()} methods, with wrap-around support for circular navigation.
 * </p>
 * <p>
 * <strong>Usage example:</strong>
 * <pre>{@code
 * PlantController controller = PlantController.getInstance();
 * Plant current = controller.getPlant();
 * controller.shiftRight();
 * Plant next = controller.getPlant();
 * }</pre>
 * </p>
 *
 * @author Hytalo
 * @version 1.0
 * @since 1.0
 * @see Plant
 * @see PlantBuilder
 */
public class PlantController {
    /** The collection of registered plant models. */
    private ArrayList<Plant> plants;

    private int currentIndex = 0;
    
    /** The singleton instance of this controller. */
    private static PlantController instance;

    /**
     * Constructs a new PlantController and initializes it with default plants.
     * <p>
     * This constructor is private to enforce the Singleton pattern. It initializes
     * the plant collection and populates it with a set of predefined plant models.
     * </p>
     */
    private PlantController() {
        plants = new ArrayList<>();
        setDefaultPlants();
        PlantController.instance = this;
    }

    /**
     * Returns the currently selected plant.
     *
     * @return the plant at the current index
     * @throws IndexOutOfBoundsException if the plant collection is empty
     */
    public Plant getPlant() {
        return plants.get(currentIndex);
    }

    /**
     * Initializes the plant collection with a set of default plant models.
     * <p>
     * This method registers several predefined plants including:
     * <ul>
     * <li>Stochastic Binary Tree - a probabilistic branching structure</li>
     * <li>Binary Tree - a deterministic branching structure</li>
     * <li>Simple Plant - a basic plant model</li>
     * <li>Koch Snowflake - a fractal pattern</li>
     * </ul>
     * Each plant is configured with appropriate colors using HSB or RGB values.
     * </p>
     */
    private void setDefaultPlants() {
        registerPlant(
                PlantBuilder.start(new StochasticBinaryTree(), "Stochastic Binary Tree")
                        .setLineColor(Color.getHSBColor(0.083f, 0.6f, 0.4f))
                        .build());
        registerPlant(
                PlantBuilder.start(new BinaryTree(), "Binary Tree")
                        .setLineColor(Color.getHSBColor(0.083f, 0.6f, 0.4f))
                        .build());
        registerPlant(
                PlantBuilder.start(new SimplePlant(), "Simple Plant")
                        .setLineColor(Color.getHSBColor(0.3333f, 0.8f, 0.7f))
                        .build());
        registerPlant(
                PlantBuilder.start(new KochSnowflake(), "Koch Snow Flake")
                        .setLineColor(Color.BLUE)
                        .build());
    }

    /**
     * Advances to the next plant in the collection.
     * <p>
     * This method increments the current index with wrap-around behavior, allowing
     * circular navigation through the plant collection. When the end is reached,
     * navigation continues from the beginning.
     * </p>
     *
     * @return this controller instance for method chaining
     */
    public PlantController shiftRight() {
        currentIndex = (currentIndex + 1) % plants.size();

        return this;
    }

    /**
     * Returns to the previous plant in the collection.
     * <p>
     * This method decrements the current index with wrap-around behavior, allowing
     * circular navigation through the plant collection. When the beginning is reached,
     * navigation continues from the end.
     * </p>
     *
     * @return this controller instance for method chaining
     */
    public PlantController shiftLeft() {
        currentIndex = (currentIndex - 1 + plants.size()) % plants.size();

        return this;
    }

    /**
     * Registers a plant in the collection.
     * <p>
     * This method adds a plant to the internal collection if it is not null.
     * Null plants are silently ignored.
     * </p>
     *
     * @param plant the plant to register, or {@code null} to skip registration
     */
    private void registerPlant(Plant plant) {
        if (plant != null) plants.add(plant);
    }

    /**
     * Returns the L-System rule of the currently selected plant.
     * <p>
     * This is a convenience method equivalent to calling
     * {@code getPlant().getRule()}.
     * </p>
     *
     * @return the L-System rule of the current plant
     * @throws IndexOutOfBoundsException if the plant collection is empty
     */
    public LSystemRule getRule() {
        return plants.get(currentIndex).getRule();
    }

     /**
     * Returns the name of the currently selected plant.
     * <p>
     * This is a convenience method equivalent to calling
     * {@code getPlant().getName()}.
     * </p>
     *
     * @return the name of the current plant
     * @throws IndexOutOfBoundsException if the plant collection is empty
     */
    public String getName() {
        return plants.get(currentIndex).getName();
    }

    /**
     * Returns the singleton instance of the PlantController.
     * <p>
     * If no instance exists, a new one is created and initialized with default plants.
     * Subsequent calls return the same instance.
     * </p>
     *
     * @return the singleton PlantController instance
     */
    public static PlantController getInstance() {
        if (PlantController.instance != null) return PlantController.instance;
        return new PlantController();
    }
}
