package furhatos.app.client.llm.client

import furhatos.app.client.config.*
import furhatos.app.client.llm.client.openai.GPTClient
import furhatos.app.client.prompt.engineering.PromptEngineering

// An interface that define how does an LLM client should be implemented to
// interact with the Furhat skill client.
interface LLMClient {
    // Prompt user input as a string to the LLM and return its response as a string.
    fun prompt(): String
}

fun loadLLMClientFromConfig(config: Config, promptEngineering: PromptEngineering) : LLMClient {
    return when(config.llm) {
        is LLMConfig.Openai -> GPTClient(config.llm.openai, promptEngineering)
        is LLMConfig.Google -> throw Exception("not implemented yet")
        is LLMConfig.Anthropic -> throw Exception("not implemented yet")
        is LLMConfig.Huggingface -> throw Exception("not implemented yet")
        is LLMConfig.Local -> throw Exception("not implemented yet")
        else -> throw Exception("unknown llm client config")
    }
}