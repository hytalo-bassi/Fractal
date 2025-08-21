# Fractal

**Fractal** — some growing plants.

---

## How to Use

This project uses **Gradle** as its build system. Here are the most common commands:

- **Run the project**
```bash
./gradlew run
```

- **Run tests**
```bash
./gradlew test
```
- **Check code formatting**
```bash
./gradlew spotlessCheck
```

- **Automatically fix code formatting**
```bash
./gradlew spotlessApply
```

## Coding Conventions

We want our codebase to be readable, consistent, and maintainable. Please follow these guidelines:

### Code Style

- Prioritize readability over clever tricks.

- Write easy-to-follow logic.

- Strive for optimized but clear code.

- Use English for all variable names, class names, and methods.

- Write comments only when necessary (the code should explain itself).

### Git Conventions

Follow Conventional Commits:
- `feat`: – new feature
- `fix`: – bug fixes
- `docs`: – documentation changes
- `style`: – formatting changes (no code logic impact)
- `refactor`: – code refactoring
- `test`: – adding or updating tests
- `chore`: – maintenance tasks

Example:
```
feat: add support for interpreting parameters in L-system strings

The L-system interpreter can now parse and evaluate parameters embedded
in the production rules (e.g., F(10), A(theta)). This enables more
flexible modeling of growth patterns and parameterized plant structures.
```

Also remember to:
- Keep commits small and focused.
- Use clear commit messages that describe what and why, not just how.
