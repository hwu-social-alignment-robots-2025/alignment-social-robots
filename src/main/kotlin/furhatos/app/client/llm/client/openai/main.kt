package furhatos.app.client.llm.client.openai

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.CompletionRequest
import furhatos.app.client.llm.client.LLMClient
import furhatos.app.client.config.GPTConfig
import furhatos.app.client.prompt.engineering.PromptEngineering
import furhatos.app.client.promptEngineering

class GPTClient(config: GPTConfig, promptEngineering: PromptEngineering) : LLMClient {
    val service = OpenAiService(config.apiKey)

    // Read more about these settings: https://beta.openai.com/docs/introduction
    var model = config.model
    var temperature = 0.9 // Higher values means the model will take more risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
    var maxTokens = 50 // Length of output generated. 1 token is on average ~4 characters or 0.75 words for English text
    var topP = 1.0 // 1.0 is default. An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
    var frequencyPenalty = 0.0 // Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
    var presencePenalty = 0.6 // Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.

    override fun prompt(): String {
        val prompt = promptEngineering?.formatPrompt()

        val completionRequest = CompletionRequest.builder()
            .temperature(temperature)
            .maxTokens(maxTokens)
            .topP(topP)
            .frequencyPenalty(frequencyPenalty)
            .presencePenalty(presencePenalty)
            .prompt(prompt)
            .stop(listOf("User:"))
            .echo(false)
            .model(model)
            .build();

        try {
            val completion = service.createCompletion(completionRequest)
            val response = completion.getChoices().first().text.trim()

            return response
        } catch (e: Exception) {
            println("Problem with connection to OpenAI: " + e.message)
        }

        // Return default error response
        return "I've failed to get my generated response from the OpenAI API"
    }
}
