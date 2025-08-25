package core.impl;

import core.LSystemRule;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import utils.LStringBuilder;

/**
 * Implementation of binary tree using L-System rules.
 * Creates branching structures that resemble a binary tree.
 */
public class BinaryTree implements LSystemRule {
    private final LStringBuilder builder = new LStringBuilder();
    private final double initialQuantity = 25;
    private final double constDecay = 0.1;

    private static final String AXIOM = new LStringBuilder().forward("12").leaf().build();
    private final Map<Character, Function<String[], String>> parametricProductionRules;

    public BinaryTree() {
        parametricProductionRules = createParametricProductionRules();
    }

    /**
     * Creates the parametric production rules
     * @return The map linking a symbol -> replacement function
     */
    private Map<Character, Function<String[], String>> createParametricProductionRules() {
        Map<Character, Function<String[], String>> rules = new HashMap<>();

        // F(step: how much units forward) -> F(x + exponentialDecayFunctionStep)
        rules.put(
                'F',
                (args) -> {
                    if (args != null && args.length >= 1) {
                        try {
                            double lastStep = Double.parseDouble(args[0]);
                            double currentStep = lastStep + expDecay(lastStep);
                            String formattedStep = String.format("%.2f", currentStep);

                            return builder.forward(formattedStep).build();
                        } catch (Exception e) {
                            return builder.forward("10").build();
                        }
                    }

                    return builder.forward("10").build();
                });
        rules.put(
                '0',
                (args) ->
                        builder.forward("10")
                                .openBranch()
                                .turnLeft()
                                .forward("5")
                                .leaf()
                                .closeBranch()
                                .turnRight()
                                .forward("5")
                                .leaf()
                                .build());

        return rules;
    }

    /**
     * Exponential decay function of x.
     * @param x the last step
     * @return the result of the exponential decay function
     */
    private double expDecay(double x) {
        return initialQuantity * Math.exp(-constDecay * x);
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
