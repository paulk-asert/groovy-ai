package micronaut

import io.micronaut.context.ApplicationContext
import util.AiModelFactory
import util.OllamaToolExecutor
import groovy.json.JsonSlurper
import domain.Activity
import util.WeatherTool
import util.WeekendTool

try (def context = ApplicationContext.run()) {

    def chatModel = context.getBean(AiModelFactory).chatModel()
    def weatherTool = context.getBean(WeatherTool)
    def weekendTool = context.getBean(WeekendTool)

    def userMessage = 'Four great things to see in Auckland this coming weekend.'

    // Step 1: initial LLM call
    def rawResponse = chatModel.chat(userMessage)
    def slurper = new JsonSlurper()
    def response = slurper.parseText(rawResponse)

    // Step 2: execute any suggested tool calls
    OllamaToolExecutor.executeToolCalls(response, weatherTool, weekendTool)

    // Step 3: feed results back if there were tool calls
    println response
    if (response.toolCalls?.size() > 0) {
        def toolResultsMessage = response.toolCalls.collect { call ->
            [
                tool: call.function.name,
                result: call.result
            ]
        }
        def followUpPrompt = "Here are the results from the tools: ${toolResultsMessage}"
        response = chatModel.chat(followUpPrompt)
    }

    // Step 4: parse Itinerary JSON
    def itineraryJson = new JsonSlurper().parseText(response.content)
    def activities = itineraryJson.itinerary.collect { a ->
        new Activity(a.activity, a.location, a.day, a.time)
    }
    activities.each { println it }
}
