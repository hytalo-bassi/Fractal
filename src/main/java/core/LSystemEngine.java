package core;

import java.util.Map;

/**
 * Core engine for L-System generation.
 * Handles the iterative application of production rules to generate L-system strings.
 */
public class LSystemEngine {
    
    private final LSystemRule rule;
    
    /**
     * Creates a new L-System engine with the specified rules
     * @param rule The L-System rule set to use
     */
    public LSystemEngine(LSystemRule rule) {
        this.rule = rule;
    }
    
    /**
     * Generates an L-System string for the specified number of iterations
     * @param iterations Number of iterations to apply
     * @return Generated L-System string
     */
    public String generate(int iterations) {
        if (iterations < 0) {
            throw new IllegalArgumentException("Iterations must be non-negative");
        }
        
        String current = rule.getAxiom();
        
        for (int i = 0; i < iterations; i++) {
            current = applyRules(current);
        }
        
        return current;
    }
    
    /**
     * Applies production rules to transform the current string
     * @param input Current L-System string
     * @return Transformed string after applying rules
     */
    private String applyRules(String input) {
        StringBuilder result = new StringBuilder();
        Map<Character, String> productionRules = rule.getProductionRules();
        
        for (char symbol : input.toCharArray()) {
            String replacement = productionRules.get(symbol);
            result.append(replacement != null ? replacement : symbol);
        }
        
        return result.toString();
    }
    
    /**
     * Gets the current rule set
     * @return Current L-System rule
     */
    public LSystemRule getRule() {
        return rule;
    }
}
