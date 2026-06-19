# Module Specification v0.1

A WH4K module is a folder that can be imported by the app and activated in settings.

## Required layout

```text
module-folder/
  module.json
  banks/
    characters.json
    locations.json
    atmospheres.json
    frames.json
  prompts/
    showcase.json
    visit.json
  styles/
    visual.json
  menus/
    directions.json
```

## module.json

```json
{
  "id": "example.grimdark",
  "name": "Example Grimdark Module",
  "version": "0.1.0",
  "minEngineVersion": "0.1.0",
  "contentRating": "ADULT_ONLY",
  "directions": [
    {
      "id": "citadel",
      "title": "Citadel Gate",
      "description": "A cinematic entry point for the module.",
      "showcasePromptKey": "showcase.citadel",
      "visitScriptKey": "visit.citadel"
    }
  ],
  "banks": {
    "characters": "banks/characters.json",
    "locations": "banks/locations.json",
    "atmospheres": "banks/atmospheres.json",
    "frameTemplates": "banks/frames.json"
  },
  "promptPacks": ["prompts/showcase.json", "prompts/visit.json"],
  "styleTags": ["grimdark", "cinematic"],
  "safety": {
    "requiresAdultCharacters": true,
    "disallowMinors": true,
    "disallowRealPersonLikeness": true,
    "disallowIllegalContent": true
  }
}
```

## Design rule

Modules describe content and style. They should not own persistence, networking, billing, authentication, or Atlas Cloud credentials. Those belong to the app shell and core infrastructure.
