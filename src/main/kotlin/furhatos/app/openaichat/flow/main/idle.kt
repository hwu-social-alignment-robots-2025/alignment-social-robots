package furhatos.app.openaichat.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.records.Location

val Idle : State = state {
    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Chat)
    }

}
