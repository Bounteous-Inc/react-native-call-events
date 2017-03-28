package com.hs2solutions.reactnativecallevents;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by jburich on 3/26/17.
 *
 * Tests for the package
 */
@RunWith(MockitoJUnitRunner.class)
public class ReactNativeCallEventsPackageTest extends TestCase {

    @Mock
    ReactApplicationContext reactContext;

    @Test
    public void testCreateNativeModules() throws Exception {
        ReactNativeCallEventsPackage instance = new ReactNativeCallEventsPackage();
        List<NativeModule> results = instance.createNativeModules(reactContext);
        ReactNativeCallEventsModule result = (ReactNativeCallEventsModule) results.get(0);
        assertThat(result, not(equalTo(null)));
    }

    @Test
    public void testCreateJSModules() throws Exception {
        ReactNativeCallEventsPackage instance = new ReactNativeCallEventsPackage();
        assertThat(instance.createJSModules().size(), equalTo(0));
    }

    @Test
    public void testCreateViewManagers() throws Exception {
        ReactNativeCallEventsPackage instance = new ReactNativeCallEventsPackage();
        assertThat(instance.createViewManagers(reactContext).size(), equalTo(0));
    }

}