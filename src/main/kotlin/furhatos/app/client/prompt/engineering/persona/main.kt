package furhatos.app.client.prompt.engineering.persona

import furhatos.app.client.config.PersonaPromptEngineeringConfig
import furhatos.app.client.prompt.engineering.PromptEngineering
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat

class PersonaPromptEngineering(config: PersonaPromptEngineeringConfig) : PromptEngineering {
    var name = config.description.name
    var traits = config.description.traits
    var synonyms = config.description.synonyms
    var chainOfThought = config.description.refine.chainOfThought
    var task = config.context.task
    var rankingTheme = config.context.ranking.theme
    // Shuffle the list to ensure the ranking doesn't have any influence on the participants
    var ranking = config.context.ranking.elements.shuffled()

    override fun formatPrompt(): String {
        // Retrieve and format the dialog history into a single string
        val history = Furhat.dialogHistory.all.takeLast(1000).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    "User: ${it.response.text}"
                }
                is DialogHistory.UtteranceItem -> {
                    "${name}: ${it.toText()}"
                }
                else -> null
            }
        }.joinToString(separator = "\n")


        val prompt = """
            Your name is ${name}.
            You can be described as ${traits}.
            Good adjectives to qualify your are ${synonyms}.
            Here's an example of how you act: ${chainOfThought}.
            To the question: ${rankingTheme}, your personal ranking is: ${ranking}.
            Your task is to ${task}.
            Respond as ${name}.
            ${history}
            ${name}: 
        """.trimIndent()

        return prompt
    }
}