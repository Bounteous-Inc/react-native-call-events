import { NativeModules } from 'react-native';
import { NativeAppEventEmitter, DeviceEventEmitter } from 'react-native';

const { ReactNativeCallEvents } = NativeModules;

const RNCallEvents = {};

RNCallEvents.init = config => {
  if ( config && config.dispatch && typeof config.dispatch === 'function') {
    DeviceEventEmitter.addListener('callStatusUpdate', event => {
      config.dispatch({
        type: event.state,
        payload: {
          phonenumber: event.phonenumber
        }
      })
    });
  }
  return ReactNativeCallEvents.init( (config && config.returnOnCall) || false, (config && config.returnOnEnd) || false);
};

export default RNCallEvents;

