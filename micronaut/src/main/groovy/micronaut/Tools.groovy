/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.tools.ToolRegistry
import util.WeatherAwareBot
import util.WeatherTool
import util.WeekendTool

try(var context = ApplicationContext.run()) {
/*
    // test out the tools manually
    context.getBean(WeekendTool).with {
        println weekend
    }
    context.getBean(WeatherTool).with {
        println getWeather('Auckland', LocalDate.now())
        println getWeather('Mt Hutt', LocalDate.now())
    }
*/
    println '----'
    var toolRegistry = context.getBean(ToolRegistry)
//    println toolRegistry.allTools
    var weatherBeanDef = context.getBeanDefinition(WeatherTool)
    var getWeather = weatherBeanDef.getRequiredMethod('getWeather', String, LocalDate)
    toolRegistry.process(weatherBeanDef, getWeather)
    var weekendBeanDef = context.getBeanDefinition(WeekendTool)
    var getWeekend = weekendBeanDef.getRequiredMethod('getWeekend')
    toolRegistry.process(weekendBeanDef, getWeekend)
    println toolRegistry.allTools

    println '----'
    var bot = context.getBean(WeatherAwareBot)
    println bot
        .chat('Four great things to see in Auckland this coming weekend.')
        .display()
    println '----'
    println bot
        .chat('A few things to do in Mt Hutt this coming weekend.')
        .display()
}

// main thing is to not expect skiing at Mt Hutt
/*
[util.WeatherTool@50dfbc58, util.WeekendTool@4416d64f]
----
Activity(Auckland Museum, 155 Queen Street, Auckland, 2025-11-14, 10:00 AM)
Activity(Beachside Stroll, Mission Bay Beach, 2025-11-14, 3:00 PM)
Activity(Harbour Bridge Walk, Auckland Harbour Bridge, 2025-11-15, 9:00 AM)
Activity(Sky Tower Panorama, Sky Tower, 666 Queen Street, 2025-11-15, 2:00 PM)
----
Activity(Mt Hutt Summit Walk, Mt Hutt Summit Track, 2025-11-15, 9:00 AM)
Activity(Lake Hutt Picnic, Lake Hutt, 2025-11-15, 1:00 PM)
Activity(Scenic Drive to Tutea Valley, Tutea Valley, Mt Hutt, 2025-11-16, 10:00 AM)
Activity(Water Activities at Lake Hutt, Lake Hutt, 2025-11-16, 2:00 PM)
*/
