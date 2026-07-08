# Miro board — raw content extract

Source: [Introduction to Nullables board](https://miro.com/app/board/uXjVH9-3QtM=/)
by Lada Kesseler ([@lexler](https://github.com/lexler)).

This is a faithful, unrestructured dump of the board's frames, in board order.
For the facilitator-ready version see [`docs/index.md`](../docs/index.md). A
visual backup of the board is in
[`Introduction-to-Nullables-Miro-board.rtb`](./Introduction-to-Nullables-Miro-board.rtb).

---

## Frame: Introduction to Nullables (title)

- **Title:** Introduction to Nullables
- **Author:** Lada Kesseler
- **Learning objectives:**
  - Describe the difference between a Nullable and a mock
  - Use a ready-made Nullable to write a unit test
- **Contact:** LinkedIn (lada kesseler), GitHub (github.com/lexler)
- **License:** Creative Commons Share-alike (CC BY-SA)

## Frame: Connect

- Prompt: "Give an example of a dependency that makes tests **slow**, **flaky**,
  or **hard** to maintain." ("dependencies" highlighted in yellow; slow/flaky/hard
  in red.)
- Instruction: "Write your thoughts on notes."
- ~28 sticky notes. Dependency categories captured:
  - Network — HTTP clients, API calls, network connections, 3rd-party APIs
  - Persistence — databases, database access, file systems, file access
  - Time — Time / `Instant.now()`, date and time
  - State — stateful dependencies, anything with state, singleton pattern
  - External services — payment portals, GitHub actions, queues, LLMs
  - Randomness — random number generation
  - Complexity — heavy frameworks, huge classes with private methods, many
    dependencies, concurrency
  - Cost — API calls that cost money
  - Architecture — multi-service / multi-process dependencies
  - Testing tools — Mockito & reflection
  - Setup that cannot be determined at start

## Frame: How easy is it to test these? (2×2 matrix)

Axes: horizontal = **ownership** (Not Yours → Yours); vertical = **communication**
(Talks to outside → Self Contained).

| Quadrant                        | Sentiment |
| ------------------------------- | --------- |
| Not Yours / Talks to outside    | 😡        |
| Yours / Talks to outside        | 😬        |
| Not Yours / Self Contained      | 🙂        |
| Yours / Self Contained          | 😎        |

(There is a second, duplicate "How easy is it to test these?" frame on the board.)

## Frame: Concept — definition

- "**Nullables are production code with an 'OFF' switch.**"
- Two characteristics:
  - Real production code
  - Doesn't talk to the outside world
- Diagram: a central "Your system — runs for real" inside a boundary, with
  arrows to the "Outside world" blocked.

## Frame: Concept — create() / createNull()

Code snippet:

```
class ApplicationController {
    create()
    createNull()
}
```

Illustrates the Null Object / factory pattern: two construction paths for the
same object.

## Frame: Concept — create in normal mode

- `create()` highlighted (yellow): "instantiates your real code that talks to
  the outside world."
- Diagram: Controller → (create) → Client, inside a green "your system" boundary;
  Client → (network) → "Another System" (red, external).

## Frame: Concept — create in nulled mode

- `createNull()` highlighted (yellow).
- Text: "instantiates your real code" and "communication with outside is turned
  OFF."
- Diagram: same Controller/Client inside the boundary, but a red ✗ blocks the
  network link to the external "Another System."

## Frame: Coding Exercise 1 — Test with a Mock

- Header: "Let's try it with a Mock."
- Repo: https://github.com/lexler/introduction-to-nullables/tree/main
- Steps:
  1. Verify you can run the tests — one test should be failing.
  2. Open `README.md`.
  3. Make `_2_1_NullLoanTest` pass **without AI assistance** — 5-minute timebox,
     stop regardless of completion.

> Note: this repo's `java/README.md` maps Exercise 1 to `_1_MockedLoanTest` in
> the `legacy` folder (Mockito), and `_2_x_NullLoanTest` to Exercise 2.

## Frame: How did it feel?

Reflection checkpoint after Exercise 1 (collect reactions). A second "How did it
feel?" frame and a "Copy of How did it feel?" frame appear after the later
exercises.

## Frame: Coding Exercise 2 — Test with a Nullable

- Header: "Try it with a Nullable."
- Repo: https://github.com/lexler/introduction-to-nullables/tree/main
- Instruction: open `README.md` and follow Exercise 2. (Uses the Nullables that
  were created for you.)

## Frame: Demo — Creating a simple Nullable

- Facilitator demonstration of building a simple Nullable / `createNull()`.

## Frame: Coding Exercise 3 — Create Simple Nullable

- Header: "Practice."
- Repo: https://github.com/lexler/introduction-to-nullables/tree/main
- Steps:
  - Open `README.md`
  - Follow instructions for Exercise 3
  - Follow instructions for Exercise 4

## Frame: Conclusions

- Left sidebar questions:
  1. "If you had to explain nullables to a colleague, what would you say?"
  2. "What was the most interesting thing you learned today?"
- ~11 sticky notes. Recurring theme — a maturity model for Nullables:
  - Level 1: work but do nothing
  - Level 2: tracking added (observe)
  - Level 3: configurable behaviour (control)
- Also referenced: test doubles / fake objects, dependency breaking, factory
  patterns, and a linked blog post.

## Frame: Feedback

- Closing feedback collection frame.
