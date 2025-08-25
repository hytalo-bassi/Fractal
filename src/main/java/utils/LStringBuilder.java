package utils;

/**
 * A builder class for constructing L-system strings using method chaining.
 */
public class LStringBuilder {
    private StringBuilder currentString;

    /**
     * Constructs a new LStringBuilder with an empty string buffer.
     */
    public LStringBuilder() {
        currentString = new StringBuilder();
    }

    /**
     * Appends a single symbol to the L-system string.
     *
     * @param symbol the character symbol to append
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder symbol(Character symbol) {
        currentString.append(symbol);
        return this;
    }

    /**
     * Appends a symbol with parameters to the L-system string.
     * The parameters are formatted as comma-separated values enclosed in parentheses.
     * If no parameters are provided, this method behaves like symbol(Character).
     *
     * @param symbol the character symbol to append
     * @param params array of parameter strings to include with the symbol
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder symbol(Character symbol, String... params) {
        if (params == null || params.length == 0) {
            return symbol(symbol);
        }

        String formattedParams = String.join(",", params);
        currentString.append(symbol).append("(").append(formattedParams).append(")");

        return this;
    }

    /**
     * Appends a branch symbol to the L-system string.
     * Branch symbols are used to save and restore turtle state in L-system interpretation.
     *
     * @param close true to append closing branch ']', false to append opening branch '['
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder branch(boolean close) {
        currentString.append(close ? "]" : "[");
        return this;
    }

    /**
     * Appends a forward movement symbol 'F' to the L-system string.
     * This represents moving forward and drawing a line in turtle graphics.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder forward() {
        return symbol('F');
    }

    /**
     * Appends a forward movement symbol 'F' with a distance parameter to the L-system string.
     * This represents moving forward by a specified distance and drawing a line in turtle graphics.
     *
     * @param distance the distance parameter as a string
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder forward(String distance) {
        return symbol('F', new String[] {distance});
    }

    /**
     * Appends the leaf symbol '0' to the L-system string.
     * This represents an actual leaf.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder leaf() {
        return symbol('0');
    }

    /**
     * Appends a left turn symbol '+' to the L-system string.
     * This represents turning the turtle left by a predefined angle.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder turnLeft() {
        return symbol('+');
    }

    /**
     * Appends a right turn symbol '-' to the L-system string.
     * This represents turning the turtle right by a predefined angle.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder turnRight() {
        return symbol('-');
    }

    /**
     * Appends an opening branch symbol '[' to the L-system string.
     * This saves the current turtle state (position and angle) onto a stack.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder openBranch() {
        return branch(false);
    }

    /**
     * Appends a closing branch symbol ']' to the L-system string.
     * This restores the turtle state (position and angle) from the stack.
     *
     * @return this LStringBuilder instance for method chaining
     */
    public LStringBuilder closeBranch() {
        return branch(true);
    }

    /**
     * Resets the String buffer.
     *
     */
    private void clear() {
        currentString.delete(0, currentString.length());
    }

    /**
     * Builds and returns the final L-system string. The string buffer is cleared after
     * built.
     *
     * @return the constructed L-system string
     */
    public String build() {
        String res = currentString.toString();
        clear();

        return res;
    }
}
