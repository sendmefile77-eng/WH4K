# WH4K / Myauko Engine Architecture

## Goal

WH4K is a modular Android RPG engine. The engine owns state, flow, prompt assembly, module loading, image-provider integration, and persistence. Modules own replaceable content: directions, banks, prompt packs, visual style tags, menus, and scripted frame templates.

## Layers

1. **App Shell**
   - Jetpack Compose UI.
   - Navigation between hub, direction selection, showcase, visit, settings, and module manager.

2. **Core Engine**
   - Game loop: Hub -> Direction -> Showcase -> Visit.
   - Visit structure: Act I-V, each act can contain frame beats and player pauses.
   - StateVector controls emotional, pacing, and risk signals.
   - BodyDna keeps generated character identity stable across frames.

3. **Module System**
   - A module is a folder with `module.json`, banks, prompt packs, menus, and styles.
   - Only one module is active at a time.
   - Modules should be replaceable without changing core engine code.

4. **Prompt System**
   - `PromptAssembler` combines game state, module style, character identity, frame intent, and player command.
   - The assembler produces positive prompt, negative prompt, seed hint, and metadata.

5. **Atlas Cloud Integration**
   - Atlas Cloud is treated as an image-provider boundary.
   - Core code should depend on `AtlasCloudClient`, not on raw HTTP calls.
   - This keeps the app testable and allows a fake client for previews.

## Safety boundary

The engine stores content-rating and safety flags at module level. Current base contracts require adult fictional characters, disallow minor characters, disallow real-person likeness, and keep provider-specific restrictions outside core gameplay state.

## Current branch

`app-skeleton` contains the first Android/Compose skeleton and core contracts.
