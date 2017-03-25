import { NativeModules } from 'react-native';
import { NativeAppEventEmitter } from 'react-native';

const { ReactNativeCallEvents } = NativeModules;

const RNCallEvents = {}

RNCallEvents.init = ( returnOnCall, returnOnEnd ) => {
  return ReactNativeCallEvents.init(returnOnCall, returnOnEnd);
}

const eventsMap = {};

export default RNCallEvents;

