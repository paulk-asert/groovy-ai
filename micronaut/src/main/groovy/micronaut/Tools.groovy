package micronaut


import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.tools.ToolRegistry
import util.WeatherAwareBot
import util.WeatherTool
import util.WeekendTool

import java.time.LocalDate


try(var context = ApplicationContext.run()) {
    context.getBean(WeekendTool).with {
        println weekend
    }
    context.getBean(WeatherTool).with {
        println getWeather('Auckland', LocalDate.now())
        println getWeather('Mt Hutt', LocalDate.now())
    }

    println '----'
    var toolRegistry = context.getBean(ToolRegistry)
    println toolRegistry.allTools
    var weatherBeanDef = context.getBeanDefinition(WeatherTool)
    var getWeather = weatherBeanDef.getRequiredMethod('getWeather', String, LocalDate)
    toolRegistry.process(weatherBeanDef, getWeather)
    var weekendBeanDef = context.getBeanDefinition(WeekendTool)
    var getWeekend = weekendBeanDef.getRequiredMethod('getWeekend')
    toolRegistry.process(weekendBeanDef, getWeekend)
    println toolRegistry.allTools
    println '----'
    var bot = context.getBean(WeatherAwareBot)
    println bot.chat('Four great things to see in Auckland this coming weekend.')
        .itinerary
        .join('\n')
    println '----'
    println bot.chat('A few things to do in Mt Hutt this coming weekend.')
        .itinerary
        .join('\n')
}

/*
Activity(Visit Auckland War Memorial Museum, Auckland, Day 1, 9:00 AM - 5:00 PM)
Activity(Explore Viaduct Harbour and Wynyard Quarter, Auckland, Day 1, 6:00 PM - 8:00 PM)
Activity(Hike up Mount Eden, Auckland, Day 2, 9:00 AM - 12:00 PM)
Activity(Visit Waiheke Island and its vineyards, Waiheke Island, Day 2, 1:00 PM - 6:00 PM)
*/
