package core;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;

/**
 * Core engine for L-System generation.
 * Handles the iterative application of production rules to generate L-system strings.
 */
public class LSystemEngine {
    private LSystemRule rule;

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
    public String applyRules(String input) {
        StringBuilder result = new StringBuilder();
        Map<Character, String> productionRules = rule.getProductionRules();
        Map<Character, Function<String[], String>> paraProductionRules =
                null; // parametric production rules

        if (rule.hasParametric()) paraProductionRules = rule.getParametricProductionRules();

        for (String symbol : splitSymbols(input)) {
            if (paraProductionRules != null) {
                // The map always has a one character symbol in the beginning
                Function<String[], String> replacementFunction =
                        paraProductionRules.get(symbol.charAt(0));

                result.append(
                        replacementFunction != null
                                ? replacementFunction.apply(splitParameters(symbol))
                                : symbol);
            } else {
                String replacement = productionRules.get(symbol.charAt(0));
                result.append(replacement != null ? replacement : symbol.charAt(0));
            }
        }

        return result.toString();
    }

    public static String[] splitParameters(String symbol) {
        // The smallest module symbol has 3 characters F()
        if (symbol.length() >= 3) {
            return symbol.substring(2, symbol.length() - 1).split(",");
        }

        return null;
    }

    /**
     * Gets the current rule set
     * @return Current L-System rule
     */
    public LSystemRule getRule() {
        return rule;
    }

    /**
     * Sets the current rule set
     */
    public void setRule(LSystemRule rule) {
        this.rule = rule;
    }

    /**
     * Splits the L-system between modules (e.g "F(10)") and symbols (e.g "0").
     * @return The list of symbols contaning both the symbol and module.
     */
    public static String[] splitSymbols(String input) {
        ArrayList<String> listSymbols = new ArrayList<>();

        int startOfModule = 0;
        boolean readingModule = false;

        int i = 0;
        while (i < input.length()) {
            // The second predicate checks if the module starts at the end of the string meaning it
            // is malformed!
            // e.g: input = 'F('
            if (!readingModule && i + 1 < input.length() - 1 && input.charAt(i + 1) == '(') {
                readingModule = true;
                startOfModule = i;
                i += 1; // jump over the '('
            } else if (!readingModule) {
                listSymbols.add(String.valueOf(input.charAt(i)));
            }

            if (readingModule && i < input.length() && input.charAt(i) == ')') {
                listSymbols.add(input.substring(startOfModule, i + 1));
                startOfModule = 0;
                readingModule = false;
            }

            i++;
        }

        if (readingModule) {
            throw new Error(
                    "Malformed input: no module termination found! Module started at "
                            + startOfModule
                            + "\n\tInput string: "
                            + input);
        }

        return listSymbols.toArray(new String[0]);
    }
}
