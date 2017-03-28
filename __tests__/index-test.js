import RNCallEvents from '../'
import { NativeModules, DeviceEventEmitter } from 'react-native';

const { ReactNativeCallEvents } = NativeModules;

it('should initialize the native module with default values', ()=>{
  RNCallEvents.init();
  expect(ReactNativeCallEvents.init).toHaveBeenCalledWith(false, false);
});

it('should initialize with returnOnCall true', ()=>{
  RNCallEvents.init({returnOnCall: true});
  expect(ReactNativeCallEvents.init).toHaveBeenCalledWith(true, false);
});

it('should initialize with returnOnEnd true', ()=>{
  RNCallEvents.init({returnOnEnd: true});
  expect(ReactNativeCallEvents.init).toHaveBeenCalledWith(false, true);
});

it('should initialize with returnOnCall and returnOnEnd true', ()=>{
  RNCallEvents.init({returnOnCall: true, returnOnEnd: true});
  expect(ReactNativeCallEvents.init).toHaveBeenCalledWith(true, true);
});

it('should register a dispatcher function', ()=>{
  const mockDispatcher = jest.fn();
  RNCallEvents.init({dispatch: mockDispatcher});

  const mockAddListener = DeviceEventEmitter.addListener.mock.calls[0];
  expect(mockAddListener[0]).toEqual('callStatusUpdate');

  //invoke the listener's event to test the mock dispatcher was set up correctly
  mockAddListener[1]({state: 'some call state', phonenumber: '8675309'});

  expect(mockDispatcher).toHaveBeenCalledWith({
    type: 'some call state',
    payload: { phonenumber: '8675309'}
  })
});