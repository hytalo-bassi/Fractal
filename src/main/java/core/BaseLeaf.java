package core;

import java.awt.geom.Path2D;

/**
 * Represents an abstract base class for leaf geometries in L-System plant visualizations.
 * <p>
 * This class provides a foundation for creating leaf shapes with customizable dimensions
 * including stem length, width, and overall size. It implements a caching mechanism to
 * optimize performance by storing generated leaf paths and only regenerating them when
 * properties are modified.
 * </p>
 * <p>
 * The default leaf shape is created using Bézier curves to produce smooth, organic-looking
 * edges with bilateral symmetry. Subclasses can override the {@link #leafPath()} method
 * to create custom leaf geometries while still benefiting from the caching infrastructure.
 * </p>
 * <p>
 * <strong>Key Features:</strong>
 * <ul>
 * <li><strong>Parametric Design</strong> - Leaf shape defined by three parameters: stem length, width, and size</li>
 * <li><strong>Path Caching</strong> - Automatic caching of generated paths for improved performance</li>
 * <li><strong>Manual Cache Control</strong> - Explicit cache invalidation when properties are modified</li>
 * <li><strong>Extensibility</strong> - Abstract design allows for custom leaf shape implementations</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Usage Example:</strong>
 * <pre>
 * BaseLeaf leaf = new ConcreteLeaf(10.0, 5.0, 15.0);
 * Path2D.Double path = leaf.getLeafPath(); // First call generates and caches
 * 
 * leaf.setWidth(7.0);
 * leaf.invalidateCache(); // Required after property change
 * path = leaf.getLeafPath(); // Returns updated path
 * </pre>
 * </p>
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see java.awt.geom.Path2D
 * @see core.impl.leafs.HeartLeaf
 * @see core.impl.leafs.SimpleLeaf
 */
public abstract class BaseLeaf {
    /* The length of the leaf stem. */
    protected double stemLength;

    /* The leaf's width. */
    protected double width;
    
    /* The overall length of the leaf body (excluding stem). */
    protected double size;

    /* The leaf's path cache */
    protected Path2D.Double cachedLeafPath = null;

    /**
     * Creates a new BaseLeaf with the specified width, size and stem's length.
     * 
     * @param stemLength The length of the leaf stem
     * @param width The leaf's width
     * @param size The leaf's path cache
     */
    public BaseLeaf(double stemLength, double width, double size) {
        this.stemLength = stemLength;
        this.width = width;
        this.size = size;
    }

    /**
     * Returns the leaf's width
     * 
     * @return double
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Returns the leaf's size
     * 
     * @return double
     */
    public double getSize() {
        return size;
    }

    /**
     * Returns the stem's length
     * 
     * @return double
     */
    public double getStemLength() {
        return stemLength;
    }

    /**
     * Sets the stem's length
     * 
     * <p>
     * This method sets the stem's length property, but do not regenerate the leaf's path.
     * After calling this method, it's necessary to invalidate the cache by calling {@link #invalidateCache()}
     * in order to use the leaf's path with the desired stem's length.
     * </p>
     * 
     * @param stemLength the stem's length
     */
    public void setStemLength(double stemLength) {
        this.stemLength = stemLength;
    }

    /**
     * Sets the leaf's width
     * 
     * <p>
     * This method sets the leaf's width property, but do not regenerate the leaf's path.
     * After calling this method, it's necessary to invalidate the cache by calling {@link #invalidateCache()}
     * in order to use the leaf's path with the desired leaf's width.
     * </p>
     * 
     * @param width the leaf's width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Sets the leaf's size
     * 
     * <p>
     * This method sets the leaf's size property, but do not regenerate the leaf's path.
     * After calling this method, it's necessary to invalidate the cache by calling {@link #invalidateCache()}
     * in order to use the leaf's path with the desired leaf's size.
     * </p>
     * 
     * @param size the leaf's size
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Invalidates the cached leaf's path.
     * 
     * <p>
     * This method regenerates the cached leaf's path by setting it to
     * the return value of {@link #leafPath()}. Useful after updating any leaf's properties. 
     * </p>
     */
    public void invalidateCache() {
        cachedLeafPath = leafPath();
    }

    /**
     * Returns a cached version of leaf path.
     * <p>
     * This method checks if a the leaf path have already been cached, if it have
     * been cached returns the cached leaf path, if not it will generate a leaf path,
     * saving it to the cache for future uses, and return the cached path. 
     * </p>
     *
     * @return a Path2D representing the leaf geometry
     */
    public Path2D.Double getLeafPath() {
        if (cachedLeafPath == null) {
            cachedLeafPath = leafPath();
        }

        return cachedLeafPath;
    }

    /**
     * Creates a leaf-shaped path geometry with the specified dimensions.
     * <p>
     * This method generates a stylized leaf shape using Bézier curves to create
     * smooth, organic-looking edges. The leaf consists of a stem and a body with
     * bilateral symmetry around the vertical axis.
     * </p>
     *
     * @return a Path2D representing the leaf geometry
     */
    protected Path2D.Double leafPath() {
        Path2D.Double leaf = new Path2D.Double();

        leaf.moveTo(0, 0);

        leaf.lineTo(0, -stemLength);

        leaf.curveTo(
                -width * 0.3,
                -stemLength - size * 0.2, // Control point 1
                -width,
                -stemLength - size * 0.6, // Control point 2
                -width * 0.7,
                -stemLength - size);

        leaf.curveTo(
                -width * 0.3, -stemLength - size * 1.1, // Control point 1
                width * 0.3, -stemLength - size * 1.1, // Control point 2
                width * 0.7, -stemLength - size);

        leaf.curveTo(
                width,
                -stemLength - size * 0.6, // Control point 1
                width * 0.3,
                -stemLength - size * 0.2, // Control point 2
                0,
                -stemLength);

        leaf.lineTo(0, 0);

        leaf.closePath();
        return leaf;
    }
}
