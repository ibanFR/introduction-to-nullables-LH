# Library Kata

## Scenario
The town library lends books and prints reminder letters for the overdue ones.
It's legacy code, and you're asked to write unit tests for it.

Follow the comments, make the tests pass

## Exercise 1 — Test it with Mockito
file: `_1_MockedLoanTest` (`legacy` folder)
(timebox to 5 minutes)

## Exercise 2 — Test it with a Nullable
You now have nullables that were created for you (`refactored` folder).
Use them instead of mockito

file: `_2_1_NullLoanTest`
file: `_2_2_NullLoanTest`
file: `_2_3_NullLoanTest`

## Exercise 3 — Make Library Nullable
`Library` needs a database, a printer, and the wall clock.
Its dependencies are already Nullables — give `Library` its own `createNull()`.

file: `_3_1_LibraryTest`

## Exercise 4 — Control and Observe your World
Tell the Library what books are borrowed and check what reminders it prints. 

file: `_4_1_LibraryTest`
file: `_4_2_LibraryTest`

---

## Setup

Needs Java 17. The easy path is [mise](https://mise.jdx.dev) — it reads
`mise.toml` and installs the right Java for you.

Install mise:
- macOS: `brew install mise`
- Linux/macOS: `curl https://mise.run | sh`
- Windows: `winget install jdx.mise`

Restart your shell, then in the project folder:

    mise install     # provisions Java 17
    mise run test    # runs the tests

`mise run doctor` prints the Java and Maven versions.

No mise? Install Temurin/OpenJDK 17 yourself (SDKMAN, `brew install temurin@17`,
or https://adoptium.net), then run `./test.sh` (Windows: `test.cmd`).

---

## Acknowledgements

This repository was created from the
[introduction-to-nullables](https://github.com/lexler/introduction-to-nullables)
template by [lexler](https://github.com/lexler). Thank you for making it
available. The original does not include a license.

This copy is released under the [MIT License](LICENSE),
Copyright (c) 2026 Iván Fernández.
