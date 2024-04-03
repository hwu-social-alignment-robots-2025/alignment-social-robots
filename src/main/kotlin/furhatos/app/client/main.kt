package furhatos.app.client

import furhatos.app.client.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier
import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.PropertySource
import furhatos.app.client.config.Config
import furhatos.app.client.llm.client.LLMClient
import furhatos.app.client.llm.client.loadLLMClientFromConfig
import furhatos.app.client.monitoring.Monitoring
import furhatos.app.client.prompt.engineering.PromptEngineering
import furhatos.app.client.prompt.engineering.loadPromptEngineeringFromConfig

var llmClient: LLMClient? = null
var monitoring: Monitoring? = null
var config: Config? = null
var promptEngineering: PromptEngineering? = null

class ClientSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    val llmClientCfgFile = System.getenv("LLM_CLIENT_CONFIG") ?: "/llm_client.yml"
    val promptEngineeringCfgFile = System.getenv("PROMPT_ENGINEERING_CONFIG") ?: "/prompt_engineering/control.yml"
    val furhatRobotCfgFile = System.getenv("FURHAT_ROBOT_CONFIG") ?: "/furhat_robot.yml"
    val monitoringCfgFile = System.getenv("MONITORING_CONFIG") ?: "/monitoring.yml"

    // Load configuration
    config = ConfigLoader.Builder().
        addSource(PropertySource.resource(llmClientCfgFile)).
        addSource(PropertySource.resource(promptEngineeringCfgFile)).
        addSource(PropertySource.resource(furhatRobotCfgFile)).
        addSource(PropertySource.resource(monitoringCfgFile)).
        build().
        loadConfigOrThrow<Config>()

    // Replace system property for the furhat robot address by the value from our own configuration
    System.setProperty("furhatos.skills.brokeraddress", config?.furhat?.address!!)
    promptEngineering = loadPromptEngineeringFromConfig(config?.promptEngineering!!)
    // Instantiate LLM Client
    llmClient = loadLLMClientFromConfig(config!!, promptEngineering!!)
    // Initialize Monitoring
    monitoring = Monitoring(config?.monitoring!!, promptEngineering!!)

    // Launch Furhat Skill
    LogisticMultiIntentClassifier.setAsDefault()
    Skill.main(args)
}
