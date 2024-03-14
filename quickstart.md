# Quickstart
You'll find here a guide to run the furhat client with the JetBrains IntelliJ IDE.

## Prerequisites
To launch the Furhat client you will need:
- The [Furhat SDk](https://docs.furhat.io/getting_started/#installation) installed
- The [JetBrains IntelliJ IDE](https://www.jetbrains.com/idea/download/) installed (either the ultimate or community edition but I would recommend the community edition as it is free).
- An API key for the chatGPT API (if you don't already have it asks to Guilhem Santé)
- No VPN running (it will prevents the connection between the Furhat SDK and the client)


## Demo
Here a full-demo of the procedure:

[demo](https://github.com/hwu-social-alignment-robots-2025/alignment-social-robots/raw/main/res/video/demo.mp4)

**Warning**: the video actually occults the mandatory step of the definition of the chatGPT API key in the environment variable (to prevents it leaks). Please refer to the associated section of this document to proceed ([here](#define-the-api-key-as-an-environment-variable)).

## Step-by-step

### Run the Furhat SDK
First run the Furhat SDK. And open the web interface from the `open web interface` button of the SDK, your default web browser should open itself automatically on the right page. A password will be asked, fill the `admin` default password.

### Define the API Key as an environment variable
Next open the IntelliJ IDE. You will need to define the chatGPT API key as the environment variable `API_KEY` to enable the client to interact with the chatGPT API. To do so open the `run` tab of the IntelliJ IDE (which should open itself while trying to run the code), and then select the `Modify Run Configuration` in the `More` option. After that you should be able to define the `API_KEY` environment variable from the `Environment variable`section of the configuration.

[demo](https://github.com/hwu-social-alignment-robots-2025/alignment-social-robots/raw/main/res/video/environment.mp4)

### Connect the client with the Furhat SDK
Next you can run the code by opening the `src/main/kotlin/furhatos/app/openaichat/main.kt` and clicking the play button in the IntelliJ IDE still. You can confirm that the client is connected to the Furhat SDK by checking the dashboard of the SDK web interface, a `Running Skill - Remote Skill`canva should appear in the up side of the interface.

### Create a virtual user and approach it to the robot
Finally in the Furhat SDK interface dashboard, you can create a virtual user by clicking anywhere on the side view of the robot environment. Ensure the virtual user is sufficiently close for the robot to engage it.

[demo](https://github.com/hwu-social-alignment-robots-2025/alignment-social-robots/raw/main/res/video/virtual-user.mp4)

And that's it! The robot should then listen to your microphone and try to respond from the chatGPT output. You can keep track of the conversation on the Dashboard from the Furhat SDK web interface.

[demo](https://github.com/hwu-social-alignment-robots-2025/alignment-social-robots/raw/main/res/video/chat.mp4)
