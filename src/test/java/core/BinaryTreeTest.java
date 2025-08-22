package core;

import static org.junit.jupiter.api.Assertions.*;

import core.impl.BinaryTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.LStringBuilder;

/**
 * Tests for BinaryTree L-System with parametric rules.
 * Parametric rule: F(x) -> F(x + 25*e^(-0.1*x))
 * Non-parametric rule: 0 -> F(10)[+F(5)0]-F(5)0
 */
class BinaryTreeTest {

    private LSystemEngine engine;
    private final LStringBuilder builder = new LStringBuilder();

    @BeforeEach
    void setUp() {
        engine = new LSystemEngine(new BinaryTree());
    }

    @Test
    @DisplayName("Should handle zero iterations correctly")
    void shouldHandleZeroIterations() {
        String result = engine.generate(0);

        assertEquals(builder.forward("12").leaf().build(), result);
    }

    @Test
    @DisplayName("Should handle one iteration correctly")
    void shouldHandleOneIteration() {
        // When
        String result = engine.generate(1);

        // Then
        assertEquals(
                builder.forward("19.53")
                        .forward("10")
                        .openBranch()
                        .turnLeft()
                        .forward("5")
                        .leaf()
                        .closeBranch()
                        .turnRight()
                        .forward("5")
                        .leaf()
                        .build(),
                result);
    }

    @Test
    @DisplayName("Should handle two iterations correctly")
    void shouldHandleTwoIteration() {
        // When
        String result = engine.generate(2);
        // Then
        assertEquals(
                builder.forward("23.08")
                        .forward("19.20")
                        .openBranch()
                        .turnLeft()
                        .forward("20.16")
                        .forward("10")
                        .openBranch()
                        .turnLeft()
                        .forward("5")
                        .leaf()
                        .closeBranch()
                        .turnRight()
                        .forward("5")
                        .leaf()
                        .closeBranch()
                        .turnRight()
                        .forward("20.16")
                        .forward("10")
                        .openBranch()
                        .turnLeft()
                        .forward("5")
                        .leaf()
                        .closeBranch()
                        .turnRight()
                        .forward("5")
                        .leaf()
                        .build(),
                result);
    }

    @Test
    @DisplayName("Should throw exception for negative iterations")
    void shouldThrowExceptionForNegativeIterations() {
        // When & Then
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> engine.generate(-1));
        assertEquals("Iterations must be non-negative", exception.getMessage());
    }
}
