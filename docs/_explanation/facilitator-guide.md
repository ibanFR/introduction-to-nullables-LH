---
title: Facilitator Guide
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

1. Understand what a **Nullable** is and how it differs from a **mock**.
2. Use a ready-made Nullable to write a unit test.
3. Give a class its own `createNull()` factory and use it to control and observe
  its behaviour.

Along the way, participants should *feel the friction* of testing legacy code
with mocks before they see the alternative.

This is a **taste**, not a deep dive — Nullables are a tricky technique and one
hour won't cover everything. The aim is that people leave able to feel the
difference from a mock and to write a test against a ready-made Nullable.

**Format:** run the coding parts in pairs (breakout rooms work well online).
Make sure each pair has a driver comfortable with the language — the sample is
Java only. Timebox each exercise to ~5 minutes plus a little setup time, and
have people return to the main room (or signal) when done.

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
- **Not yours, self-contained** 🙂 — a money/currency library, `Math`, most pure
  packages. Not yours, but painless: no side effects, no cost.
- **Yours, talks to outside** 😬 — your own code hitting a DB or the network.
  Harder, but at least you control it ("your chance to shoot yourself in the leg").
- **Not yours, talks to outside** 😡 — the worst, and especially bad as a direct
  dependency: messy and hard.
- Nullables move your code toward the bottom-right 😎 — still yours, but no
  longer talking to the outside.

### 2. What a Nullable is

The technique comes from **James Shore** ([Testing Without Mocks][Nullables]).
His one-line definition:

> **Nullables are production code with an "OFF" switch.**

You tell the whole system "start in real mode" or "start in a mode where you
don't talk to the outside".

- It runs your **real production code** — not a hand-written imitation.
- It **doesn't talk to the outside world** — no DB, network, console, or clock.

At first glance a Nullable *looks* like a test double, but it isn't one: it's
real code with real usages, wired to skip the outside world.

### 3. Two ways to build the same object

A class exposes two factories:

```
class ApplicationController {
    create()      // normal mode
    createNull()  // nulled mode
}
```

- **`create()` — normal mode:** instantiates your real code that wires up a client that talks to another system over 
  the network (Controller → Client → network → another system).
- **`createNull()` — nulled mode:** instantiates the *same* real code, but
  communication with the outside is turned **OFF** (the network hop is blocked).

The logic under test is identical in both modes; only the edge that leaves your
system changes. `createNull()` **cascades**: it tells each direct dependency to
turn its outside conversation off, and that chains all the way down — so you
don't pay a big setup cost at the top.

### 4. How it differs from hexagonal + fakes

- **Hexagonal** reverses the usual "everything depends on the database"
  dependency — but it forces you to restructure the whole system up front and is
  hard to adopt incrementally.
- With hexagonal you often test against a **fake** adapter, so the adapter itself
  goes untested. (Emily Bache's Learning Hour on **narrow integration tests**
  covers that seam.)
- **Nullables** adopt incrementally, mix with existing code, and keep tests
  flexible. Following James Shore, you run *all* your real code, find the first
  piece that talks to the outside, and replace just that with an **embedded stub**
  that lives in the production code.
- The **trade-off**: that stub is test-support code shipped in production. It
  feels wrong at first, but it has real usages and buys you tests against real
  behaviour.

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

Then debrief with a sharper question than "how did it feel?":

> **How much of those 5 minutes did you actually spend thinking about the test
> case** — the scenario you were trying to verify?

Rate it (😡 🙂 😬 😎) and jot a note.

With mocks the honest answer is often "zero": all the time went into setup, and
you end up testing the mock rather than the code. (One participant ran the test,
saw it pass, and asked "what are we even testing?")

### Exercise 2 — Test it with a Nullable (⏱️ ~8 min)

The Nullables have already been created for you (`refactored` folder) — frame it
as "a colleague refactored the system to Nullables while you were away." Use them
instead of Mockito to pass:

- `_2_1_NullLoanTest` — start with the simplest scenario: an empty library sends
  no reminders (nobody borrowed a book, so nobody gets reminded).
- `_2_2_NullLoanTest` — a book borrowed but not yet due isn't overdue.
- `_2_3_NullLoanTest` — from a mix of loans, only the past-due books come back.

Debrief with the **same** question — how much of the 5 minutes went into the test
case itself? This time expect "it was easy", "nice to read", "easy to concentrate
on the business logic".

A strong facilitator move: when a test goes green, **deliberately break the production code** to show the test fails.
This proves the Nullable runs real code, not a stand-in — one participant did exactly this to convince himself
`createNull` was trustworthy ("it's too green for my taste, let's break it").

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

> **Setup cost, honestly:** low-level infra wrappers (e.g. a JDBC wrapper) are
> painful to write, but you write them once and reuse them across the whole
> codebase/tech stack — *invest once, use forever*. Higher-level Nullables built
> on top are cheap.

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
