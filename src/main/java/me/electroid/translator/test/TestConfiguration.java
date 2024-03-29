package me.electroid.translator.test;

import me.electroid.translator.ConfigurationProvider;

public class TestConfiguration implements ConfigurationProvider {
    @Override
    public String getString(String path) {
        switch (path) {
        case "translator":
            return "googletranslate";
        case "message-format":
            return "<%s> %s";
        case "enabled":
            return "true";
        default:
            return null;
        }
    }

    @Override
    public void setString(String path, String value) {
        //Do nothing
    }
}
