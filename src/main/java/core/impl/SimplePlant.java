package core.impl;

import core.LSystemRule;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import utils.LStringBuilder;

/**
 * Implementation of plant growth L-System rules.
 * Creates branching structures that resemble plant growth patterns.
 */
public class SimplePlant implements LSystemRule {
    private final LStringBuilder builder = new LStringBuilder();

    private static final String AXIOM = "X";
    private final Map<Character, String> productionRules;

    public SimplePlant() {
        productionRules = createProductionRules();
    }

    private Map<Character, String> createProductionRules() {
        Map<Character, String> rules = new HashMap<>();
        rules.put(
                'X',
                builder.forward()
                        .turnLeft()
                        .openBranch()
                        .openBranch()
                        .symbol('X')
                        .closeBranch()
                        .turnRight()
                        .symbol('X')
                        .closeBranch()
                        .turnRight()
                        .forward()
                        .openBranch()
                        .turnRight()
                        .forward()
                        .symbol('X')
                        .closeBranch()
                        .turnLeft()
                        .symbol('X')
                        .build());
        rules.put('F', builder.forward().forward().build());
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
        return "Plant growth L-System with branching structures. "
                + "X represents nothing (yet). "
                + "F represents stem segments. "
                + "Brackets [ ] represent branching points.";
    }

    @Override
    public Map<Character, Function<String[], String>> getParametricProductionRules() {
        throw new UnsupportedOperationException(
                "Unimplemented method 'getParametricProductionRules'");
    }
}
