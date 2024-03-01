package furhatos.app.openaichat.flow

import furhatos.app.openaichat.flow.llm.chatbot.openai.OpenAIChatbot
import furhatos.app.openaichat.setting.distanceToEngage
import furhatos.app.openaichat.setting.maxNumberOfUsers
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val Init: State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)

        /** start the interaction */
        goto(InitFlow)
    }

}

val InitFlow: State = state() {
    onEntry {
        when {
            users.hasAny() -> goto(Chat)
            else -> goto(Idle)
        }
    }

}


