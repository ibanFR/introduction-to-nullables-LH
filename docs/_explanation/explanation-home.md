---
title: What are Nullables?
nav_order: 1
---

# What are Nullables?
{: .no_toc }

A short explanation of the Nullables pattern and how it differs from mocks.
{: .fs-6 .fw-300 }

A **Nullable** is a real instance of your production class that has been told not
to talk to the outside world — no database, no network, no console, no wall
clock. You create one through a dedicated `createNull()` factory that swaps the
class's infrastructure for embedded, in-memory stand-ins while keeping all the
real logic intact.

Unlike a mock, a Nullable runs your actual code, so tests exercise real
behaviour instead of a hand-written imitation of it. You still get control (feed
it the state it should start from) and observability (track what it sent to the
outside world) through small, purpose-built seams — often an output tracker for
the things the code emits.

The exercises in this repository walk from a Mockito-based test of the `Library`
towards Nullables built for its `Loans`, `Printer`, and `Clock` dependencies, and
finally a `Library.createNull()` of its own.
