package me.electroid.translator.test;

import me.electroid.translator.ConfigurationProvider;
import me.electroid.translator.Platform;
import me.electroid.translator.ServerPlayer;

public class TestPlatform implements Platform {
    private TestConfiguration config = new TestConfiguration();

    @Override
    public ConfigurationProvider getConfiguration() {
        return config;
    }

    @Override
    public ServerPlayer getPlayer(String name) {
        return new TestPlayer(name, "en");
    }
}
