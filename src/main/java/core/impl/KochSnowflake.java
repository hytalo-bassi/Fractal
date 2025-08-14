
package core.impl;

import java.util.HashMap;
import java.util.Map;
import core.LSystemRule;

// Do not renders correctly, since the interpreter uses the constant 25 degree angle instead
// of the 60 degree angle that must be used. This can be solved by using the parDOL system (e.g +(60) and -(60))
// or, in some way, force the interpreter to know how to change the angle and features of this
// system.

/**
 * Implementation of Koch Snowflake using L system rules.
 * Creates a snow flake.
 */
public class KochSnowflake implements LSystemRule {
    
    private static final String AXIOM = "F--F--F";
    private final Map<Character, String> productionRules;
    
    public KochSnowflake() {
        productionRules = createProductionRules();
    }
    
    private Map<Character, String> createProductionRules() {
        Map<Character, String> rules = new HashMap<>();
        rules.put('F', "F+F--F+F");
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
