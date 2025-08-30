package core;

import java.time.Instant;
import java.util.SplittableRandom;

/**
 * Singleton class that manages random number generation for L-System implementations.
 * Provides a centralized source of randomness with time-based seed generation,
 * ensuring consistent random behavior across all L-System rule implementations.
 *
 * <p>This singleton is particularly useful for stochastic L-Systems where multiple
 * rule implementations need access to the same random number generator to maintain
 * reproducible results when using the same seed.</p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * // Get the singleton instance
 * RandomSingleton randomManager = RandomSingleton.getInstance();
 *
 * // Generate a new time-based seed
 * randomManager.generateSeed();
 *
 * // Use the random generator
 * SplittableRandom rand = randomManager.getRandom();
 * int randomValue = rand.nextInt(1, 20);
 *
 * // Set a specific seed for reproducible results
 * randomManager.setSeed(12345L);
 * </pre>
 *
 * <h3>Thread Safety:</h3>
 * <p><strong>Note:</strong> This implementation is NOT thread-safe. If used in a
 * multi-threaded environment, external synchronization is required.</p>
 *
 * @author [Your Name]
 * @version 1.0
 * @since 1.0
 */
public class RandomSingleton {
    private Long seed;
    private static RandomSingleton instance;
    private SplittableRandom random;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the singleton with a time-based seed.
     */
    private RandomSingleton() {
        generateSeed();
    }

    /**
     * Returns the singleton instance of RandomSingleton.
     * Creates a new instance if one doesn't exist (lazy initialization).
     * Automatically generates a seed.
     *
     * @return the singleton RandomSingleton instance
     */
    public static RandomSingleton getInstance() {
        if (instance == null) {
            instance = new RandomSingleton();
        }
        return instance;
    }

    /**
     * Generates and sets a new time-based seed.
     * This method creates a highly unique seed by combining current time
     * milliseconds with nanosecond precision for maximum entropy.
     *
     * <p>Calling this method will reset the random number generator
     * with a new seed, making subsequent random number sequences different
     * from previous ones.</p>
     */
    public void generateSeed() {
        setSeed(generateTimeSeed());
    }

    /**
     * Resets the SplittableRandom's state with the same seed.
     *
     * After resetting the state, calling any number generator like nextInt()
     * will give you the first number in the pseudo-random sequence as you did only
     * call it once.
     */
    public void reset() {
        setSeed(getSeed());
    }

    /**
     * Generates a time-based seed with high precision and uniqueness.
     * Combines epoch milliseconds with nanoseconds using XOR operation
     * to create a seed that's unique even when called multiple times
     * within the same millisecond.
     *
     * <p>The algorithm works as follows:</p>
     * <ol>
     *   <li>Gets current time in milliseconds since epoch</li>
     *   <li>Gets current nanoseconds within the current second</li>
     *   <li>Right-shifts nanoseconds by 16 bits to reduce precision</li>
     *   <li>XORs milliseconds with shifted nanoseconds for final seed</li>
     * </ol>
     *
     * @return a time-based seed value with high uniqueness probability
     */
    private long generateTimeSeed() {
        Instant now = Instant.now();

        // mixing milli with nanoseconds gives us a really random number
        return now.toEpochMilli() ^ (now.getNano() >>> 16);
    }

    /**
     * Sets the seed for random number generation.
     * Creates a new SplittableRandom instance with the specified seed (resets the SplittableRandom),
     * replacing any existing random number generator.
     *
     * <p>Using the same seed will produce identical sequences of random numbers,
     * which is useful for creating reproducible L-System generations.</p>
     *
     * @param seed the seed value to use for random number generation
     */
    public void setSeed(long seed) {
        this.seed = seed;
        this.random = new SplittableRandom(seed);
    }

    /**
     * Returns the SplittableRandom instance for generating random numbers.
     *
     * <p>The returned SplittableRandom can be used directly for all random
     * number generation needs. It supports splitting into independent
     * random number streams if needed.</p>
     *
     * @return the SplittableRandom instance, never null because it is automatically
     * set when getInstance() is called
     */
    public SplittableRandom getRandom() {
        return random;
    }

    /**
     * Returns the current seed value.
     *
     * @return the current seed used for random number generation
     */
    public long getSeed() {
        return seed;
    }
}
