package core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import core.impl.BinaryTree;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class LSystemEngineTest {
    
    private LSystemEngine engine;
    
    @BeforeEach
    void setUp() {
        engine = new LSystemEngine(new BinaryTree());
    }
    
    @Test
    @DisplayName("Should produce the right string after 2 iterations")
    void shoudProduceRightString() {
        // When
        String result = engine.generate(2);
        // Then
        assertEquals("FF[+FFF[+F0]-F0]-FFF[+F0]-F0", result);
    }
}
