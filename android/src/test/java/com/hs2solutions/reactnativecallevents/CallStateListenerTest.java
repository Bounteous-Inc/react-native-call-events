package com.hs2solutions.reactnativecallevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jburich on 3/27/17.
 *
 * Tests for the CallStateListener
 */
@RunWith(MockitoJUnitRunner.class)
public class CallStateListenerTest {
    private static final String FAKE_PACKAGE_NAME = "fake.package";

    @Mock
    ReactApplicationContext mockReactContext;

    @Mock
    Activity mockActivity;

    @Mock
    Context mockApplicationContext;

    @Mock
    PackageManager mockPackageManager;

    @Mock
    Intent mockIntent;

    @Mock
    TelephonyManager mockTelephonyManager;

    @Mock
    DeviceEventManagerModule.RCTDeviceEventEmitter mockEmitter;

    @Before
    public void setup() {
        when(mockReactContext.getApplicationContext()).thenReturn(mockApplicationContext);
        when(mockApplicationContext.getPackageManager()).thenReturn(mockPackageManager);
        when(mockApplicationContext.getPackageName()).thenReturn(FAKE_PACKAGE_NAME);
        when(mockPackageManager.getLaunchIntentForPackage(FAKE_PACKAGE_NAME)).thenReturn(mockIntent);
        when(mockActivity.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(mockTelephonyManager);
        when(mockReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).thenReturn(mockEmitter);
    }

    private CallStateListener getInstance(boolean returnOnCall, boolean returnOnEnd, ReactApplicationContext reactContext) {
        return new CallStateListener(returnOnCall, returnOnEnd, reactContext) {
            @Override
            public WritableMap createMap() {
                return new MockWritableMap();
            }
        };
    }

    @Test
    public void onCallStateChanged_CALL_STATE_OFFHOOK_returnOnCallFalse() {
        CallStateListener instance = getInstance(false, true, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_OFFHOOK, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_OFFHOOK");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should not attempt to launch the app
        verify(mockApplicationContext, never()).startActivity(any(Intent.class));
    }

    @Test
    public void onCallStateChanged_CALL_STATE_OFFHOOK_returnOnCallTrue() {
        CallStateListener instance = getInstance(true, false, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_OFFHOOK, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_OFFHOOK");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should launch the app
        verify(mockApplicationContext).startActivity(mockIntent);
    }

    @Test
    public void onCallStateChanged_CALL_STATE_RINGING_returnOnCallFalse() {
        CallStateListener instance = getInstance(false, true, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_RINGING");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should not attempt to launch the app
        verify(mockApplicationContext, never()).startActivity(any(Intent.class));
    }

    @Test
    public void onCallStateChanged_CALL_STATE_RINGING_returnOnCallTrue() {
        CallStateListener instance = getInstance(true, false, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_RINGING, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_RINGING");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should launch the app
        verify(mockApplicationContext).startActivity(mockIntent);
    }

    @Test
    public void onCallStateChanged_CALL_STATE_IDLE_returnOnEndFalse() {
        CallStateListener instance = getInstance(true, false, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_IDLE, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_END");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should not attempt to launch the app
        verify(mockApplicationContext, never()).startActivity(any(Intent.class));
    }

    @Test
    public void onCallStateChanged_CALL_STATE_IDLE_returnOnEndTrue() {
        CallStateListener instance = getInstance(false, true, mockReactContext);

        instance.onCallStateChanged(TelephonyManager.CALL_STATE_IDLE, "8675309");

        //always set the return flags
        verify(mockIntent).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //Should always fire the event back to Javascript
        WritableMap expected = new MockWritableMap();
        expected.putString("phonenumber", "8675309");
        expected.putString("state", "CALL_STATE_END");
        verify(mockEmitter).emit("callStatusUpdate", expected);

        //should launch the app
        verify(mockApplicationContext).startActivity(mockIntent);
    }

}