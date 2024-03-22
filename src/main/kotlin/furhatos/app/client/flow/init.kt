package furhatos.app.client.flow

import furhatos.app.client.furhatConfig
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val Init: State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(furhatConfig.interactions.distanceToEngage,
            furhatConfig.interactions.maxNumberOfUsers)

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


