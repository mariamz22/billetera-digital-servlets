# Upgrade Plan: billetera-digital (20261112034134)

- **Generated**: 2026-11-12 03:41:34
- **HEAD Branch**: N/A
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 27: C:\Program Files\Java\jdk-27\bin (existing environment JDK, usable as fallback for compile checks)
- JDK 25: C:\Users\benne\.jdk\jdk-25\jdk-25.0.2\bin (required by target LTS)
- JDK 17: not available (baseline will be skipped)

**Build Tools**
- Maven 3.9.15: C:\Users\benne\.maven\maven-3.9.15\bin (required for compile/package steps)
- Maven Wrapper: not present in the repository

## Guidelines

- Upgrade the Java runtime to the latest LTS version in auto-execution mode.
- Keep the project compilable using the minimum necessary Maven changes.
- Prefer a narrow, deterministic build-target upgrade rather than framework refactoring.

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20261112034134
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 25

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| ---------------------- | ------- | --------------------- | ---------------- |
| Java | 17 | 25 | User requested latest LTS runtime |
| Maven | not installed | 3.9.0+ | Java 25 is best supported with Maven 3.9+ |
| maven.compiler.release | 17 | 25 | Java target must match the requested runtime |
| jakarta.servlet-api | 6.0.0 | 6.0.x | Already compatible with modern Java; no API migration required |
| maven-war-plugin | 3.4.0 | 3.4.0+ | Compatible with JDK 25; no rewrite required unless build fails |

## Derived Upgrades

- Java 25 requires the project build to target Java 25 via `maven.compiler.release`.
- Maven 3.9+ is required for the toolchain used in the final verification step.
- No Spring Boot or Jakarta namespace migration is required because this project already uses the Jakarta Servlet API and a simple JSP/Servlet structure.

## Impact Analysis

### Subsection: Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
| ---- | ---------- | ------- | ------ | ------ | ------ |
| pom.xml | `maven.compiler.release` | 17 | upgrade | 25 | User-requested Java runtime upgrade |
| pom.xml | build tool/runtime | not installed | add | Maven 3.9.15 | Required to compile on Java 25 |

### Subsection: Source Code Changes

| File | Location | Current | Required Change | Reason |
| ---- | -------- | ------- | --------------- | ------ |
| src/main/java/pe/upn/billetera/model/Billetera.java | class compilation | Java 17 source-compatible | No source rewrite expected | No reflection or removed JDK APIs detected |
| src/main/java/pe/upn/billetera/model/Movimiento.java | class compilation | Java 17 source-compatible | No source rewrite expected | No reflection or removed JDK APIs detected |
| src/main/java/pe/upn/billetera/servlet/BilleteraServlet.java | servlet implementation | Java 17 source-compatible | No source rewrite expected | Servlet API version already targets Jakarta 6 |

### Subsection: Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
| ---- | ---------------- | ------- | --------------- | ------ |
| pom.xml | `maven.compiler.release` | 17 | set to `25` | Required to compile with the target JDK |

### Subsection: CI/CD Changes

| File | Location | Current | Required Change |
| ---- | -------- | ------- | --------------- |
| None | N/A | N/A | No CI/CD files were detected in the repository |

### Subsection: Risks & Warnings

- **No historical Java 17 baseline is available**: The project does not currently expose a JDK 17 installation in the environment, so the baseline step is skipped. **Mitigation**: verify by compiling and packaging against the installed JDK 25 after the upgrade.
- **No Maven installation was detected**: Build validation cannot proceed until Maven is installed. **Mitigation**: install Maven 3.9.15 and verify `mvn -version` before building.
- **No automated tests were detected**: The repository contains application source and JSP files but no test suite. **Mitigation**: compile and package the WAR with the target JDK and report any remaining build/test gaps clearly.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Java 25 requires a matching JDK and a modern Maven installation before any build verification can occur.
  - **Changes to Make**: Install JDK 25 and Maven 3.9.15; verify the toolchain is available.
  - **Verification**: `java -version` and `mvn -version` using the JDK 25 environment; expected result is successful detection of Java 25 and Maven 3.9.15.

- Step 2: Setup Baseline
  - **Rationale**: Confirm whether a baseline compile/test pass exists before the runtime change; skip if the base JDK is not available.
  - **Changes to Make**: Run baseline if JDK 17 is available; otherwise note that the baseline is skipped because the original JDK is unavailable in this environment.
  - **Verification**: `mvn clean compile test-compile -q && mvn clean test -q` with the original runtime if available; otherwise mark as skipped.

- Step 3: Upgrade the Maven Java target to Java 25
  - **Rationale**: This is the minimal required change to move the project from Java 17 to the target LTS runtime.
  - **Changes to Make**: Apply the `pom.xml` Java target upgrade in the `Dependency Changes` and `Configuration Changes` sections.
  - **Verification**: `C:\Users\benne\.maven\maven-3.9.15\bin\mvn clean test-compile -q` with `JAVA_HOME=C:\Users\benne\.jdk\jdk-25\jdk-25.0.2` and expected result: compilation succeeds.

- Step 4: Final Validation
  - **Rationale**: Confirm the WAR package builds successfully under Java 25 and no compilation/runtime regressions remain.
  - **Changes to Make**: Resolve any remaining build errors or compatibility issues until the project packages cleanly.
  - **Verification**: `C:\Users\benne\.maven\maven-3.9.15\bin\mvn clean package -q` with `JAVA_HOME=C:\Users\benne\.jdk\jdk-25\jdk-25.0.2`; expected result: successful WAR build with no compilation errors.
