# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Android app "Atom Journey" — single-module Jetpack Compose project. Application/package id is `com.vfg.silkroad.goo` (note the mismatch with the project display name; do not "fix" it). At the time of writing, both Activities are empty Compose scaffolds — this is a greenfield project, not a partial rewrite.

## Build / Test

The Gradle wrapper is at the repo root. All commands run from `/Users/artem/AndroidStudioProjects/AtomJourney`.

- Build debug APK: `./gradlew :app:assembleDebug`
- Install on a connected device/emulator: `./gradlew :app:installDebug`
- Unit tests (JVM): `./gradlew :app:testDebugUnitTest`
- Single unit test: `./gradlew :app:testDebugUnitTest --tests "com.vfg.silkroad.goo.ExampleUnitTest.addition_isCorrect"`
- Instrumented tests (needs device/emulator): `./gradlew :app:connectedDebugAndroidTest`
- Lint: `./gradlew :app:lintDebug` (HTML report under `app/build/reports/lint-results-debug.html`)
- Clean: `./gradlew clean`

`local.properties` (gitignored) holds the SDK path; do not check it in.

## Toolchain

- AGP `9.1.1`, Kotlin `2.2.10`, Compose BOM `2026.02.01`, Material3.
- Java/Kotlin source/target compatibility: **Java 11**.
- `compileSdk = 36` (with `minorApiLevel = 1` — uses the new AGP 9 `compileSdk { version = release(...) }` DSL, not the older `compileSdk = 36` form), `minSdk = 28`, `targetSdk = 36`.
- Dependencies are managed via the version catalog at `gradle/libs.versions.toml`. Add new libraries there (under `[libraries]` / `[versions]`) and reference them as `libs.xxx` in `app/build.gradle.kts` rather than hard-coding coordinates.
- Settings enforce `RepositoriesMode.FAIL_ON_PROJECT_REPOS` — repositories must be declared centrally in `settings.gradle.kts`, not in module build files.

## Architecture

- **Launcher entry point is `LoadingActivity`**, not `MainActivity` (see `AndroidManifest.xml` — `LoadingActivity` owns the `MAIN`/`LAUNCHER` intent filter and is `exported=true`; `MainActivity` is `exported=false`). Treat `LoadingActivity` as the splash/bootstrap screen and `MainActivity` as the post-load destination.
- UI is 100% Jetpack Compose (`buildFeatures.compose = true`, no View XML layouts under `res/layout`). Each Activity calls `enableEdgeToEdge()` then `setContent { AtomJourneyTheme { ... } }`.
- Theme lives in `app/src/main/java/com/vfg/silkroad/goo/ui/theme/` (`Theme.kt`, `Color.kt`, `Type.kt`). `AtomJourneyTheme` opts into Android 12+ dynamic color by default — light/dark `ColorScheme` constants only kick in on older devices or when `dynamicColor = false` is passed explicitly.
- A custom font is bundled at `res/font/font.ttf` and is intended to be wired into `Typography` in `Type.kt`.
