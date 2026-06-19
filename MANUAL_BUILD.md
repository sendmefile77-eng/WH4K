# WH4K Manual Build

This repository is configured for manual builds only. There is no automatic build on push or pull request.

## What is included now

- Android app module: `:app`.
- Core engine module: `:core`.
- Compose preview screen connected to core state and prompt assembly.
- ModelsLab image provider layer added in code, but API key is not configured yet.
- Example module manifest: `modules/example_grimdark/module.json`.
- Manual GitHub Actions workflow: `.github/workflows/manual-android-build.yml`.

## Build in GitHub web version

Use this if you do not have Android Studio locally.

1. Open the repository on GitHub.
2. Go to the **Actions** tab.
3. In the left sidebar, choose **Manual Android Build**.
4. Click **Run workflow**.
5. Keep branch as `main`.
6. Click the green **Run workflow** button.
7. Open the started workflow run and wait until it finishes.
8. At the bottom of the run page, download the artifact named:

   ```text
   wh4k-debug-apk
   ```

9. Unzip the artifact. The APK inside should be the debug build.

The workflow does not run automatically. It only runs when you press **Run workflow** manually.

## What the workflow does

The manual workflow:

1. checks out the repository;
2. installs JDK 17;
3. installs Android SDK packages for API 35;
4. sets up Gradle 8.7;
5. runs:

   ```bash
   gradle :app:assembleDebug --no-daemon --stacktrace
   ```

6. uploads the debug APK as a GitHub Actions artifact.

## Local requirements

Install locally only if you want to build outside GitHub:

1. Android Studio Ladybug or newer.
2. JDK 17.
3. Android SDK Platform 35.
4. Android Gradle Plugin 8.6.x support.

## Build with Android Studio

1. Clone the repository:

   ```bash
   git clone https://github.com/sendmefile77-eng/WH4K.git
   cd WH4K
   ```

2. Open the repository folder in Android Studio.
3. Wait for Gradle sync.
4. Select the `app` run configuration.
5. Run on an emulator or connected Android device.

## Build from terminal

This repository currently does not include Gradle Wrapper files (`gradlew`, `gradlew.bat`, `gradle/wrapper/...`).

Use one of these options:

### Option A: generate wrapper locally

From Android Studio terminal or a system Gradle installation:

```bash
gradle wrapper --gradle-version 8.7
./gradlew :app:assembleDebug
```

On Windows:

```powershell
gradle wrapper --gradle-version 8.7
.\gradlew.bat :app:assembleDebug
```

### Option B: use installed Gradle directly

```bash
gradle :app:assembleDebug
```

The debug APK should be produced at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Expected first screen

The first build should open a simple WH4K / Myauko Engine screen showing:

- active module id;
- current visit act and frame;
- generated prompt preview;
- note that the image provider layer is present but API key is not configured yet.

## ModelsLab is not enabled yet

Do not put API keys into Git.

The next step is to add a local-only configuration mechanism for ModelsLab:

- API key stored outside the repository;
- default model id;
- width and height;
- steps;
- guidance scale;
- safety checker setting;
- provider toggle.

## Troubleshooting

### GitHub Actions page does not show the workflow

Check that this file exists on `main`:

```text
.github/workflows/manual-android-build.yml
```

If the Actions tab is disabled, enable Actions in repository settings.

### Gradle sync cannot find version catalog

Check that this file exists:

```text
gradle/libs.versions.toml
```

### App cannot resolve `:core`

Check that `settings.gradle.kts` includes:

```kotlin
include(":app", ":core")
```

### JDK mismatch

Set the local Gradle JDK to JDK 17 if building outside GitHub.
