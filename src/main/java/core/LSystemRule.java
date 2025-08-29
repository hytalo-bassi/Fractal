package core;

import java.util.Map;
import java.util.function.Function;

/**
 * Interface defining an parametric L-System rule set.
 * Contains the axiom (starting string) and production rules for symbol transformation.
 */
public interface LSystemRule {

    /**
     * Gets the axiom (initial string) for this L-System
     * @return The axiom string
     */
    String getAxiom();

    /**
     * Gets the production rules for symbol transformation
     * @return Map of symbol -> replacement string
     */
    Map<Character, String> getProductionRules();

    /**
     * Gets the paremetric production rules for symbol transformation. The replacer function
     * receives the list of Strings consisting of each argument then return a replacement String.
     * @return Map of symbol -> replacer function
     */
    Map<Character, Function<String[], String>> getParametricProductionRules();

    /**
     *  Returns true if rule supports parametric L-Systems
     *  @return True if parDOL is supported
     */
    default boolean hasParametric() {
        return false;
    }

    /**
     * Gets a human-readable description of this rule set
     * @return Description of the L-System rule
     */
    String getDescription();
}
