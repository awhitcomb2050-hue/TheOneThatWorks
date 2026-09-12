# Update to Pedro Pathing 3

This plan updates the project to Pedro Pathing v3 by streamlining the dependencies and adding the required repositories.

## User Review Required

> [!IMPORTANT]
> This update removes the legacy `com.pedropathing:ftc:2.1.1` dependency. Existing v2 code may need to be updated to use the new v3 API (which you have already started in `pedro3.java`).

## Proposed Changes

### Build Configuration

#### [MODIFY] [build.dependencies.gradle](file:///C:/Users/awhi0/StudioProjects/TheOneThatWorks/build.dependencies.gradle)
- Add Frozen Milk repository (required for `com.pedropathing:tuning:1.0.0` transitives like `Sloth`).
- Remove legacy Pedro Pathing v2 dependencies (`ftc:2.1.1`, `telemetry:1.0.0`).
- Remove duplicate `com.acmerobotics.dashboard:dashboard` entry.
- Ensure consistent Pedro Pathing v3 versions.

## Verification Plan

### Automated Tests
- Run Gradle Sync to verify all dependencies resolve.
- Run `:TeamCode:assembleDebug` to ensure the project compiles with the new dependencies.

### Manual Verification
- Verify that `pedro3.java` no longer shows unresolved symbol errors in the IDE.
