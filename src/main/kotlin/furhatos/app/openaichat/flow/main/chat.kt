package furhatos.app.openaichat.flow

import furhatos.app.openaichat.flow.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.app.openaichat.flow.llm.chatbot.openai.OpenAIChatbot

val Chat = state(Parent) {
    onEntry {


        delay(2000)
        Furhat.dialogHistory.clear()
        reentry()
    }

    onReentry {
        furhat.listen()
    }

    onResponse("can we stop", "goodbye") {
        furhat.say("Okay, goodbye")
        goto(Idle)
    }

    onResponse {
        // Retrieve API Key from environment variables
        val apiKey: String = System.getenv("API_KEY") ?: ""
        /** Check API key for the OpenAI GPT3 language model has been set */
        if (apiKey.isEmpty()) {
            println("Missing API key for OpenAI GPT3 model. ")
            exit()
        }
        // Initialize LLM client
        val chatbot = OpenAIChatbot(apiKey)

        furhat.gesture(GazeAversion(2.0))
        val response = call {
            chatbot.prompt()
        } as String
        furhat.say(response)
        reentry()
    }

    onNoResponse {
        reentry()
    }
}
