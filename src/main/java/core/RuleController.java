package core;

import core.impl.BinaryTree;
import core.impl.KochSnowflake;
import core.impl.SimplePlant;
import core.impl.StochasticBinaryTree;
import java.util.ArrayList;

public class RuleController {
    private ArrayList<LSystemRule> rules;
    private int currentIndex = 0;

    /**
     * Constructs a new RuleController and initializes it with default L-System rules.
     * The controller is pre-loaded with Binary Tree, Simple Plant, and Koch Snowflake rules.
     * The current index is set to 0 (BinaryTree) by default.
     */
    public RuleController() {
        rules = new ArrayList<>();

        registerRule(new BinaryTree());
        registerRule(new SimplePlant());
        registerRule(new KochSnowflake());
        registerRule(new StochasticBinaryTree());
    }

    /**
     * Shifts the current selection to the next rule in the collection.
     * If the current rule is the last one, it cycles back to the first rule.
     *
     * @return this for method chaining (e.g. shifRight().getRule())
     */
    public RuleController shiftRight() {
        currentIndex = (currentIndex + 1) % rules.size();

        return this;
    }

    /**
     * Shifts the current selection to the previous rule in the collection.
     * If the current rule is the first one, it cycles to the last rule.
     *
     * @return this for method chaining (e.g. shifLeft().getRule())
     */
    public RuleController shiftLeft() {
        currentIndex = (currentIndex - 1 + rules.size()) % rules.size();

        return this;
    }

    /**
     * Retrieves the currently selected L-System rule.
     *
     * @return the L-System rule at the current index
     * @throws IndexOutOfBoundsException if the current index is invalid
     */
    public LSystemRule getRule() {
        return rules.get(currentIndex);
    }

    /**
     * Registers a new L-System rule with the controller by adding it to the
     * internal rules collection.
     *
     * @param rule the L-System rule to be registered; must not be null
     */
    private void registerRule(LSystemRule rule) {
        if (rule != null) rules.add(rule);
    }
}
