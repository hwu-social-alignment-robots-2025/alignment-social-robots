package furhatos.app.client.config

import furhatos.app.client.prompt.engineering.PromptEngineering

// The config of the whole Furhat Skills client to be loaded from a configuration by the hoplite library
data class Config(val furhat: FurhatConfig, val llm: LLMConfig, val promptEngineering: PromptEngineeringConfig)

// The subconfig for settings related to the Furhat robot exclusively (either it be the SDK or the actual robot)
data class FurhatConfig(val address: String, val interactions: FurhatInteractionsConfig)

// The subconfig for the Furhat robot interactions
data class FurhatInteractionsConfig(val maxNumberOfUsers: Int, val distanceToEngage: Float)

// The subconfig of the LLM client configuration
sealed class LLMConfig {
    data class Openai(val openai: GPTConfig) : LLMConfig()
    data class Google(val google: GeminiConfig) : LLMConfig()
    data class Anthropic(val anthropic: ClaudeConfig) : LLMConfig()
    data class Huggingface(val huggingface: HuggingfaceOpenSourceModelConfig) : LLMConfig()
    data class Local(val local: LocalOpenSourceModelConfig) : LLMConfig()
}

// The subconfig for the Openai LLM client configuration
data class GPTConfig(val apiKey: String, val model: String)

// The subconfig for the Google LLM client configuration
data class GeminiConfig(val apiKey: String, val model: String)

// The subconfig for the Anthropic LLM client configuration
data class ClaudeConfig(val apiKey: String, val model: String)

// The subconfig for the Huggingface LLM client configuration
data class HuggingfaceOpenSourceModelConfig(val apiKey: String, val model: String)

// The subconfig for any LLM running on the local machine
data class LocalOpenSourceModelConfig(val port: Int)

// The subconfig for the prompt engineering config (meaning how does the LLM will be queried)
sealed class PromptEngineeringConfig {
    data class Persona(val persona: PersonaPromptEngineeringConfig) : PromptEngineeringConfig()
}

// The subconfig of the persona prompt engineering
data class PersonaPromptEngineeringConfig(val description: PersonaConfig, val context: ContextConfig)

// The subconfig for the persona description
data class PersonaConfig(val name: String, val traits: String, val synonyms: List<String>, val refine: RefineConfig)

// The subconfig for the persona refining
data class RefineConfig(val chainOfThought: String)

// The subconfig that describe the context in which the persona will interact
data class ContextConfig(val task: String)
