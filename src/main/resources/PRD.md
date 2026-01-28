# XSD to JSON Generator - Task List

## Instructions for Working with This Document

**IMPORTANT GUIDELINES:**
- Do not implement code unless explicitly asked to
- Scan the task list to identify the current task (marked with [x])
- Provide guidelines and help the user implement the current task
- Answer questions about the current task
- Only move forward when the user confirms task completion

---
## Project Status Summary

**Last Updated:** 2026-01-24
**Current Phase:** WP1 Complete ✅ | WP2 In Progress 🚧

### What Has Been Completed:

**WP1: Project Setup and Foundation** ✅
- Maven project structure created with standard directory layout
- Package structure established:
  - `parser/` - for XSD parsing logic
  - `model/` - for internal data models
  - `generator/` - for random data generation
  - `serializer/` - for JSON output
  - `cli/` - for command-line interface
- All dependencies configured in pom.xml:
  - Xerces 2.12.2 (XSD parsing)
  - Jackson 2.16.1 (JSON serialization)
  - SLF4J 2.0.9 + Logback 1.4.14 (Logging)
  - Picocli 4.7.5 (CLI framework - added but user wants to build CLI manually for learning)
  - JUnit 5.10.1 (Testing)
- Logback configuration created with:
  - Console appender for development
  - File appender with daily rolling policy
  - Logs saved to `logs/` directory
  - Root logger set to INFO level
- Maven Surefire Plugin 3.2.3 configured for test execution
- Maven build verified and working
- README.md deferred until project is more fleshed out

**WP2: XSD Parser Implementation** 🚧
- XsdLoader class implemented in `parser/` package
  - Loads XSD files from filesystem (handles both absolute and relative paths)
  - Uses Xerces XMLSchemaLoader to parse XSD into XSModel
  - File validation (exists, isFile)
  - Comprehensive error handling with XsdLoadException
  - SLF4J logging for debugging
- XsdLoadException custom exception created for schema loading errors
- Unit tests created (XsdLoaderTest):
  - Test loading valid XSD schema
  - Test handling non-existent files
  - Test handling invalid XML/XSD content
  - Test XSD file created: `src/test/resources/simple-person.xsd`
- All tests passing ✅

### Decisions Made:

1. **CLI Framework:** Picocli added as dependency, but user wants to implement CLI manually for learning purposes. Will use Picocli as reference/fallback if needed.

2. **Logging Configuration:** Chose Option 2 (Console + File logging) for flexibility in both development and production environments.

3. **Java Version:** Targeting Java 11+

4. **README:** Postponed until features are implemented to provide more meaningful documentation

### Project Structure Created:
```
xsd-json-generator/
├── pom.xml (fully configured)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── [base.package]/
│   │   │       ├── parser/
│   │   │       ├── model/
│   │   │       ├── generator/
│   │   │       ├── serializer/
│   │   │       └── cli/
│   │   └── resources/
│   │       └── logback.xml (configured)
│   └── test/
│       ├── java/
│       └── resources/
└── logs/ (will be created on first run)
```

### Next Steps:

**WP2: XSD Parser Implementation** is next. This involves:
- Implementing XSD file loader using Xerces
- Parsing schema definitions and constraints
- Creating internal model to represent parsed XSD
- Handling namespaces and imports

---

## WP1: Project Setup and Foundation ✅ COMPLETE

- [x] Create Maven project structure with proper package organization
- [x] Configure pom.xml with Xerces dependency
- [x] Configure pom.xml with Jackson dependency
- [x] Configure pom.xml with SLF4J + Logback dependency
- [x] Configure pom.xml with CLI framework (Picocli)
- [x] Configure pom.xml with JUnit 5
- [x] Set up logging configuration (logback.xml)
- [x] Create README.md
- [x] Create package structure: parser
- [x] Create package structure: model
- [x] Create package structure: generator
- [x] Create package structure: serializer
- [x] Create package structure: cli
- [x] Verify Maven build succeeds

## WP2: XSD Parser Implementation

- [x] Implement XSD file loader using Xerces
- [ ] Parse root elements from XSD
- [ ] Parse complex types (sequences)
- [ ] Parse complex types (choices)
- [ ] Parse complex types (all)
- [ ] Parse simple types with restrictions
- [ ] Extract element cardinality (minOccurs, maxOccurs)
- [ ] Extract string constraints (minLength, maxLength)
- [ ] Extract string pattern constraints
- [ ] Extract numeric constraints (minInclusive, maxInclusive, etc.)
- [ ] Extract enumeration values
- [ ] Handle imported schemas
- [ ] Handle included schemas
- [ ] Create internal model classes for parsed schema
- [ ] Implement namespace resolution for imported types
- [ ] Implement error handling for invalid XSD files
- [ ] Write unit tests for XSD parsing

## WP3: Random Data Generator Core

- [ ] Implement random string generator
- [ ] Implement random string generator with length constraints
- [ ] Implement random string generator with regex patterns
- [ ] Implement random integer generator
- [ ] Implement random long generator
- [ ] Implement random short generator
- [ ] Implement random byte generator
- [ ] Implement numeric generators with range constraints
- [ ] Implement random decimal generator
- [ ] Implement random float generator
- [ ] Implement random double generator
- [ ] Implement numeric generators with precision constraints
- [ ] Implement random boolean generator
- [ ] Implement random date generator
- [ ] Implement random datetime generator
- [ ] Implement random time generator
- [ ] Implement random base64Binary generator
- [ ] Implement random hexBinary generator
- [ ] Implement sequence handling in complex types
- [ ] Implement choice handling in complex types
- [ ] Implement attribute generation
- [ ] Implement minOccurs constraint handling
- [ ] Implement maxOccurs constraint handling
- [ ] Implement enumeration type handling
- [ ] Add configurable random seed for reproducibility
- [ ] Write tests for constraint compliance

## WP4: JSON Serialization Layer

- [ ] Implement Jackson-based JSON serializer
- [ ] Map XSD elements to JSON properties
- [ ] Map XSD attributes to JSON properties
- [ ] Handle arrays for maxOccurs > 1
- [ ] Implement pretty print formatting option
- [ ] Implement compact output mode
- [ ] Handle namespace information (optional)
- [ ] Support element vs attribute representation conventions
- [ ] Write tests for JSON output validation

## WP5: CLI Interface Implementation

- [ ] Implement CLI using Picocli
- [ ] Add --xsd argument
- [ ] Add --output argument
- [ ] Add --root-element argument
- [ ] Add --seed argument
- [ ] Add --pretty argument
- [ ] Add --count argument
- [ ] Add --verbose argument
- [ ] Implement help text
- [ ] Add usage examples to help
- [ ] Implement input validation
- [ ] Implement error reporting with helpful messages
- [ ] Set proper exit codes (0 for success, non-zero for errors)
- [ ] Support reading from stdin
- [ ] Write CLI integration tests

## WP6: JAR Library Interface

- [ ] Create public API for programmatic usage
- [ ] Implement builder pattern for configuration
- [ ] Create XsdJsonGenerator main class
- [ ] Implement fluent API methods
- [ ] Support in-memory XSD schemas
- [ ] Ensure thread-safety
- [ ] Write API usage examples
- [ ] Write JavaDoc for public API
- [ ] Write library usage tests

## WP7: Build and Packaging

- [ ] Configure Maven Assembly Plugin
- [ ] Create fat JAR with all dependencies
- [ ] Add manifest with Main-Class entry
- [ ] Test JAR execution on Linux
- [ ] Test JAR execution on Windows
- [ ] Test JAR execution on macOS
- [ ] Create shell script wrapper
- [ ] Verify single executable JAR works

## WP8: Testing and Quality Assurance

- [ ] Write unit tests for parser components
- [ ] Write unit tests for generator components
- [ ] Write unit tests for serializer components
- [ ] Write integration tests with real XSD files
- [ ] Test deeply nested structures
- [ ] Test recursive type definitions
- [ ] Test large schemas with many types
- [ ] Test invalid XSD files handling
- [ ] Test missing imported schemas handling
- [ ] Perform performance testing with large schemas
- [ ] Profile memory usage
- [ ] Verify 80%+ code coverage
- [ ] Cross-platform testing

## WP9: Documentation and Examples

- [ ] Write installation instructions in README
- [ ] Write CLI usage examples in README
- [ ] Write library usage examples in README
- [ ] Document configuration options
- [ ] Write troubleshooting guide
- [ ] Create additional example XSD files
- [ ] Document supported XSD features
- [ ] Document XSD limitations
- [ ] Add JavaDoc comments to public APIs
- [ ] Create CHANGELOG.md
- [ ] Write contribution guidelines

## WP10: Polish and Release Preparation

- [ ] Code cleanup and refactoring
- [ ] Enforce consistent code style
- [ ] Review and improve error messages
- [ ] Add version information to output
- [ ] Create release notes
- [ ] Tag release version in Git
- [ ] Final build verification