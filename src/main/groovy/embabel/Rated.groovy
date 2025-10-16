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
package embabel

import com.embabel.agent.api.annotation.AchievesGoal
import com.embabel.agent.api.annotation.Action
import com.embabel.agent.api.annotation.Agent
import com.embabel.agent.api.common.OperationContext
import com.embabel.agent.api.common.autonomy.Autonomy
import com.embabel.agent.config.annotation.EnableAgents
import com.embabel.agent.config.annotation.LoggingThemes
import com.embabel.agent.core.ProcessOptions
import com.embabel.agent.domain.io.UserInput
import domain.Alternatives
import domain.RatedAlternatives
import domain.RatedItinerary
import domain.Rating
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Agent(description = "Creates and ranks itineraries for a holiday at a given location")
class ItineraryAgent {
    @Action
    Alternatives generateItineraries(UserInput userInput, OperationContext context) {
        context.ai()
            .withLlm('mistral:7b')
            .createObject("Generate 5 alternative sets of: $userInput.content?", Alternatives)
    }

    @Action
    RatedAlternatives rateItineraries(Alternatives alternatives, OperationContext context) {
        new RatedAlternatives(alternatives.content.collect { itinerary ->
            var rating = context.ai()
                .withLlm('qwen3:8b')
                .createObject("Rate this itinerary on variety and number of activities: $itinerary?", Rating)
            new RatedItinerary(itinerary, rating)
        })
    }

    @Action
    @AchievesGoal(description = 'Best itinerary')
    RatedItinerary bestItinerary(RatedAlternatives ratedAlternatives) {
        ratedAlternatives.content.max { it.rating.percentage }
    }
}

@SpringBootApplication
@EnableAgents(loggingTheme = LoggingThemes.STAR_WARS)
void main() {
    try(var context = SpringApplication.run(Rated)) {
        println context.getBean(Autonomy)
            .chooseAndRunAgent('Itinerary for a long-weekend holiday in Caloundra', ProcessOptions.DEFAULT).output
    }
}
/*
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
*/
