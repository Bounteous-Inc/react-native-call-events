const rn = require('react-native');

jest.mock('NativeModules', () => ({
    ReactNativeCallEvents: {
      init: jest.fn()
    }
}));

jest.mock('RCTDeviceEventEmitter', () => ({
  addListener: jest.fn()
}));

module.exports = rn;