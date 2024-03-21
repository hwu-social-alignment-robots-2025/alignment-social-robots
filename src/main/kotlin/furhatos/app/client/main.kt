package furhatos.app.client

import furhatos.app.client.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import furhatos.app.client.config.Config
import furhatos.app.client.llm.client.none.NoLLMClient
import furhatos.app.client.llm.client.LLMClient
import furhatos.app.client.llm.client.loadLLMClientFromConfig

var llmClient: LLMClient = NoLLMClient()

class ClientSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    // Load configuration
    val config = ConfigLoader.Builder().
        addSource(PropertySource.resource("/llm_client.yml")).
        addSource(PropertySource.resource("/prompt_engineering.yml")).
        addSource(PropertySource.resource("/furhat_robot.yml")).
        build().
        loadConfigOrThrow<Config>()

    // Instantiate LLM Client
    llmClient = loadLLMClientFromConfig(config)

    // Launch Furhat Skill
    LogisticMultiIntentClassifier.setAsDefault()
    Skill.main(args)
}
