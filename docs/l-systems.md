# L-Systems

Lindenmayer Systems (**L-Systems**) are a mathematical formalism originally developed to model the growth of plants. They are defined by an **alphabet of symbols**, an **axiom** (starting string), and a set of **production rules** that rewrite the string iteratively.

---

## 1. The L-System

### Definition
- **Alphabet**: `F`
- **Axiom (start)**: `F`
- **Production rule**: `F → F+F−F`

Each iteration applies the rule to every symbol in the current string.  
After a few iterations, this produces increasingly complex patterns.

---

## 2. Example: A Binary Tree

We can model a simple **binary tree** with an L-System:

- **Alphabet**: `0, F, [, ], +, -`
- **Axiom**: `0`
- **Production rules**:
    - `0 → F[+F0]-F0`
    - `F → FF`

### Meaning
- `F`: Draw forward.
- `+`: Turn right by an angle.
- `-`: Turn left by an angle.
- `[`: Push current state (position + orientation).
- `]`: Pop state (return to saved position + orientation).

When iterated and interpreted, this produces a branching binary tree structure.

---

## 3. Turtle Interpretation

To visualize L-systems, we use a **turtle graphics** approach:

- Imagine a "turtle" moving with a position and orientation.
- **Commands**:
  - `F`: Move forward and draw a line.
  - `+`: Rotate right by a fixed angle.
  - `-`: Rotate left by a fixed angle.
  - `[`: Save the current position and orientation on a stack.
  - `]`: Restore the last saved state from the stack.

This allows the system to create complex recursive structures like trees and plants.

---

## 4. Parameterized L-Systems

Basic L-systems treat symbols as **atomic** (just `F`, `+`, etc.).  
**Parameterized L-Systems** extend this idea by attaching **parameters** to symbols, making them more expressive.

### Example
- **Alphabet**: `F(x)`
- **Axiom**: `F(1)`
- **Production rules**:
  - `F(x) → F(x * 1.1)[+F(x * 0.7)][-F(x * 0.7)]`

### Explanation
- Here, `F(x)` means "draw forward with length `x`".
- At each iteration:
  - The trunk grows (`x * 1.1`).
  - Two smaller branches grow at different angles (`x * 0.7`).
- The result is a more **realistic branching structure** where branch sizes shrink naturally.

Parameterized L-systems make it possible to encode **growth dynamics**, **angles**, and **probabilistic variations** — leading to highly detailed plant models.

---

## 5. Code

Here we splitted the L-systems in fours parts:
- `/core`: holds the math
- `/core/impl`: implementation of some rules (like the Binary Tree, a Simple plant and Koch Snowflake)
- `/graphics`: holds the graphics (TurtleIntepreter and Renderer)
- `/models`: classes that holds are used to hold values like the Turtle's state

## 6. References

- Aristid Lindenmayer (1968). *Mathematical models for cellular interactions in development*.
- Prusinkiewicz & Lindenmayer (1990). *The Algorithmic Beauty of Plants*.  
    [Free online edition](http://algorithmicbotany.org/papers/abop/abop.pdf)

---
