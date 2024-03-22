package furhatos.app.client.llm.client.none

import furhatos.app.client.llm.client.LLMClient

// Default LLMClient which throw an error when used
class NoLLMClient : LLMClient {
    override fun prompt(): String {
        throw Exception("no llm client provided")
    }
}