package furhatos.app.client

import furhatos.app.client.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import furhatos.app.client.config.Config
import furhatos.app.client.config.FurhatConfig
import furhatos.app.client.config.FurhatInteractionsConfig
import furhatos.app.client.llm.client.none.NoLLMClient
import furhatos.app.client.llm.client.LLMClient
import furhatos.app.client.llm.client.loadLLMClientFromConfig

var llmClient: LLMClient = NoLLMClient()
var furhatConfig: FurhatConfig = FurhatConfig(
    address = "localhost",
    interactions = FurhatInteractionsConfig(
        maxNumberOfUsers = 2,
        distanceToEngage = 1.0,
        listenEndSil = 800
    ))

class ClientSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    val configDir = System.getenv("CONFIG_DIR")


    // Load configuration
    val config = ConfigLoader.Builder().
        addSource(PropertySource.resource(configDir + "/llm_client.yml")).
        addSource(PropertySource.resource(configDir + "/prompt_engineering.yml")).
        addSource(PropertySource.resource(configDir + "/furhat_robot.yml")).
        build().
        loadConfigOrThrow<Config>()

    // Replace default furhat settings by settings from the configuration file
    furhatConfig = config.furhat
    // Replace system property for the furhat robot address by the value from our own configuration
    System.setProperty("furhatos.skills.brokeraddress", furhatConfig.address)
    // Instantiate LLM Client
    llmClient = loadLLMClientFromConfig(config)

    // Launch Furhat Skill
    LogisticMultiIntentClassifier.setAsDefault()
    Skill.main(args)
}
