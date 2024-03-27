package furhatos.app.client.monitoring.architecture

import furhatos.app.client.config.ParticipantInformation
import furhatos.app.client.prompt.engineering.PromptEngineering

/*
    Describe the architecture of the monitoring output file.
 */

data class MonitoringContent(
    var uuid: String,
    var participant: ParticipantInformation,
    var promptEngineering: PromptEngineering,
    var dialog: MutableList<DialogLine>,
)

data class DialogLine(
    var actor: String,
    var line: String
)
