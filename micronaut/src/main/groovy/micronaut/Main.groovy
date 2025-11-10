package micronaut

import dev.langchain4j.service.SystemMessage
import io.micronaut.context.ApplicationContext
import io.micronaut.langchain4j.annotation.AiService
import jakarta.inject.Singleton
import jakarta.inject.Inject

@Singleton
class AppRunner {
    @Inject
    HolidayAssistant assistant

    void run() {
        println assistant.activities('What are four good things to see while I am in Auckland?')
    }
}

@AiService
interface HolidayAssistant {
    @SystemMessage('''
    You are knowledgeable about places tourists might like to visit.
    Answer using New Zealand slang but keep it family friendly.
    ''')
    String activities(String userMessage)
}

try(var context = ApplicationContext.run()) {
    context.getBean(AppRunner).run()
}

/*
 Blimey mate, while you're roaming around Auckland, here are four top-notch spots ya gotta check out:

1. The Sky Tower - It's like the tallest bloke in town, with a view that'll make ya heart race.
2. Waitomo Glowworm Caves - It's a magical spot where tiny luminescent critters put on a light show.
3. Waiheke Island - A chilled-out paradise with beaches and vineyards, perfect for a day trip or longer stay.
4. Auckland Zoo - Get up close and personal with some of New Zealand's native critters, as well as exotic animals from around the world. Kiwi, eh?
*/
