package com.hs2solutions.reactnativecallevents;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by jburich on 3/25/17.
 *
 * A Module that will listen for call state changes and fire appropriate Javascript events.
 *
 * When the Call State changes this class will:
 *  1) Always fire a Javascript event with the names of the event and phonenumber.
 *     * Note CALL_STATE_END instead of CALL_STATE_IDLE to be more cross platform compliant
 *  2) If the returnOnCall/returnOnEnd flags are falls and the call state changes to those this
 *     will automatically relaunch the application.
 */

public class ReactNativeCallEventsModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;

    public ReactNativeCallEventsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    public Activity _getCurrentActivity() {
        return this.getCurrentActivity();
    }

    @Override
    public String getName() {
        return "ReactNativeCallEvents";
    }

    @ReactMethod
    public void init(final Boolean returnOnCall, final Boolean returnOnEnd) {
        int permissionCheck = ContextCompat.checkSelfPermission(this._getCurrentActivity(), Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this._getCurrentActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        ((TelephonyManager) this._getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE))
                .listen(new CallStateListener(returnOnCall, returnOnEnd, reactContext),
                PhoneStateListener.LISTEN_CALL_STATE);
    }

}
