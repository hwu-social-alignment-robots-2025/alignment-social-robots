package furhatos.app.client.monitoring.architecture

import furhatos.app.client.config.ParticipantInformation
import furhatos.app.client.prompt.engineering.PromptEngineering

/*
    Describe the architecture of the monitoring output file.
 */

data class MonitoringContent(
    var metadata: MonitoringMetadata,
    var promptEngineering: PromptEngineering,
    var dialog: MutableList<DialogLine>,
)

data class MonitoringMetadata(
    var uuid: String,
    var comment: String?,
    var timestamp: String?,
    var participant: ParticipantInformation,
)

data class DialogLine(
    var actor: String,
    var line: String
)
