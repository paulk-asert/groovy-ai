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

You can change LLMs or models in the `application.properties` file (for Spring AI, Embabel, and Micronaut)
or via the hard-coded values in the other examples.

After cloning the repo, available tasks can be seen using:
```
./gradlew tasks --group=application
```

Example output follows.

## Ollama4j

> Found ollama: true
>
> Four things:
> 1. Visit the beautiful beaches: Caloundra is famous for its stunning beaches, including Kings Beach, Moffat Beach, and Bulcock Beach. Spend your days soaking up the sun, swimming, or surfing.
>
> 2. Explore the UnderWater World SeaLife Aquarium: This marine attraction offers a unique opportunity to interact with various sea creatures. You can even have a close encounter with sharks and turtles!
>
> 3. Visit the Glastonbury Estate: For those who love history, this beautiful estate is worth a visit. It was built in the 1920s and features a variety of artifacts and memorabilia from World War I and II.
>
> 4. Take a day trip to the Australia Zoo: Made famous by the Crocodile Hunter, Steve Irwin, the Australia Zoo is just a short drive from Caloundra. It's home to a wide variety of Australian wildlife, including kangaroos, koalas, and crocodiles. Don't miss the daily wildlife shows!
>
> Best thing:
> If you only have half a day and can choose just one activity, I would recommend visiting the beautiful Kings Beach in Caloundra. It offers a lovely stretch of sandy beach, perfect for swimming, sunbathing, or simply taking a leisurely stroll along the shoreline. The beach also has various amenities like picnic areas, BBQ facilities, and a playground for children. Plus, it provides stunning views of the Pacific Ocean and the Glass House Mountains in the distance. It's the perfect place to relax and soak up the sun on your vacation!

## LangChain4J

> Response:
> 1. Visit the beautiful beaches: Caloundra is known for its stunning beaches, with Kings Beach and Moffat Beach being some of the most popular ones. You can spend your days sunbathing, swimming, or surfing.
>
> 2. Explore the underwater world: Take a trip to the UnderWater World Sea Life Mooloolaba, an aquarium that houses a variety of marine life including sharks, turtles, and seahorses. It's a great way to learn about and appreciate the ocean's wonders.
>
> 3. Visit the Glastonbury Estate: This historic homestead offers a glimpse into Australia's past. The estate features beautiful gardens, a tea room, and often hosts various events throughout the year.
>
> 4. Take a day trip to the Glass House Mountains: Just a short drive from Caloundra, these iconic volcanic plugs offer breathtaking views and hiking trails for all levels of fitness. You can also visit the Kondalilla National Park for waterfalls and rainforest walks.

## Spring AI

Running the `Structured` script:

> Response:
> Activity(Visit Kings Beach, Caloundra, Day 1, Morning)
> Activity(Explore Bulcock Beach, Caloundra, Day 1, Afternoon)
> Activity(Sunset at Moffat Headland, Caloundra, Day 1, Evening)
> Activity(Visit the Australian Zoo, Beerwah, Day 2, Whole Day)
> Activity(Relax at Shelly Beach, Caloundra, Day 3, Morning)
> Activity(Explore Pumicestone Passage by boat tour, Caloundra, Day 3, Afternoon)

## Embabel

Running the `Rated` script:

```
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
```

## Micronaut

> Blimey mate, while you're roaming around Auckland, here are four top-notch spots ya gotta check out:
>
> 1. The Sky Tower - It's like the tallest bloke in town, with a view that'll make ya heart race.
> 2. Waitomo Glowworm Caves - It's a magical spot where tiny luminescent critters put on a light show.
> 3. Waiheke Island - A chilled-out paradise with beaches and vineyards, perfect for a day trip or longer stay.
> 4. Auckland Zoo - Get up close and personal with some of New Zealand's native critters, as well as exotic animals from around the world. Kiwi, eh?

## Quarkus

> Asking:
> What are four things to do while visiting Minneapolis?
> Answer:
> 1. Explore the Minneapolis Sculpture Garden: This 11-acre outdoor museum located in downtown Minneapolis is home to over 40 works of art, including the iconic "Spoonbridge and Cherry" sculpture. The garden also features walking trails, picnic areas, and a conservatory.
>
> 2. Visit the Mill City Museum: Located in the former Washburn A Mill, this museum tells the story of Minneapolis' milling heritage. You can explore exhibits on the city's flour-milling past, take a tour of the restored flour mill elevators, and enjoy panoramic views of the Mississippi River from the rooftop observation deck.
>
> 3. Stroll through the Minnehaha Park: This beautiful urban park features hiking trails, a waterfall, and breathtaking views of the Mississippi River. You can also visit Minnehaha Falls, a 53-foot waterfall that is one of the most popular attractions in Minneapolis.
>
> 4. Attend a concert or sporting event: Minneapolis is home to several major sports teams, including the Minnesota Vikings (NFL), Minnesota Timberwolves (NBA), and Minnesota Twins (MLB). The city also has a thriving music scene, with venues like First Avenue and the Orpheum Theatre hosting concerts by popular artists. Additionally, the Walker Art Center offers free outdoor performances during the summer months at its Sculpture Garden.

If you don't want to clone locally, check out the results in the GitHub Actions logs: \
[![Ollama4j examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runOllama4j.yml)
[![LangChain4J examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runLangChain4j.yml)
[![Spring AI examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runSpringAI.yml)
[![Embabel examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runEmbabel.yml)
[![Micronaut examples](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronautAI.yml/badge.svg)](https://github.com/paulk-asert/groovy-ai/actions/workflows/runMicronautAI.yml)
