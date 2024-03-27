package furhatos.app.client.prompt.engineering.vanilla

import furhatos.app.client.config.VanillaPromptEngineeringConfig
import furhatos.app.client.prompt.engineering.PromptEngineering
import furhatos.app.client.prompt.engineering.persona.Ranking
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat

// The prompting technique when no persona is required to discuss with the participant
class VanillaPromptEngineering(config: VanillaPromptEngineeringConfig) : PromptEngineering {
    var task = config.context.task
    var rankings = config.context.rankings.map { ranking ->
        // Shuffle the list to ensure the ranking doesn't have any influence on the participants
        if (ranking.shuffled) {
            Ranking(ranking.theme, ranking.elements.shuffled())
        } else {
            Ranking(ranking.theme, ranking.elements)
        }
    }

    override fun formatPrompt(): String {
        // Retrieve and format the dialog history into a single string
        val history = Furhat.dialogHistory.all.takeLast(1000).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    "User: ${it.response.text}"
                }
                is DialogHistory.UtteranceItem -> {
                    "Assistant: ${it.toText()}"
                }
                else -> null
            }
        }.joinToString(separator = "\n")

        val rankingsPrompt = rankings.map { ranking ->
            "To the question: ${ranking.theme}, your own ranking is: ${ranking.elements}."
        }

        val prompt = """
            ${rankingsPrompt}.
            Your task is to ${task}.
            Respond as Assistant.
            ${history}
            Assistant: 
        """.trimIndent()

        return prompt
    }
}
