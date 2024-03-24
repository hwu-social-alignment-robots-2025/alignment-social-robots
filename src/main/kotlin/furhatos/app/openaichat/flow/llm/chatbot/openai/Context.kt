package furhatos.app.openaichat.flow.llm.chatbot.openai

data class Context(
        var context : String,
        var AgentLastMsg: String,
        var UserLastMsg: String
)
