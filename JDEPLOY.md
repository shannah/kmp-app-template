# jDeploy Setup

This repository has been configured for jDeploy, allowing the Kotlin Multiplatform Compose Desktop application to be distributed as native installers for Windows, macOS, and Linux.

## What's Included

### Desktop Application
- **Target Platforms**: Linux (x64/ARM64), macOS (x64/ARM64), Windows (x64)
- **Main Class**: `MainKt` (Compose Desktop entry point)
- **Java Version**: 11 (compatible with KMP requirements)

### jDeploy Configuration
- **Package.json**: Complete jDeploy configuration with cross-platform bundle support
- **Application Icon**: 192x192 PNG sourced from Android launcher icon
- **Platform Bundles**: Optimized bundles for each platform using .jdpignore files
- **GitHub Workflow**: Automated CI/CD pipeline for building and publishing installers

### Platform-Specific Optimizations
The setup includes `.jdpignore` files for each platform to exclude unnecessary native libraries:
- `.jdpignore.linux-x64` - Linux x86_64 bundles
- `.jdpignore.linux-arm64` - Linux ARM64 bundles  
- `.jdpignore.mac-x64` - macOS Intel bundles
- `.jdpignore.mac-arm64` - macOS Apple Silicon bundles
- `.jdpignore.win-x64` - Windows x64 bundles
- `.jdpignore.win-arm64` - Windows ARM64 bundles (future support)

## Building

### Local Development
```bash
# Build the executable JAR
./gradlew :composeApp:packageUberJarForCurrentOS

# Run the desktop application
./gradlew :composeApp:run
```

### jDeploy Commands
```bash
# Install jDeploy (if not already installed)
npm install -g jdeploy

# Build installers
jdeploy package

# Publish to npm (requires npm account)
jdeploy publish
```

### GitHub Actions
The repository includes a workflow (`.github/workflows/jdeploy.yml`) that automatically:
1. Builds the application on push/tag
2. Creates platform-specific installers 
3. Publishes releases to GitHub
4. Optionally creates signed macOS DMG files

## Cross-Platform Native Libraries

The desktop application includes native libraries for all supported platforms:
- **Skiko**: Compose Multiplatform rendering engine
- **Ktor**: HTTP client with platform-specific implementations
- **Coil**: Image loading with network capabilities

This ensures the JAR runs on any supported platform without requiring platform-specific builds.

## Installation

Once published with jDeploy, users can install the application via npm:

```bash
npm install -g kmp-app-template
```

Or download platform-specific installers from GitHub Releases.