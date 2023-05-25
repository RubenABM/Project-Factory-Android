package com.myapplication;

import java.util.HashMap;

public class DataHolder {
    private static DataHolder instance;
    private HashMap<String, String> dataMap;

    private DataHolder() {
        // Private constructor to prevent instantiation
        dataMap = new HashMap<>();
    }

    public static synchronized DataHolder getInstance() {
        if (instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }
}
