package springai

import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
void main() {
    try(def context = SpringApplication.run(Holiday)) {
        def chatClient = context.getBean(ChatClient)
        println "Asking Ollama..."
        def response = chatClient
                .prompt("What are some interesting things to do while I am on vacation in Caloundra?")
                .call()
                .entity(Itinerary)
        println "\nResponse:\n" + response.itinerary.join('\n')
    }
}
/*
Asking Ollama...

Response:
Activity(Visit Kings Beach, Caloundra, Day 1, Morning)
Activity(Explore Bulcock Beach, Caloundra, Day 1, Afternoon)
Activity(Sunset at Moffat Headland, Caloundra, Day 1, Evening)
Activity(Visit the Australian Zoo, Beerwah, Day 2, Whole Day)
Activity(Relax at Shelly Beach, Caloundra, Day 3, Morning)
Activity(Explore Pumicestone Passage by boat tour, Caloundra, Day 3, Afternoon)
*/
