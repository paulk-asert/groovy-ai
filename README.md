<!--
SPDX-License-Identifier: Apache-2.0

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

groovy-ai
=========

Examples for this blog post:
https://groovy.apache.org/blog/groovy-ai

Groovy AI chat code to find holiday activity suggestions
using Ollama4j, LangChain4j, Spring AI, Embabel, Micronaut, and Quarkus,
plus hosted-model examples using OpenRouter and the ASF llmao gateway.

The examples are configured to use local LLMs via Ollama.
They assume you have at least the `mistral:7b` model available.

You can set that up manually, use GitHub actions like this repo does, or use docker as follows:

```bash
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
docker exec -it ollama ollama run mistral:7b
```

Some examples use the larger but slightly better `qwen3:8b` model.

You can change LLMs or models in the `application.properties` file (for Spring AI, Embabel, and Micronaut)
or via the hard-coded values in the other examples.

The build requires Gradle to be run using JDK25 or later.

After cloning the repo, available tasks can be seen using:
```
./gradlew tasks --group=application
```

Example output follows.

## Ollama4j

    Found ollama: true

    Four things:
    1. Visit the beautiful beaches: Caloundra is famous for its stunning beaches, including Kings Beach, Moffat Beach, and Bulcock Beach. Spend your days soaking up the sun, swimming, or surfing.

    2. Explore the UnderWater World SeaLife Aquarium: This marine attraction offers a unique opportunity to interact with various sea creatures. You can even have a close encounter with sharks and turtles!

    3. Visit the Glastonbury Estate: For those who love history, this beautiful estate is worth a visit. It was built in the 1920s and features a variety of artifacts and memorabilia from World War I and II.

    4. Take a day trip to the Australia Zoo: Made famous by the Crocodile Hunter, Steve Irwin, the Australia Zoo is just a short drive from Caloundra. It's home to a wide variety of Australian wildlife, including kangaroos, koalas, and crocodiles. Don't miss the daily wildlife shows!

    Best thing:
    If you only have half a day and can choose just one activity, I would recommend visiting the beautiful Kings Beach in Caloundra. It offers a lovely stretch of sandy beach, perfect for swimming, sunbathing, or simply taking a leisurely stroll along the shoreline. The beach also has various amenities like picnic areas, BBQ facilities, and a playground for children. Plus, it provides stunning views of the Pacific Ocean and the Glass House Mountains in the distance. It's the perfect place to relax and soak up the sun on your vacation!

## LangChain4J

    Response:
    1. Visit the beautiful beaches: Caloundra is known for its stunning beaches, with Kings Beach and Moffat Beach being some of the most popular ones. You can spend your days sunbathing, swimming, or surfing.

    2. Explore the underwater world: Take a trip to the UnderWater World Sea Life Mooloolaba, an aquarium that houses a variety of marine life including sharks, turtles, and seahorses. It's a great way to learn about and appreciate the ocean's wonders.

    3. Visit the Glastonbury Estate: This historic homestead offers a glimpse into Australia's past. The estate features beautiful gardens, a tea room, and often hosts various events throughout the year.

    4. Take a day trip to the Glass House Mountains: Just a short drive from Caloundra, these iconic volcanic plugs offer breathtaking views and hiking trails for all levels of fitness. You can also visit the Kondalilla National Park for waterfalls and rainforest walks.

## Spring AI

Running the `Structured` script:

    Response:
    Activity(Visit Kings Beach, Caloundra, Day 1, Morning)
    Activity(Explore Bulcock Beach, Caloundra, Day 1, Afternoon)
    Activity(Sunset at Moffat Headland, Caloundra, Day 1, Evening)
    Activity(Visit the Australian Zoo, Beerwah, Day 2, Whole Day)
    Activity(Relax at Shelly Beach, Caloundra, Day 3, Morning)
    Activity(Explore Pumicestone Passage by boat tour, Caloundra, Day 3, Afternoon)

## Embabel

Running the `Rated` script:

    RatedItinerary[
      itinerary=Itinerary[itinerary=[
        Activity(Arrival, Caloundra, Friday, Afternoon),
        Activity(Kings Beach Leisure Park, Kings Beach, Friday, Evening),
        Activity(Dinner at Bulcock Street Tavern, Caloundra, Friday, Night),
        Activity(Explore Powerboat Racing Complex, Caloundra, Saturday, Morning),
        Activity(Lunch at Point Cartwright, Point Cartwright, Saturday, Afternoon),
        Activity(Sunset Yoga on Mooloolaba Beach, Mooloolaba Beach, Saturday, Evening),
        Activity(Dinner at Ricky's River Bar & Restaurant, Eumundi, Saturday, Night),
        Activity(Visit the Ginger Factory, Yandina, Sunday, Morning),
        Activity(Relax at Golden Beach, Golden Beach, Sunday, Afternoon),
        Activity(Farewell Dinner, Caloundra, Sunday, Evening),
        Activity(Departure, Caloundra, Monday, Morning)]],
      rating=Rating[percentage=90.0]]

## Micronaut

    Blimey mate, while you're roaming around Auckland, here are four top-notch spots ya gotta check out:

    1. The Sky Tower - It's like the tallest bloke in town, with a view that'll make ya heart race.
    2. Waitomo Glowworm Caves - It's a magical spot where tiny luminescent critters put on a light show.
    3. Waiheke Island - A chilled-out paradise with beaches and vineyards, perfect for a day trip or longer stay.
    4. Auckland Zoo - Get up close and personal with some of New Zealand's native critters, as well as exotic animals from around the world. Kiwi, eh?

### Micronaut native

The Micronaut examples can also be compiled to GraalVM native executables,
making use of the native-image support in Groovy 6.
Run Gradle using GraalVM 25.2 or later (earlier versions fail with
`Could not find target method ... invalidateSwitchPoints`, a Groovy method
they expect but current Groovy versions no longer have) and use the `nativeHolidayCompile`,
`nativeStructuredCompile` or `nativeToolsCompile` tasks, e.g.:

```
./gradlew :micronaut:nativeToolsCompile
micronaut/build/native/nativeToolsCompile/tools
```

The reflection metadata for our scripts is in
`micronaut/src/main/resources/META-INF/native-image`.
If you change the examples, regenerate it by running them with the
native-image agent and copying the result
(new entries are merged into the existing file, so delete it first to drop stale entries):

```
./gradlew run.micronaut.Holiday run.micronaut.Structured run.micronaut.Tools -Pagent
./gradlew :micronaut:metadataCopy
```

## Quarkus

Quarkus needs special boot code to run.
To run the example use the `quarkusRun` task.
Also, `quarkusRun` doesn't seem to honor the toolchain setting,
so instead run your Gradle build using JDK25.

    Asking:
    What are four things to do while visiting Minneapolis?
    Answer:
    1. Explore the Minneapolis Sculpture Garden: This 11-acre outdoor museum located in downtown Minneapolis is home to over 40 works of art, including the iconic "Spoonbridge and Cherry" sculpture. The garden also features walking trails, picnic areas, and a conservatory.

    2. Visit the Mill City Museum: Located in the former Washburn A Mill, this museum tells the story of Minneapolis' milling heritage. You can explore exhibits on the city's flour-milling past, take a tour of the restored flour mill elevators, and enjoy panoramic views of the Mississippi River from the rooftop observation deck.

    3. Stroll through the Minnehaha Park: This beautiful urban park features hiking trails, a waterfall, and breathtaking views of the Mississippi River. You can also visit Minnehaha Falls, a 53-foot waterfall that is one of the most popular attractions in Minneapolis.

    4. Attend a concert or sporting event: Minneapolis is home to several major sports teams, including the Minnesota Vikings (NFL), Minnesota Timberwolves (NBA), and Minnesota Twins (MLB). The city also has a thriving music scene, with venues like First Avenue and the Orpheum Theatre hosting concerts by popular artists. Additionally, the Walker Art Center offers free outdoor performances during the summer months at its Sculpture Garden.

## OpenAI-compatible (llmao and OpenRouter)

The `openai-compatible` examples talk to any OpenAI-compatible endpoint:

* [OpenRouter](https://openrouter.ai) is a hosted service in front of hundreds of models
  (Claude, GPT, Gemini, Llama, Mistral, and more).
* [llmao](https://github.com/apache/tooling-llmao) is the ASF LLM gateway at `llm.apache.org`,
  giving ASF committers access to Foundation-sanctioned models such as `qwen3.8-27b` and `gemma4-26b`.

The `Holiday` script uses Groovy's declarative HTTP client (`@HttpBuilderClient`),
while `HolidayStructured` reuses LangChain4j's OpenAI module with a different base URL.
Both pick their endpoint from whichever API key is set: `LLMAO_API_KEY`, then `OPENROUTER_API_KEY`.

`HolidayParallel` instead asks *every* configured provider the same question, fanning the
calls out with Groovy's `collectParallel` so the endpoints answer concurrently, and prints
the replies side by side. With two providers configured it also times a one-at-a-time run
for comparison.

Like the other examples, these run with no extra setup: if neither key is set,
they use Ollama's OpenAI-compatible endpoint (`http://localhost:11434/v1`) with `mistral:7b`.
The output's first line shows which endpoint was used:

    Using mistral:7b via Ollama (http://localhost:11434/v1)

    Four things:
    Activity(Visit Kings Beach, Kings Beach, Caloundra, Saturday, Morning to Afternoon)
    Activity(Explore Bulcock Beach Esplanade, Bulcock Beach Esplanade, Caloundra, Saturday, Afternoon to Evening)
    Activity(Glass Bottom Boat Tour to Dicky Beach, Coastal Waters near Dicky Beach, Sunday, Morning)
    Activity(Sundowner at Moffat Headland, Moffat Headland, Caloundra, Sunday, Evening)

    Best thing:
    Activity(Glass Bottom Boat Tour to Dicky Beach, Coastal Waters near Dicky Beach, Sunday, Morning)

### Using llmao

1. Sign in at https://llm.apache.org with your ASF id and create a personal access token under **My Keys**
   (the secret is only shown once). The [Models page](https://llm.apache.org/models) lists the available model ids.
2. Set the token (and optionally the model, default `qwen3.8-27b`) and run the examples:

   ```bash
   export LLMAO_API_KEY=<your personal access token>
   export LLMAO_MODEL=gemma4-26b   # optional
   ./gradlew run.openai-compatible.All
   ```

   or in PowerShell:

   ```powershell
   $env:LLMAO_API_KEY = '<your personal access token>'
   $env:LLMAO_MODEL = 'gemma4-26b'   # optional
   ./gradlew run.openai-compatible.All
   ```

Requests to llmao turn off Qwen3's reasoning mode (`enable_thinking: false`),
as reasoning can use up the output budget and return an empty answer.

### Using OpenRouter

1. Create an account at https://openrouter.ai and create an API key at https://openrouter.ai/settings/keys.
2. The default model (`mistralai/mistral-small-3.2-24b-instruct`) is paid, so add a few dollars of credit.
   Alternatively, pick one of the free models (ids ending in `:free`, rate-limited and subject to change)
   from https://openrouter.ai/models. For `HolidayStructured`, the model must support structured outputs.
3. Set the key (and optionally the model) and run the examples:

   ```bash
   export OPENROUTER_API_KEY=sk-or-...
   export OPENROUTER_MODEL=openai/gpt-4o-mini   # optional
   ./gradlew run.openai-compatible.All
   ```

   or in PowerShell:

   ```powershell
   $env:OPENROUTER_API_KEY = 'sk-or-...'
   $env:OPENROUTER_MODEL = 'openai/gpt-4o-mini'   # optional
   ./gradlew run.openai-compatible.All
   ```

The `runOpenAICompatible` GitHub workflow reads repository secrets named `LLMAO_API_KEY` and/or `OPENROUTER_API_KEY`
(Settings → Secrets and variables → Actions). For llmao, request a key for the script under **Request a key**
on **My Keys** rather than using your personal token. Without either secret (e.g. on forks), the workflow skips the examples.

If you don't want to clone locally, check out the results in the GitHub Actions logs: \
[![Ollama4j examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml)
[![LangChain4J examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml)
[![Spring AI examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml)
[![Embabel examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml)
[![Micronaut examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronautAI.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronautAI.yml)
[![Quarkus examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runQuarkus.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runQuarkus.yml)
[![OpenAI-compatible examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOpenAICompatible.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOpenAICompatible.yml)
