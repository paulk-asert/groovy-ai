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

Groovy AI chat code using Ollama4j, LangChain4j, Spring AI, Embabel, and Micronaut to find holiday activity suggestions.

The examples are configured to use local LLMs via Ollama.
They assume you have at least the `mistral:7b` model available.

You can set that up manually, use GitHub actions like this repo does, or use docker as follows:

```bash
docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
docker exec -it ollama ollama run mistral:7b
```

A second model is assumed for the `embabel.Rated` example.

You can change LLMs or models in the `application.properties` file (for Spring AI and Embabel)
or via the hard-coded values in the other examples.

After cloning the repo, available tasks can be seen using:
```
./gradlew tasks --group=application
```

If you don't want to clone locally, check out the results in the GitHub Actions logs: \
[![Ollama4j examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml)
[![LangChain4J examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml) \
[![Spring AI examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml)
[![Embabel examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml)
[![Micronaut examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronaut.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronaut.yml)
