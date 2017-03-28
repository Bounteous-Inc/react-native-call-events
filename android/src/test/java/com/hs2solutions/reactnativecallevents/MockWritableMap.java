package com.hs2solutions.reactnativecallevents;


import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jburich on 3/27/17.
 *
 * A Testable WriteableMap since I can't even get the Arguments.createMap to mock properly.
 */

class MockWritableMap implements WritableMap {
    private Map values = new HashMap();
    @Override
    public void putNull(String key) {
        values.put(key, null);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        values.put(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        values.put(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        values.put(key, value);
    }

    @Override
    public void putString(String key, String value) {
        values.put(key, value);
    }

    @Override
    public void putArray(String key, WritableArray value) {
        values.put(key, value);
    }

    @Override
    public void putMap(String key, WritableMap value) {
        values.put(key, value);
    }

    @Override
    public void merge(ReadableMap source) {
        throw new RuntimeException("Not Implemented in Mock Yet");
    }

    @Override
    public boolean hasKey(String name) {
        return values.containsKey(name);
    }

    @Override
    public boolean isNull(String name) {
        return values.get(name) == null;
    }

    @Override
    public boolean getBoolean(String name) {
        return (boolean) values.get(name);
    }

    @Override
    public double getDouble(String name) {
        return (double) values.get(name);
    }

    @Override
    public int getInt(String name) {
        return (int) values.get(name);
    }

    @Override
    public String getString(String name) {
        return (String) values.get(name);
    }

    @Override
    public ReadableArray getArray(String name) {
        return (ReadableArray) values.get(name);
    }

    @Override
    public ReadableMap getMap(String name) {
        return (ReadableMap) values.get(name);
    }

    @Override
    public ReadableType getType(String name) {
        return (ReadableType) values.get(name);
    }

    @Override
    public ReadableMapKeySetIterator keySetIterator() {
        throw new RuntimeException("Not yet implemented in mock");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockWritableMap that = (MockWritableMap) o;

        return values != null ? values.equals(that.values) : that.values == null;

    }

    @Override
    public int hashCode() {
        return values != null ? values.hashCode() : 0;
    }
}
