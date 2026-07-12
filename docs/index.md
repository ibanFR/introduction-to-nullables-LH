---
title: Home
layout: home
---

# {{ site.title }}
{: .no_toc }

{{ site.description }}.
{: .fs-6 .fw-300 }


Welcome to the **Introduction to Nullables** [Learning Hour]. We take a small
Library that lends books and prints overdue reminders, and replace its mocks
with [Nullables] — real instances of your own code wired to run without talking
to the outside world.

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Learning Goals 🎯

1. Understand what a **Nullable** is and how it differs from a **mock**.
2. Use ready-made Nullables to control and observe behaviour in unit tests.
3. Give a class its own `createNull()` factory and use it to control and observe
  its behaviour.

## Connect (⏱️ 5 min)

Ask the group:

> **What are examples of dependencies that have caused you problems in tests —
> making them slow, flaky, or hard to maintain?**

Have everyone write their answers on sticky notes (databases, clocks, file
systems, third-party APIs, message queues…) and share a few out loud. This
surfaces the pain that Nullables address and connects the topic to the team's
own codebase.

## Concept (⏱️ 10 min)

### How easy is it to test these?

Draw a 2×2 matrix with **ownership** on the horizontal axis (*not yours* →
*yours*) and **communication** on the vertical axis (*self-contained* → *talks
to the outside*):

| | Not yours | Yours |
| --- | --- | --- |
| **Talks to the outside** | 😡 | 😬 |
| **Self-contained** | 🙂 | 😎 |

Place the dependencies from the Connect activity on the matrix. The insight to
land: **talking to the outside is what makes testing painful**, whether the
code is yours or not.

### Nullables: production code with an OFF switch

The technique comes from **James Shore** ([Testing Without Mocks][Nullables]).
His one-line definition:

> **Nullables are production code with an "OFF" switch.**

- **Real production code** — not a hand-written imitation of it.
- Code that **doesn't talk to the outside world** — the external communication
  is switched off; everything inside your system still runs for real.

### Two ways to build the same object

Show the two factory methods side by side:

```java
class ApplicationController {
    // instantiates your real code, talks to the outside world
    static ApplicationController create() { /* ... */ }

    // same real code, external communication switched off
    static ApplicationController createNull() { /* ... */ }
}
```

- **`create()` — normal mode:** instantiates your real code that wires up a client that talks to another system over
  the network (Controller → Client → network → another system).
- **`createNull()` — nulled mode:** instantiates the *same* real code, but
  communication with the outside is turned **OFF** (the network hop is blocked).

## Concrete Practice — from Mockito to Nullables (⏱️ 35 min)

All four exercises live in the [`java/`]({{ site.repository }}/tree/main/java)
implementation. Follow the comments in each test and make it pass.

Work in pairs or ensemble on the [`java/`]({{ site.repository }}/tree/main/java)
implementation of the Library kata. Setup instructions are in
[`java/README.md`]({{ site.repository }}/blob/main/java/README.md).

### Demo — creating a simple Nullable (optional)

If the group is new to the pattern, live-code a minimal `createNull()` on a
small class before the exercises. See [What are Nullables?]({% link _explanation/explanation-home.md %})
for the explanation to draw from.

### Exercise 1 — Test with a mock (⏱️ 5 min timebox)

1. Run the tests — one should be failing.
2. Open `java/README.md`.
3. Make `_1_MockedLoanTest` (in the `legacy` folder) pass using Mockito.

**Timebox to 5 minutes and stop even if unfinished.** The point is to feel the
friction, not to finish.

Then debrief with a sharper question than "how did it feel?":

> **How much of those 5 minutes did you actually spend thinking about the test
> case** — the scenario you were trying to verify?

Rate it (😡 🙂 😬 😎) and jot a note.

### Exercise 2 — Test with a Nullable (⏱️ ~10 min)

The Nullables have already been created for you (`refactored` folder) — frame it
as "a colleague refactored the system to Nullables while you were away." Use them
instead of Mockito to pass:

- `_2_1_NullLoanTest` — start with the simplest scenario: an empty library sends
  no reminders (nobody borrowed a book, so nobody gets reminded).
- `_2_2_NullLoanTest`
- `_2_3_NullLoanTest`

Debrief with the **same** question — how much of the 5 minutes went into the test
case itself? This time expect "it was easy", "nice to read", "easy to concentrate
on the business logic".

### Exercises 3 & 4 — Create a simple Nullable (⏱️ ~15 min)

1. Open `java/README.md` and follow **Exercise 3**: `Library` needs a database,
   a printer, and the wall clock. Its dependencies are already Nullables — give
   `Library` its own `createNull()`. File: `_3_1_LibraryTest`.
2. Then **Exercise 4**: control and observe your world — tell the `Library` what
   books are borrowed and check which reminders it prints. Files:
   `_4_1_LibraryTest`, `_4_2_LibraryTest`.

> **How did it feel?** One last rating and note.

## Conclusions (⏱️ 5 min)

Close with two questions and collect answers on sticky notes:

> **If you had to explain Nullables to a colleague, what would you say?**

> **What was the most interesting thing you learned today?**

Close by collecting feedback on the session itself — what worked, what to
change for next time.

## Attribution

This Learning Hour is based on the *Introduction to Nullables* [Miro board] and
[kata template] by [Lada Kesseler], shared under a Creative Commons
Attribution-ShareAlike license.


[Learning Hour]: https://sammancoaching.org/reference/learning_hour_definition.html
[Nullables]: https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks
[Miro board]: https://miro.com/app/board/uXjVH9CcfFM=/
[kata template]: https://github.com/lexler/introduction-to-nullables
[Lada Kesseler]: https://github.com/lexler
[Connect Activities]: https://sammancoaching.org/activities/connect.html
[Concept Activities]: https://sammancoaching.org/activities/concept.html
[Concrete Practice Activities]: https://sammancoaching.org/activities/concrete.html
[Conclusions Activities]: https://sammancoaching.org/activities/conclusions.html
[Samman Coaching]: https://sammancoaching.org/
