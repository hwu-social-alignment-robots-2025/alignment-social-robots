package furhatos.app.openaichat.flow.llm.chatbot

// An interface that define how does an LLM client should be implemented to
// interact with the Furhat skill.
interface Chatbot {
    // Prompt user input as a string to the LLM and return its response as a string.
    fun prompt(): String
}