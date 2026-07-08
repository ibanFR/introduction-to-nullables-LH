---
title: Home
layout: home
---

# {{ site.title }}
{: .no_toc }

{{ site.description }}.
{: .fs-6 .fw-300 }


Welcome to the **Introduction to Nullables** [Learning Hour], based on Lada
Kesseler's ([@lexler]) session. We take a small Library that lends books and
prints overdue reminders, and replace its mocks with [Nullables] — real
instances of your own code wired to run without talking to the outside world.

This page is the facilitator guide: run it top to bottom in about an hour. The
timings are the Samman four-phase rhythm (Connect → Concept → Concrete Practice
→ Conclusions).

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Learning Goals 🎯

- Describe the difference between a Nullable and a mock.
- Use a ready-made Nullable to write a unit test.
- Give a class its own `createNull()` factory and use it to control and observe
  its behaviour.

Along the way, participants should *feel the friction* of testing legacy code
with mocks before they see the alternative.

## Connect — dependencies that hurt (⏱️ 5 min)

Open the floor with the prompt:

> **Give an example of a dependency that makes tests _slow_, _flaky_, or _hard_
> to maintain.**

Ask everyone to write their thoughts on sticky notes (physical or on the board).
Cluster the answers as they come in. Expect categories like:

- **Network** — HTTP clients, API calls, 3rd-party APIs
- **Persistence** — databases, file systems
- **Time** — `Instant.now()`, dates, the wall clock
- **State** — singletons, anything stateful, concurrency
- **External services** — payment portals, queues, LLMs, CI actions
- **Randomness** — random number generation
- **Cost** — API calls that cost money
- **Testing tooling itself** — Mockito & reflection

Keep it short — the goal is to surface the shared pain, not solve it yet.

## Concept — Nullables are production code with an OFF switch (⏱️ 5 min)

### 1. How easy is it to test these?

Draw the 2×2 matrix and place the group's Connect examples in it:

|                     | **Not yours**            | **Yours**              |
| ------------------- | ------------------------ | ---------------------- |
| **Talks to outside**| 😡 hardest               | 😬 awkward             |
| **Self-contained**  | 🙂 fine                  | 😎 easiest             |

- **Axes:** _ownership_ (do you control the code?) × _communication_ (does it
  talk to the outside world?).
- The pain lives in the top-left: code you don't own that reaches outside.
- Nullables move your code toward the bottom-right 😎 — still yours, but no
  longer talking to the outside.

### 2. What a Nullable is

> **Nullables are production code with an "OFF" switch.**

- It runs your **real production code** — not a hand-written imitation.
- It **doesn't talk to the outside world** — no DB, network, console, or clock.

### 3. Two ways to build the same object

A class exposes two factories:

```
class ApplicationController {
    create()      // normal mode
    createNull()  // nulled mode
}
```

- **`create()` — normal mode:** instantiates your real code that *talks to the
  outside world* (Controller → Client → network → another system).
- **`createNull()` — nulled mode:** instantiates the *same* real code, but
  communication with the outside is turned **OFF** (the network hop is blocked).

The logic under test is identical in both modes; only the edge that leaves your
system changes.

See [What are Nullables?]({{ '/explanation/explanation-home.html' | relative_url }})
for a fuller written explanation.

## Concrete Practice — from Mockito to Nullables (⏱️ ~35 min)

All four exercises live in the [`java/`]({{ site.repository }}/tree/main/java)
implementation. Follow the comments in each test and make it pass. See
[Concrete Practice Activities] for facilitation ideas (mob/pair, driver-navigator).

Setup note: the code needs Java 17. The easy path is [mise] — `mise install`
then `mise run test`. See [`java/README.md`]({{ site.repository }}/tree/main/java#setup)
for alternatives.

### Exercise 1 — Test it with a Mock (⏱️ 5 min, no AI)

1. Run the tests — one should be failing.
2. Open `java/README.md`.
3. Make `_1_MockedLoanTest` (in the `legacy` folder) pass using Mockito.

**Timebox to 5 minutes and stop even if unfinished.** The point is to feel the
friction, not to finish.

Then discuss: **How did it feel?** (Collect quick reactions — verbose setup,
brittle expectations, testing the mock instead of the code…)

### Exercise 2 — Test it with a Nullable (⏱️ ~8 min)

The Nullables have already been created for you (`refactored` folder). Use them
instead of Mockito to pass:

- `_2_1_NullLoanTest`
- `_2_2_NullLoanTest`
- `_2_3_NullLoanTest`

Discuss again: **How did it feel** compared to Exercise 1?

### Demo — Creating a simple Nullable (⏱️ ~5 min)

Facilitator demo: show how a `createNull()` factory is built — swap the real
infrastructure for an embedded, in-memory stand-in while keeping the real logic.
This sets up participants to write their own.

### Exercises 3 & 4 — Create your own Nullable (⏱️ ~12 min)

1. Open `java/README.md` and follow **Exercise 3**: `Library` needs a database,
   a printer, and the wall clock. Its dependencies are already Nullables — give
   `Library` its own `createNull()`. File: `_3_1_LibraryTest`.
2. Then **Exercise 4**: control and observe your world — tell the `Library` what
   books are borrowed and check which reminders it prints. Files:
   `_4_1_LibraryTest`, `_4_2_LibraryTest`.

## Conclusions — reflect (⏱️ 5 min)

Close with two questions and collect answers on sticky notes:

1. **If you had to explain Nullables to a colleague, what would you say?**
2. **What was the most interesting thing you learned today?**

A useful frame that often emerges — the maturity levels of a Nullable:

- **Level 1** — it works but does nothing (no-op, keeps the code running).
- **Level 2** — tracking added (you can *observe* what it emitted).
- **Level 3** — configurable behaviour (you can *control* what it returns).

See [Conclusions Activities] for more ways to facilitate the reflection.

## Resources

- Raw content extracted from the source Miro board:
  [`board/board-content.md`]({{ site.repository }}/blob/main/board/board-content.md)
- James Shore, [Testing Without Mocks / Nullables][Nullables]
- Original kata: [introduction-to-nullables] by [@lexler]


[Learning Hour]: https://sammancoaching.org/reference/learning_hour_definition.html
[Nullables]: https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks
[Connect Activities]: https://sammancoaching.org/activities/connect.html
[Concept Activities]: https://sammancoaching.org/activities/concept.html
[Concrete Practice Activities]: https://sammancoaching.org/activities/concrete.html
[Conclusions Activities]: https://sammancoaching.org/activities/conclusions.html
[Samman Coaching]: https://sammancoaching.org/
[introduction-to-nullables]: https://github.com/lexler/introduction-to-nullables
[@lexler]: https://github.com/lexler
[mise]: https://mise.jdx.dev
