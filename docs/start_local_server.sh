#!/usr/bin/env bash
#
# Build and serve the documentation site locally with live reload.
# Preview at http://localhost:4000
#
# Requires Ruby and Bundler. From the repository root or anywhere, run:
#   ./docs/start_local_server.sh

set -euo pipefail

# Run from the directory containing this script (the docs site root).
cd "$(dirname "$0")"

if ! command -v bundle >/dev/null 2>&1; then
  echo "Error: Bundler is not installed. Install it with 'gem install bundler'." >&2
  exit 1
fi

# Only install when the locked dependencies aren't already satisfied.
if ! bundle check >/dev/null 2>&1; then
  echo "Installing gem dependencies..."
  bundle install
fi

echo "Serving site at http://localhost:4000 (Ctrl+C to stop)..."
bundle exec jekyll serve --livereload "$@"
