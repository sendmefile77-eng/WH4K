# Atlas Cloud Integration

Atlas Cloud is the image-generation provider for WH4K.

## Boundary

The core engine should only talk to the `AtlasCloudClient` interface:

```kotlin
interface AtlasCloudClient {
    suspend fun generateImage(request: AtlasImageRequest): AtlasImageResult
}
```

The prompt assembler prepares a provider-neutral `PromptResult`. The Atlas layer converts it into the actual request format expected by Atlas Cloud.

## Planned flow

1. Player enters a direction or continues a visit.
2. Core engine updates `GameState`.
3. `PromptAssembler` creates prompt data.
4. `AtlasCloudClient` submits the image job.
5. App stores the job id, generated image url/cache key, prompt metadata, and frame identity.
6. UI displays the generated frame and lets the player continue or regenerate.

## Configuration

Planned settings:

- Atlas Cloud base URL.
- API key / auth token, stored outside module files.
- Default model.
- Default width/height.
- Steps and guidance scale.
- Cache policy.

## Testability

`FakeAtlasCloudClient` is included for local previews and UI work before the real HTTP client is implemented.
