package furhatos.app.client.flow

import furhatos.flow.kotlin.*
import furhatos.app.client.llmClient

val Chat = state(Parent) {
    onEntry {
        delay(2000)
        Furhat.dialogHistory.clear()
        reentry()
    }

    onReentry {
        furhat.listen()
    }

    onResponse("can we stop", "goodbye") {
        furhat.say("Okay, goodbye")
        goto(Idle)
    }

    onResponse {
        furhat.gesture(GazeAversion(2.0))

        val response = llmClient.prompt()
        furhat.say(response)

        reentry()
    }

    onNoResponse {
        reentry()
    }
}
