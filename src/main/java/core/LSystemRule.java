package core;

import java.util.Map;
import java.util.function.Function;

// This is the simplest L-system rule set. To create more complex trees and structutures
// there will be another kind of rule that comes the parametrized D0L-System. The difference
// between each one is that the parDOL system can also receive 'modules' or 'functions' like:
// F(x) : x < 4 -> F(x+1)[+F(1)0]-F(1)0
//
// This module F(x) is rewritten to the part after '->' if the condition x < 4
// is satisfied. Otherwise it will be treated like a constante, so no more rewritings of this
// rule.
//
// It's also interpreted like the current 'F' symbol, it moves the turtle forward. But differently
// than the current interpretator is that the F(x) moves by x units of length.
//
// With parDOL systems we could generate even more difference between plants under the same rule
// using, for example, a PRNG to generate y length and passing it to F(y) or creating a theta
// and pass it to +(theta) or -(theta), creating variable length and angles. Mantaining the
// same plant structuture


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
