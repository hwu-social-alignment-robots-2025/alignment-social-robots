package furhatos.app.client.flow

import furhatos.flow.kotlin.*
import furhatos.app.client.llmClient
import furhatos.app.client.config
import furhatos.app.client.monitoring

val Chat = state(Parent) {
    onEntry {
        delay(2000)
        Furhat.dialogHistory.clear()
        reentry()
    }

    onReentry {
        furhat.listen(endSil = config?.furhat?.interactions?.listenEndSil!!)
    }

    onResponse("goodbye") {
        monitoring?.updateDialog()
        furhat.say("Okay, goodbye")
        monitoring?.updateDialog()
        goto(Idle)
    }

    onResponse {
        furhat.gesture(GazeAversion(2.0))
        monitoring?.updateDialog()

        val response = llmClient?.prompt()
        furhat.say(response!!)

        monitoring?.updateDialog()
        reentry()
    }

    onNoResponse {
        reentry()
    }
}
