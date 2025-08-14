
package core.impl;

import java.util.HashMap;
import java.util.Map;
import core.LSystemRule;

/**
 * Implementation of binary tree using L-System rules.
 * Creates branching structures that resemble a binary tree.
 */
public class BinaryTree implements LSystemRule {
    
    private static final String AXIOM = "0";
    private final Map<Character, String> productionRules;
    
    public BinaryTree() {
        productionRules = createProductionRules();
    }
    
    private Map<Character, String> createProductionRules() {
        Map<Character, String> rules = new HashMap<>();
        rules.put('0', "F[+F0]-F0");
        rules.put('F', "FF");
        return rules;
    }
    
    @Override
    public String getAxiom() {
        return AXIOM;
    }
    
    @Override
    public Map<Character, String> getProductionRules() {
        return new HashMap<>(productionRules); // Return defensive copy
    }
    
    @Override
    public String getDescription() {
        return "Simple Binary Tree" +
               "0 represents leaves, F represents forward movements. " +
               "Brackets [ ] represent branching points.";
    }
}
