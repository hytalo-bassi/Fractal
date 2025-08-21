package core;

import static org.junit.jupiter.api.Assertions.*;

import core.impl.BinaryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LSystemEngineTest {

    private LSystemEngine engine;

    @BeforeEach
    void setUp() {
        engine = new LSystemEngine(new BinaryTree());
    }

    @Test
    @DisplayName("Should produce the right string after 3 iterations")
    void shoudProduceRightString() {
        // When
        String result = engine.generate(3);
        // Then
        assertEquals("FF[+FFF[+F0]-F0]-FFF[+F0]-F0", result);
    }

    @Test
    @DisplayName("Should produce the right parametric string after 2 iterations")
    void shoudProduceRightParametricString() {
        // When
        String result = engine.generate(2);
        // Then
        assertEquals("FF[+FFF[+F0]-F0]-FFF[+F0]-F0", result);
    }
}
