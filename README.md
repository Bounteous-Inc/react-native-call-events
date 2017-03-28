# react-native-call-events
A component to get call events fired back to your application.

Currently this works only on Android.

## Installation
```
yarn add react-native-call-events
```

## Usage
Initialize the listeners with
``` javascript
import RNCallEvent from 'react-native-call-events'
RNCallEvent.init(false, false);
```

Init Parameters:

Parameter | Description
--- | ---
returnOnCall | Return to the calling app when a phone call begins. Note: this currently does not work with a call that goes to voicemail.
returnOnEnd | Return to the calling app when a phone call ends

## Events

###callStatusUpdate
Will be fired with every call status change

```javascript
    DeviceEventEmitter.addListener('callStatusUpdate', event => {
      console.log(`Event: ${event}`);
    });
```

Event Object Properties

Property | Description
--- | ---
type | A string that will be one of: CALL_STATE_OFFHOOK, CALL_STATE_RINGING, or CALL_STATE_END
phonenumber | Will be populated on both offhook and ringing, but most devices do not populate on end