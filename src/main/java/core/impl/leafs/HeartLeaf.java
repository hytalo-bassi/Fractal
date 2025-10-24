package core.impl.leafs;

import java.awt.geom.Path2D;

import core.BaseLeaf;

/**
 * Implements a heart-shaped leaf geometry for L-System plant visualizations.
 *
 * @author Hytalo Bassi
 * @version 1.0
 * @since 1.0
 * @see BaseLeaf
 * @see java.awt.geom.Path2D
 */
public class HeartLeaf extends BaseLeaf {
    public HeartLeaf(double stemLength, double width, double size) {
        super(stemLength, width, size);
    }

    protected Path2D.Double leafPath() {
        Path2D.Double heart = new Path2D.Double();

        // Start at the bottom point of the heart
        heart.moveTo(0, 0);
            
        // Left side of the heart
        heart.curveTo(
            -width * 0.5, -size * 0.3,        // Control point 1 (outward curve)
            -width * 0.9, -size * 0.6,        // Control point 2 (reaching left lobe)
            -width * 0.6, -size * 0.95        // End at top of left lobe
        );
            
        // Left top curve (the rounded lobe)
        heart.curveTo(
            -width * 0.4, -size * 1.15,       // Control point 1 (top of left lobe)
            -width * 0.15, -size * 1.1,       // Control point 2 (approaching center)
            0, -size * 0.85                    // End at center cleft
        );
            
        // Right top curve (the rounded lobe)
        heart.curveTo(
            width * 0.15, -size * 1.1,        // Control point 1 (leaving center)
            width * 0.4, -size * 1.15,        // Control point 2 (top of right lobe)
            width * 0.6, -size * 0.95         // End at top of right lobe
        );
            
        // Right side of the heart
        heart.curveTo(
            width * 0.9, -size * 0.6,         // Control point 1 (right lobe curve)
            width * 0.5, -size * 0.3,         // Control point 2 (inward curve)
            0, 0                               // Back to bottom point
        );
            
        heart.closePath();
        return heart;
    }
}
