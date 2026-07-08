# Introduction to Nullables

A Learning Hour on replacing mocks with [Nullables], built around a small Library
kata in Java. This repository is organised as:

- A [Jekyll] documentation site configured with the [Just the Docs] theme, in the [`docs`](docs) folder
- One folder per language implementation of the kata — currently only [`java`](java)
- GitHub Actions workflows in [`.github/workflows`](.github/workflows) to build & test the code and to deploy the site to GitHub Pages
- An [MIT License](LICENSE) for the source code in the language folders
- A [CC BY-SA 4.0 License](LICENSE-CC-BY-SA-4.0) for the documentation site content, plus a [NOTICE](NOTICE) file for third-party attributions

## Repository structure

```
.
├── docs/     # Just the Docs site (deployed to GitHub Pages)
├── java/     # Java implementation of the kata (Maven, JUnit 5, AssertJ, Mockito)
└── .github/  # CI workflows and Dependabot config
```

## The kata

Start from the exercises described in [`java/README.md`](java/README.md): go from a
Mockito-based test of a legacy `Library` to Nullables, and finally give `Library`
its own `createNull()`. See the [`java`](java) folder for setup and how to run the tests.

## Documentation site

The [`docs`](docs) folder is a [Jekyll] site using the [Just the Docs] theme.

To build and preview it locally (with [Jekyll] and [Bundler] installed):

1. `cd docs`
2. `bundle install`
3. `bundle exec jekyll serve` and open `localhost:4000`

The built site is written to `docs/_site`. On push to `main`, the
`.github/workflows/pages.yml` workflow builds and deploys it to GitHub Pages
(enable it via Settings > Pages > Build and deployment > Source > GitHub Actions).

## GitHub Actions Workflows

- **Java Build & Test:** `.github/workflows/java-build-test.yml` builds and tests the Java project on pushes and pull requests touching `java/`. Test results are uploaded as artifacts.
- **Site Deployment:** `.github/workflows/pages.yml` builds and deploys the documentation site to GitHub Pages on pushes touching `docs/`.

## Acknowledgements

The Java kata was created from the
[introduction-to-nullables](https://github.com/lexler/introduction-to-nullables)
template by [lexler](https://github.com/lexler). The original does not include a
license. Thank you for making it available.

## Licensing and Attribution

- Source code in the language folders (e.g. `java/`) is licensed under the [MIT License](LICENSE), Copyright (c) 2026 Iván Fernández.
- Documentation content in the `docs/` folder is authored by Iván Fernández and licensed under the [Creative Commons Attribution-ShareAlike 4.0 International (CC BY-SA 4.0) License](LICENSE-CC-BY-SA-4.0).
- This repository uses the [Just the Docs] theme; a copy of its [MIT License] is included in `docs/LICENSE`.
- The deployment workflow is based on GitHub's [starter workflows], available under their MIT License.
- See [NOTICE](NOTICE) for the full third-party attribution details.

[Nullables]: https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks
[Jekyll]: https://jekyllrb.com
[Just the Docs]: https://just-the-docs.github.io/just-the-docs/
[Bundler]: https://bundler.io
[MIT License]: https://en.wikipedia.org/wiki/MIT_License
[starter workflows]: https://github.com/actions/starter-workflows/blob/main/pages/jekyll.yml
