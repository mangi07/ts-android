Translation Studio 2.0
--

translationStudio for unfoldingWord


###Contributing
Below are the technical specifications regarding this Android app and instructions for setting up your development environment.

* **Minimum SDK**: 15 (4.0.3 Ice Cream Sandwich)
* **Target Devices**: Nexus 7, 10 Tablets
* **IDE**: [Android Studio]
* **Emulator**: [Genymotion] (way better than the default android emulator)

Before you can start developing you must install [Android Studio]. 15 is currently the standard minimum sdk verion so it is likely you will have it once [Android Studio] has been installed. If not then you will need to download it following the instructions in [Adding SDK Packages].

Next install [Genymotion] and build an emulator. There are a few different licenses available, just choose the free license. Once installed the interface is pretty straight forward so if you experience difficulty setting up an emulator please refer to their documentation.

>NOTE: [Genymotion] requires [Virtual Box] as a dependency. You may pre-install [Virtual Box] or download the [Genymotion] installer prepackaged with [Virtual Box].

For added convenience you may download the [Genymotion Android Studio Plugin]. This will add a button next to the default AVD Manager button in your [Android Studio] menu bar. From there you can create new emulators or start existing ones.

>NOTE: I have found that the [Genymotion] emulators must be started before running the application in Android Studio.

Now you are ready to develop! Run the app in debug mode (the bug button) and watch the app pop up on your emulator. 

###Third Party Libraries
* [Sliding Layer] by 6Wunderkinder

###REST API
Translation Studio 2.0 utilizes a REST API to suppliment the resources packaged with the distributed apk. If an active internet connection exists the app will look for updates using this api, otherwise it will continue to use the packaged resources.
The API specification can be viewed at the [Unfolding Word API](https://door43.org/en/dev/api/unfoldingword)

###Object Organization
Below is an explaination of some of the primary objects within the app.

####Project
A project encapsulates a specific translation effort regardless of language. The app has a set of predefined projects including starting data that will be augmented by data found on the server (if an active internect connection is available). Projects may contain 0 or more Chapters.

####Chapter
A chapter encapsulates a specific set translation Frames regardless of of language. Chapters mostly act to better organize the translation effort into sections for better navigation. Chapters may containe 0 or more frames.

####Frame
A frame encapsulates a specific piece of translated work. Frames include additional translation information such as language. This is the lowest level of translation (just above the text file).

[Virtual Box]:https://www.virtualbox.org/
[Genymotion Android Studio Plugin]:https://cloud.genymotion.com/page/doc/#collapse-intellij
[Adding SDK Packages]:http://developer.android.com/sdk/installing/adding-packages.html
[Sliding Layer]:https://github.com/6wunderkinder/android-sliding-layer-lib
[Genymotion]:http://www.genymotion.com/
[Android Studio]:https://developer.android.com/sdk/installing/studio.html