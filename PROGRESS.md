# Progress Log

Last Updated: 2026-02-01

## Scope Clarification
- The program accepts one primary XSD that defines all host message root elements.
- Types referenced by those host messages may live in other XSDs via import/include and must be resolved.

## Parser Entry Point and Facade
- Added `ParsedSchema` model to return root elements + `TypeRegistry`.
- Added `XsdSchemaParser` to orchestrate load → extract roots → parse complex types into registry.
- Added `SchemaParsingFacade` as a thin wrapper around `XsdSchemaParser`.

Files:
- `src/main/java/com/simmerr/xsdjson/model/ParsedSchema.java`
- `src/main/java/com/simmerr/xsdjson/parser/XsdSchemaParser.java`
- `src/main/java/com/simmerr/xsdjson/parser/SchemaParsingFacade.java`

## XsdComplexTypeParser Fixes (Sequence-Only)
- `parseComplexType(...)` now returns a populated `ComplexTypeDefinition`, sets name/namespace/mixed/abstract, handles EMPTY, and registers the type.
- `parseParticle(...)` now returns a `ParsedGroup` (keeps content model + elements together).
- `parseModelGroup(...)` now aggregates child elements instead of overwriting them; throws on CHOICE/ALL.
- Error message no longer uses `XSParticle.getName()` (invalid).

File:
- `src/main/java/com/simmerr/xsdjson/parser/XsdComplexTypeParser.java`

## Tests Added
- New test XSD: `host-message-sequence.xsd` (simple sequence with min/max).
- New test class `XsdComplexTypeParserTest` for sequence parsing.
- Added second test in same class for `multiple-elements.xsd` (multiple roots and two complex types).

Files:
- `src/test/resources/host-message-sequence.xsd`
- `src/test/java/com/simmerr/parser/XsdComplexTypeParserTest.java`

## Notes / Known Limitations
- CHOICE/ALL compositors are not supported yet (parser throws).
- Registry is singleton; no reset between tests (not addressed yet).
- Import/include resolution relies on Xerces `XSModel` behavior; no explicit resolver implemented yet.
