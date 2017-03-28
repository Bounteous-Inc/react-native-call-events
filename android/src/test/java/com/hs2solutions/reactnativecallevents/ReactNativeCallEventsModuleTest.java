package com.hs2solutions.reactnativecallevents;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jburich on 3/27/17.
 *
 * Test for the Module
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ContextCompat.class, ActivityCompat.class})
public class ReactNativeCallEventsModuleTest {

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

    @Before
    public void setup() {
        when(mockReactContext.getApplicationContext()).thenReturn(mockApplicationContext);
        when(mockApplicationContext.getPackageManager()).thenReturn(mockPackageManager);
        when(mockApplicationContext.getPackageName()).thenReturn(FAKE_PACKAGE_NAME);
        when(mockPackageManager.getLaunchIntentForPackage(FAKE_PACKAGE_NAME)).thenReturn(mockIntent);
        when(mockActivity.getSystemService(Context.TELEPHONY_SERVICE)).thenReturn(mockTelephonyManager);
    }

    private ReactNativeCallEventsModule getInstance() {
        return new ReactNativeCallEventsModule(mockReactContext) {
            @Override
            public Activity _getCurrentActivity() {
                return mockActivity;
            }
        };
    }

    /**
     * This is a kind of odd thing to test but is vital because this is the string that is used
     * in the javascript code to get a reference to this native module
     */
    @Test
    public void getName_Returns_Correct_String_For_JavascriptReference() {
        ReactNativeCallEventsModule instance = getInstance();
        assertThat(instance.getName(), equalTo("ReactNativeCallEvents"));
    }

    @Test
    public void init_Verifies_Permissions_NoPopupWhenGranted() throws Exception {
        ReactNativeCallEventsModule instance = getInstance();

        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.when(ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.READ_PHONE_STATE))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        PowerMockito.mockStatic(ActivityCompat.class);
        PowerMockito.doNothing().when(ActivityCompat.class);
        ActivityCompat.requestPermissions(mockActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        instance.init(false, false);

        PowerMockito.verifyStatic();
        ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.READ_PHONE_STATE);

        PowerMockito.verifyStatic(Mockito.never());
        ActivityCompat.requestPermissions(Mockito.any(Activity.class), Mockito.any(String[].class), Mockito.anyInt());
    }

    @Test
    public void init_Verifies_Permissions_PopupWhenNotYetGranted() throws Exception {
        ReactNativeCallEventsModule instance = getInstance();

        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.when(ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.READ_PHONE_STATE))
                .thenReturn(PackageManager.PERMISSION_DENIED);

        PowerMockito.mockStatic(ActivityCompat.class);
        PowerMockito.doNothing().when(ActivityCompat.class);
        ActivityCompat.requestPermissions(mockActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        instance.init(false, false);

        PowerMockito.verifyStatic();
        ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.READ_PHONE_STATE);

        PowerMockito.verifyStatic();
        ActivityCompat.requestPermissions(mockActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
    }


    @Test
    public void init_SetsUpListener() throws Exception {
        ReactNativeCallEventsModule instance = getInstance();

        //skip past the permissions check
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.when(ContextCompat.checkSelfPermission(mockActivity, Manifest.permission.READ_PHONE_STATE))
                .thenReturn(PackageManager.PERMISSION_GRANTED);

        PowerMockito.mockStatic(ActivityCompat.class);
        PowerMockito.doNothing().when(ActivityCompat.class);
        ActivityCompat.requestPermissions(mockActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

        instance.init(true, false);

        verify(mockTelephonyManager);
        mockTelephonyManager.listen(new CallStateListener(true, false, mockReactContext), PhoneStateListener.LISTEN_CALL_STATE);
    }
}