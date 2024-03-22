package furhatos.app.client.flow

import furhatos.flow.kotlin.*

val Idle : State = state {
    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Chat)
    }

}
