
package core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import core.LSystemRule;

/**
 * Implementation of binary tree using L-System rules.
 * Creates branching structures that resemble a binary tree.
 */
public class BinaryTree implements LSystemRule {
    
    private static final String AXIOM = "0";
    private final Map<Character, String> productionRules;
    private final Map<Character, Function<String[], String>> parametricProductionRules;

    
    public BinaryTree() {
        productionRules = createProductionRules();
        parametricProductionRules = createParametricProductionRules();
    }
    
    private Map<Character, String> createProductionRules() {
        Map<Character, String> rules = new HashMap<>();
        rules.put('0', "F[+F0]-F0");
        rules.put('F', "FF");
        return rules;
    }
    
    private Map<Character, Function<String[], String>> createParametricProductionRules() {
        Map<Character, Function<String[], String>> rules = new HashMap<>();

        // F(step: how much units forward) -> F(x + exponentialDecayFunctionStep)
        rules.put('F', (args) -> {
            if (args != null && args.length >= 1) {
                try {
                    double lastStep = Double.parseDouble(args[0]);
                    double currentStep = lastStep + (25 * Math.exp(-0.1 * lastStep)); // step + 25e^(-0.1*step)
                    String formattedStep = String.format("%.2f", currentStep);

                    return "F(" + formattedStep  + ")"; 
                } catch (Exception e) {
                    return "F(10)";
                }
            }

            return "F(10)";
        });
        rules.put('0', (args) -> "F(10)[+F(5)0]-F(5)0");
        return rules;
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
        return new HashMap<>(productionRules);
    }
    
    @Override
    public String getDescription() {
        return "Simple Binary Tree" +
               "0 represents leaves, F represents forward movements. " +
               "Brackets [ ] represent branching points.";
    }

    @Override
    public Map<Character, Function<String[], String>> getParametricProductionRules() {
        return new HashMap<>(parametricProductionRules);
    }
}
