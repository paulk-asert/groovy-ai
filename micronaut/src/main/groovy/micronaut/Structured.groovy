package micronaut

import dev.langchain4j.service.SystemMessage
import domain.Itinerary
import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.annotation.AiService

@AiService
interface HolidayBot {
    @SystemMessage('''
    Return holiday activity suggestions as structured JSON matching Itinerary.
    Timebox activities if needed to fit within the holiday length and not overlap other
    activities while still giving enough time to see all major aspects of each attraction.
    Exclude other information.
    ''')
    Itinerary itinerary(String userMessage)
}

try(var context = ApplicationContext.run()) {
    println context.getBean(HolidayBot)
        .itinerary('Four great things to see in Auckland over a weekend.')
        .itinerary
        .join('\n')
}

/*
Activity(Visit Auckland War Memorial Museum, Auckland, Day 1, 9:00 AM - 5:00 PM)
Activity(Explore Viaduct Harbour and Wynyard Quarter, Auckland, Day 1, 6:00 PM - 8:00 PM)
Activity(Hike up Mount Eden, Auckland, Day 2, 9:00 AM - 12:00 PM)
Activity(Visit Waiheke Island and its vineyards, Waiheke Island, Day 2, 1:00 PM - 6:00 PM)
*/
