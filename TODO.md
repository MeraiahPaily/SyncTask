# TODO: Fix Gradle Build Issues in SyncTask Project

## Issues Identified
1. **Missing Library Versions**: `libs.versions.toml` was missing versions for Room, Hilt, and Lifecycle libraries, causing unresolved references.
2. **Kotlin Version Compatibility**: Kotlin version 2.0.21 was incompatible with KSP 1.9.22-1.0.17, requiring downgrade to 1.9.22.
3. **Compile SDK Version**: Compile SDK and target SDK were set to 34, but dependencies required at least 36.
4. **Google Services Plugin**: Plugin was enabled but `google-services.json` file was missing, causing build failures.
5. **Gradle Daemon and Configuration**: Build hangs during configuration phase with default settings.

## Fixes Applied
- [x] Added missing library versions in `libs.versions.toml` (Room 2.6.1, Hilt 2.51, Lifecycle 2.8.4).
- [x] Downgraded Kotlin version from 2.0.21 to 1.9.22 for KSP compatibility.
- [x] Updated compileSdk and targetSdk from 34 to 36 to meet dependency requirements.
- [x] Commented out `com.google.gms.google-services` plugin in `app/build.gradle.kts` due to missing `google-services.json`.
- [x] Enabled Gradle daemon and disabled configure on demand in `gradle.properties` to resolve build hangs.

## Remaining Issues
- Build still hangs during execution phase, likely due to dependency resolution or network issues.
- Need to add `google-services.json` for Firebase integration or remove Firebase dependencies if not needed.

## Next Steps
1. Investigate and resolve build hangs during execution phase.
2. Add `google-services.json` file for Firebase services or remove Firebase dependencies.
3. Test the app functionality after successful build.
4. Ensure all code compiles without errors.
