package furhatos.app.client.prompt.engineering

import furhatos.app.client.config.PromptEngineeringConfig
import furhatos.app.client.prompt.engineering.persona.PersonaPromptEngineering

interface PromptEngineering {
    fun formatPrompt(): String
}

fun loadPromptEngineeringFromConfig(config: PromptEngineeringConfig): PromptEngineering {
    return when(config) {
        is PromptEngineeringConfig.Persona -> PersonaPromptEngineering(config.persona)
        else -> throw Exception("unknown prompt engineering config")
    }
}