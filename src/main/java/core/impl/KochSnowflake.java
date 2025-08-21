package core.impl;

import core.LSystemRule;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// 1. Do not renders correctly, since the interpreter uses the constant 25 degree angle instead
// of the 60 degree angle that must be used. This can be solved by using the parDOL system (e.g
// +(60) and -(60))
// or, in some way, force the interpreter to know how to change the angle and features of this
// system.

// 2. After implementing parametric L-systems, the rule still do not produce a good looking
// snowflake. Possible cause
// is wrong algorithm, but it should be researched first.

/**
 * Implementation of Koch Snowflake using L system rules.
 * Creates a snow flake.
 */
public class KochSnowflake implements LSystemRule {

    private static final String AXIOM = "F(100)A(-120)F(100)A(-120)F(100)";
    private final Map<Character, String> productionRules;
    private final Map<Character, Function<String[], String>> parametricProductionRules;

    public KochSnowflake() {
        productionRules = createProductionRules();
        parametricProductionRules = createParametricProductionRules();
    }

    private Map<Character, String> createProductionRules() {
        Map<Character, String> rules = new HashMap<>();
        rules.put('F', "F+F--F+F");
        return rules;
    }

    private Map<Character, Function<String[], String>> createParametricProductionRules() {
        Map<Character, Function<String[], String>> rules = new HashMap<>();

        rules.put('F', (args) -> "F(100)A(60)F(100)A(-120)F(100)A(60)F(100)");
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
        return new HashMap<>(productionRules); // Return defensive copy
    }

    @Override
    public String getDescription() {
        return "Koch snowflake"
                + "F represents forward movements "
                + "A(x) turns the turtle `x` degrees";
    }

    @Override
    public Map<Character, Function<String[], String>> getParametricProductionRules() {
        return new HashMap<>(parametricProductionRules);
    }
}
