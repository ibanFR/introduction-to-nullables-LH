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

This session plan follows the 4C model (Connect, Concept, Concrete Practice,
Conclusions) and is based on the *Introduction to Nullables* [Miro board] by
[Lada Kesseler].

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Learning Goals 🎯

- Understand what a **Nullable** is and how it differs from a **mock**.
- Feel the friction of testing legacy code with mocks.
- Use ready-made Nullables to control and observe behaviour in unit tests.
- Give a class its own `createNull()` factory and use it in a test.

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

A Nullable is:

- **Real production code** — not a hand-written imitation of it.
- Code that **doesn't talk to the outside world** — the external communication
  is switched off; everything inside your system still runs for real.

Show the two factory methods side by side:

```java
class ApplicationController {
    // instantiates your real code, talks to the outside world
    static ApplicationController create() { /* ... */ }

    // same real code, external communication switched off
    static ApplicationController createNull() { /* ... */ }
}
```

`create()` is *normal mode*: the controller wires up a client that talks to
another system over the network. `createNull()` is *nulled mode*: the same
controller and client are created, but the connection to the outside world is
blocked at the boundary.

### Demo — creating a simple Nullable (optional)

If the group is new to the pattern, live-code a minimal `createNull()` on a
small class before the exercises. See [What are Nullables?]({% link _explanation/explanation-home.md %})
for the explanation to draw from.

## Concrete Practice (⏱️ 35 min)

Work in pairs or ensemble on the [`java/`]({{ site.repository }}/tree/main/java)
implementation of the Library kata. Setup instructions are in
[`java/README.md`]({{ site.repository }}/blob/main/java/README.md). Do the
exercises without AI assistance — the point is to feel the difference yourself.

### Exercise 1 — Test with a mock (⏱️ 5 min timebox)

- Open `java/README.md` and follow the instructions for **Exercise 1**
  (`_1_MockedLoanTest` in the `legacy` folder). One test is failing — make it
  pass with Mockito.
- Stop when the timebox ends, finished or not.
- **How did it feel?** How much of the time went into thinking about the test
  case versus wiring up the mocks? Rate it (😡 🙂 😬 😎) and jot a note.

### Exercise 2 — Test with a Nullable (⏱️ ~10 min)

- Follow the instructions for **Exercise 2**: Nullables have already been
  created for you in the `refactored` folder. Use them instead of Mockito in
  `_2_1_NullLoanTest`, `_2_2_NullLoanTest`, and `_2_3_NullLoanTest`.
- **How did it feel?** Compare with the mock version — same emoji rating, new
  note.

### Exercises 3 & 4 — Create a simple Nullable (⏱️ ~15 min)

- Follow the instructions for **Exercise 3**: `Library` needs a database, a
  printer, and the wall clock. Its dependencies are already Nullables — give
  `Library` its own `createNull()` (`_3_1_LibraryTest`).
- Continue with **Exercise 4**: control and observe your world — tell the
  Library what books are borrowed and check what reminders it prints
  (`_4_1_LibraryTest`, `_4_2_LibraryTest`).
- **How did it feel?** One last rating and note.

## Conclusions (⏱️ 5 min)

Two reflection prompts, answered on sticky notes and shared:

> **If you had to explain Nullables to a colleague, what would you say?**

> **What was the most interesting thing you learned today?**

Close by collecting feedback on the session itself — what worked, what to
change for next time.

## Attribution

This Learning Hour is based on the *Introduction to Nullables* [Miro board] and
[kata template] by [Lada Kesseler], shared under a Creative Commons
Attribution-ShareAlike license. See the [Samman Coaching] website for more on
the [Learning Hour] format and its [Connect Activities], [Concept Activities],
[Concrete Practice Activities], and [Conclusions Activities].


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
