package langchain4j

import dev.langchain4j.model.ollama.OllamaChatModel

def chatModel = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .timeout(Duration.ofMinutes(5))
    .modelName("mistral:7b")
    .build()

String prompt = '"What are some interesting things to do while I am on vacation in Caloundra?"'
println "Ollama Response: " + chatModel.chat(prompt)
/*
Ollama Response:  Caloundra, located on the Sunshine Coast of Australia, offers a variety of activities that cater to different interests. Here are some suggestions for an enjoyable vacation:

1. **Beaches**: Caloundry's main beach and Kings Beach are popular choices for swimming, sunbathing, and surfing. Moffat Beach and Dicky Beach are also worth visiting.

2. **Bulcock Beach Esplanade Market**: This market, held every Sunday, offers a wide range of local produce, arts, crafts, and food stalls. It's a great place to experience the local culture and find unique souvenirs.

3. **The Eumundi Markets**: A bit further from Caloundra (about 40 minutes drive), these markets are renowned for their vibrant atmosphere and wide variety of artisanal goods, food, and live music. They are held every Wednesday and Saturday.

4. **Glass House Mountains National Park**: This park features a series of volcanic plugs that offer stunning views. You can hike, picnic, or simply enjoy the scenic vistas.

5. **UnderWater World Sea Life Mooloolaba**: A popular aquarium located in nearby Mooloolaba. It features a variety of marine life, including sharks, stingrays, and turtles.

6. **Australia Zoo**: Made famous by the Crocodile Hunter, Steve Irwin, Australia Zoo is home to a wide range of Australian wildlife. It's about a 40-minute drive from Caloundra.

7. **Noosa Everglades and Great Sandy National Park**: For a longer day trip (about 1.5 hours drive), you can explore the Noosa Everglades, Australia's only everglade system, or hike through the beautiful landscapes of Great Sandy National Park.

1. Visit the beaches: Caloundra has several beautiful beaches such as Kings Beach, Moffat Beach, and Bulcock Beach. These beaches offer swimming, surfing, and sunbathing opportunities.
2. Explore the Glass House Mountains: The Glass House Mountains are a group of 12 granite peaks that rise above the low-lying plains of the Sunshine Coast. You can hike or take a guided tour to explore these unique landmarks.
3. Visit the UnderWater World Sea Life Mooloolaba: This aquarium is home to a variety of marine life, including sharks, turtles, and seahorses. It's a great destination for families with children.
4. Take a boat tour: There are several companies that offer boat tours in the Pumicestone Passage, where you can spot dolphins, dugongs, and other wildlife.
5. Visit the Eumundi Markets: The Eumundi Markets are one of Australia's most popular markets, with over 600 stalls selling everything from local produce to handmade crafts. It's open every Wednesday and Saturday.
6. Take a surf lesson: Caloundra is known for its surf breaks, so why not take a surf lesson and learn how to ride the waves?
7. Visit Australia Zoo: Located about an hour's drive from Caloundra, Australia Zoo was made famous by the late Steve Irwin and offers visitors the opportunity to get up close and personal with a wide variety of Australian wildlife.
8. Take a scenic flight: For a truly unique experience, take a scenic flight over the Sunshine Coast and see the area from a bird's eye view.

*/
