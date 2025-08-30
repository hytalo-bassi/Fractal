package core.impl;

import core.LSystemRule;
import core.RandomSingleton;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.function.Function;
import utils.LStringBuilder;

/**
 * Implementation of stochastic binary tree using L-System rules.
 *
 * The main difference between the normal BinaryTree and this one here is the production for leaves:
 * '0'(no parameters) -> F(x)[A(+theta)F(y)0][A(-gamma)F(z)0]
 *
 * Being x, y, z the trunk and right and left branches' random lengths, theta and gamma the random angle
 *
 * Creates branching structures that resemble a binary tree with varying angles and distances.
 */
public class StochasticBinaryTree implements LSystemRule {
    private static final int MIN_TRUNK_LENGTH = 1;
    private static final int MAX_TRUNK_LENGTH = 20;
    private static final int MIN_BRANCH_LENGTH = 1;
    private static final int MAX_BRANCH_LENGTH = 20;
    private static final int MIN_BRANCH_ANGLE = 1;
    private static final int MAX_BRANCH_ANGLE = 25; // Angles > 25 look unnatural

    private static final double INITIAL_QUANTITY = 25;
    private static final double DECAY_CONSTANT = 0.1;

    private static final String DEFAULT_STEP = "10";

    // Instead of getting the SplittableRandom instance we get the singleton to ensure
    // that if the application resets the SplittableRandom's state
    // we are going to use the updated state or seed
    private final RandomSingleton randomSingleton = RandomSingleton.getInstance();

    private final LStringBuilder builder = new LStringBuilder();

    private static final String AXIOM = new LStringBuilder().leaf().build();
    private final Map<Character, Function<String[], String>> parametricProductionRules;

    public StochasticBinaryTree() {
        parametricProductionRules = createParametricProductionRules();
    }

    /**
     * Creates the stochastic parametric production rules
     * @return The map linking a symbol -> replacement function
     */
    private Map<Character, Function<String[], String>> createParametricProductionRules() {
        Map<Character, Function<String[], String>> rules = new HashMap<>();

        // Forward movement rule with exponential decay
        rules.put('F', this::createForwardRule);

        // Leaf expansion rule with random branching
        rules.put('0', this::createLeafExpansionRule);

        return rules;
    }

    /**
     * Creates a forward movement rule with exponential decay.
     *
     * @param args the arguments from the F(x) symbol
     * @return the replacement string for forward movement
     */
    private String createForwardRule(String[] args) {
        if (args != null && args.length >= 1) {
            try {
                double lastStep = Double.parseDouble(args[0]);
                double currentStep = lastStep + expDecay(lastStep);
                String formattedStep = String.format("%.2f", currentStep);

                return builder.forward(formattedStep).build();
            } catch (NumberFormatException e) {
                // Fall through to default case
            }
        }
        return builder.forward(DEFAULT_STEP).build();
    }

    /**
     * Creates a leaf expansion rule with random branches.
     * Generates two branches at random angles and lengths.
     *
     * @param args the arguments (unused for leaf expansion)
     * @return the replacement string for leaf expansion
     */
    private String createLeafExpansionRule(String[] args) {
        SplittableRandom rand = randomSingleton.getRandom();

        // Generate random dimensions
        int trunkLength = rand.nextInt(MIN_TRUNK_LENGTH, MAX_TRUNK_LENGTH);
        int leftBranchLength = rand.nextInt(MIN_BRANCH_LENGTH, MAX_BRANCH_LENGTH);
        int rightBranchLength = rand.nextInt(MIN_BRANCH_LENGTH, MAX_BRANCH_LENGTH);

        // Generate random angles (kept small for natural appearance)
        int leftBranchAngle = rand.nextInt(MIN_BRANCH_ANGLE, MAX_BRANCH_ANGLE);
        int rightBranchAngle = rand.nextInt(MIN_BRANCH_ANGLE, MAX_BRANCH_ANGLE);

        return builder.forward(String.valueOf(trunkLength))
                .openBranch()
                .symbol('A', String.valueOf(leftBranchAngle))
                .forward(String.valueOf(leftBranchLength))
                .leaf()
                .closeBranch()
                .symbol('A', "-" + rightBranchAngle)
                .forward(String.valueOf(rightBranchLength))
                .leaf()
                .build();
    }

    /**
     * Exponential decay function of x.
     * @param x the last step
     * @return the result of the exponential decay function
     */
    private double expDecay(double x) {
        return INITIAL_QUANTITY * Math.exp(-DECAY_CONSTANT * x);
    }

    @Override
    public boolean hasParametric() {
        return true;
    }

    @Override
    public String getAxiom() {
        return AXIOM;
    }

    @Override
    public Map<Character, String> getProductionRules() {
        return new HashMap<>(); // empty because the LSystemEngine automatically
        // chooses between parametric and normal production
        // rules. Meaning that method is a dead code that
        // should not ever run.
    }

    @Override
    public String getDescription() {
        return "Simple Binary Tree. "
                + "0 represents leaves, F represents forward movements. "
                + "Brackets [ ] represent branching points.";
    }

    @Override
    public Map<Character, Function<String[], String>> getParametricProductionRules() {
        return new HashMap<>(parametricProductionRules);
    }
}
