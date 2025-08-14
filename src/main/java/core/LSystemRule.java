package core;

import java.util.Map;

/**
 * Interface defining an L-System rule set.
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
     * Gets a human-readable description of this rule set
     * @return Description of the L-System rule
     */
    String getDescription();
}
