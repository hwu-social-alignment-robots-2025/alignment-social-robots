package furhatos.app.openaichat.flow.llm.chatbot.openai
import com.google.gson.Gson
import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.CompletionRequest
import furhatos.app.openaichat.flow.llm.chatbot.Chatbot
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat
import java.io.File

class OpenAIChatbot(serviceKey: String) : Chatbot {
    val gson = Gson();
    val json_path = "src\\main\\kotlin\\furhatos\\app\\openaichat\\flow\\llm\\chatbot\\openai\\context.json"
    val contextFile = File(json_path)
    val context = gson.fromJson(contextFile.readText(), Context::class.java)
    val service = OpenAiService(serviceKey)

    // Read more about these settings: https://beta.openai.com/docs/introduction
    var temperature = 0.9 // Higher values means the model will take more risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
    var maxTokens = 50 // Length of output generated. 1 token is on average ~4 characters or 0.75 words for English text
    var topP = 1.0 // 1.0 is default. An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
    var frequencyPenalty = 0.0 // Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
    var presencePenalty = 0.6 // Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.




    fun addUserMsg(userMsg:String)
    {
        context.UserLastMsg = userMsg
        var contextjson = gson.toJson(context)
        contextFile.writeText(contextjson)
    }
    fun addAgentMsg(agentMsg:String)
    {
        context.AgentLastMsg = agentMsg
        var contextjson = gson.toJson(context)
        contextFile.writeText(contextjson)
    }

    fun addContext()
    {
        val prompt : String = "Having a conversation with this context: ${context.context} " +
                "and the last message of the agent is ${context.AgentLastMsg}  " +
                "and the last message of the user is ${context.UserLastMsg}, update the new context," +
                "try to improve the context with the message of the agent and the message with the user"
        val completionRequest = CompletionRequest.builder()
                .temperature(temperature)
                .maxTokens(maxTokens)
                .topP(topP)
                .frequencyPenalty(frequencyPenalty)
                .presencePenalty(presencePenalty)
                .prompt(prompt)
                .stop(listOf("User:"))
                .echo(false)
                .model("gpt-3.5-turbo-instruct")
                .build();

        val completion = service.createCompletion(completionRequest)
        val response = completion.getChoices().first().text.trim()
        context.context = response
        var contextjson = gson.toJson(context)
        contextFile.writeText(contextjson)
    }


    override fun prompt(): String {
        /** The prompt for the chatbot includes a context of ten "lines" of dialogue. **/
        val response = Furhat.dialogHistory.responses.lastOrNull()?.response
        if (response != null) {
            println(response.text)
            addUserMsg(response.text)
        }

        val history = Furhat.dialogHistory.all.takeLast(10).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    "User: ${it.response.text}"
                }
                is DialogHistory.UtteranceItem -> {
                    "Agent: ${it.toText()}"
                }
                else -> null
            }
        }.joinToString(separator = "\n")
        val prompt = " \n\n$history\nAgent:"

        val completionRequest = CompletionRequest.builder()
            .temperature(temperature)
            .maxTokens(maxTokens)
            .topP(topP)
            .frequencyPenalty(frequencyPenalty)
            .presencePenalty(presencePenalty)
            .prompt(prompt)
            .stop(listOf("User:"))
            .echo(false)
            .model("gpt-3.5-turbo-instruct")
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
