groovy-ai
=========

Examples for this blog post:
https://groovy.apache.org/blog/groovy-ai

Groovy AI chat code using Ollama4j, L:angChain4j, Spring AI, and Embabel to find holiday activity suggestions.

The examples are configured to use local LLMs via Ollama.
They assume you have at least the `mistral:7b` model available.
A second model is assumed for the `embabel.Rated` example.

You can change models in the `application.properties` file (for Spring AI and Embabel)
or via the hard-coded values in the other examples.

After cloning the repo, available tasks can be seen using:
```
./gradlew tasks --group=application
```

If you don't want to clone locally, check out the results in the GitHub Actions logs:
https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j/badge.svg
