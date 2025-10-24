package core.impl.leafs;

import core.BaseLeaf;

/**
 * Implements a simple leaf geometry for L-System plant visualizations.
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see BaseLeaf
 * @see java.awt.geom.Path2D
 */
public class SimpleLeaf extends BaseLeaf {
    /**
     * Creates a new SimpleLeaf with the specified width, size and stem's length.
     * 
     * @param stemLength The length of the leaf stem
     * @param width The leaf's width
     * @param size The leaf's path cache
     */
    public SimpleLeaf(double stemLength, double width, double size) {
        super(stemLength, width, size);
    }
}
