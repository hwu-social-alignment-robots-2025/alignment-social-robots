package furhatos.app.client.monitoring

import com.google.gson.Gson
import furhatos.app.client.config.MonitoringConfig
import furhatos.app.client.prompt.engineering.PromptEngineering
import furhatos.app.client.monitoring.architecture.*
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat
import java.io.File
import java.util.UUID

class Monitoring(config: MonitoringConfig, promptEngineering: PromptEngineering) {
    val gson = Gson()
    val uuid = UUID.randomUUID().toString()
    val filepath = "${config.outputDir}/${uuid}.json"
    val outputFile = File(filepath)
    val content = MonitoringContent(
        uuid,
        config.participant,
        promptEngineering,
        mutableListOf<DialogLine>()
    )

    init {
        // Write in file to create it
        outputFile.writeText(gson.toJson(content))
    }

    fun updateDialog() {
        // Retrieve and format the dialog history into a single string
        val history = Furhat.dialogHistory.all.takeLast(1).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    content.dialog.add(DialogLine("participant", it.response.text))
                }
                is DialogHistory.UtteranceItem -> {
                    content.dialog.add(DialogLine("assistant", it.toText()))
                }
                else -> null
            }
        }.joinToString(separator = "\n")

        outputFile.writeText(gson.toJson(content))
    }
}
