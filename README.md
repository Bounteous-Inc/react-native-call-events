# react-native-call-events
A component to get call events fired back to your application.

Currently this works only on Android.

## Usage
Initialize the listners with
```
import RNCallEvent from 'react-native-call-events'
RNCallEvent.init(false, false);
```
Init Parameters:
returnOnCall | Return to the calling app when a phone call begins. Note: this currently does not work with a call that goes to voicemail.
returnOnEnd | Return to the calling app when a phone call ends
