package com.hs2solutions.reactnativecallevents;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Created by jburich on 3/27/17.
 *
 * When the Call State changes this class will:
 *  1) Always fire a Javascript event with the names of the event and phonenumber.
 *     * Note CALL_STATE_END instead of CALL_STATE_IDLE to be more cross platform compliant
 *  2) If the returnOnCall/returnOnEnd flags are falls and the call state changes to those this
 *     will automatically relaunch the application.
 */
class CallStateListener extends PhoneStateListener {

    private boolean returnOnCall;
    private boolean returnOnEnd;
    private ReactApplicationContext reactContext;

    CallStateListener(boolean returnOnCall, boolean returnOnEnd, ReactApplicationContext reactContext) {
        this.returnOnCall = returnOnCall;
        this.returnOnEnd = returnOnEnd;
        this.reactContext = reactContext;
    }

    public WritableMap createMap() {
        return Arguments.createMap();
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        final Context context = reactContext.getApplicationContext();
        final Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        WritableMap params = this.createMap();
        params.putString("phonenumber", incomingNumber);
        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            params.putString("state", "CALL_STATE_OFFHOOK");
            if( returnOnCall ) {
                context.startActivity(i);
            }
        }
        if (TelephonyManager.CALL_STATE_RINGING == state) {
            params.putString("state", "CALL_STATE_RINGING");
            if( returnOnCall ) {
                context.startActivity(i);
            }
        }
        if (TelephonyManager.CALL_STATE_IDLE == state) {
            params.putString("state", "CALL_STATE_END");
            if ( returnOnEnd ) {
                context.startActivity(i);
            }
        }

        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("callStatusUpdate", params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallStateListener that = (CallStateListener) o;

        return returnOnCall == that.returnOnCall && returnOnEnd == that.returnOnEnd && reactContext.equals(that.reactContext);
    }

    @Override
    public int hashCode() {
        int result = (returnOnCall ? 1 : 0);
        result = 31 * result + (returnOnEnd ? 1 : 0);
        result = 31 * result + reactContext.hashCode();
        return result;
    }
}
