package com.hs2solutions.reactnativecallevents;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by jburich on 3/25/17.
 */

public class ReactdNativeCallEventsModule extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;
    public ReactdNativeCallEventsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeCallEvents";
    }

    @ReactMethod
    public void init(final Boolean returnOnCall, final Boolean returnOnEnd) {
        int permissionCheck = ContextCompat.checkSelfPermission(this.getCurrentActivity(), Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getCurrentActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //TODO
        }
        final Context context = reactContext.getApplicationContext();
        //when this state occurs, and your flag is set, restart your app
        final Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        //For resuming the application from the previous state
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        ((TelephonyManager) this.getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE)).listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                Log.d("JJB", "Call State Change: " + state + " incoming number: " + incomingNumber + " compared to TelephonyManager.CALL_STATE_RINGING=" + TelephonyManager.CALL_STATE_RINGING );
                if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                    if( returnOnCall ) {
                        context.startActivity(i);
                    }
                }
                if (TelephonyManager.CALL_STATE_RINGING == state) {
                    if( returnOnCall ) {
                        context.startActivity(i);
                    }
                }
                if (TelephonyManager.CALL_STATE_IDLE == state) {
                    if ( returnOnEnd ) {
                        context.startActivity(i);
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
