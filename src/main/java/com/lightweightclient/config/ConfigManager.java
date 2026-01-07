package com.lightweightclient.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import com.lightweightclient.LightweightClientMod;
import java.io.*;
import java.nio.file.*;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path configPath;
    private ModConfig config;
    private boolean dirty = false;
    
    public ConfigManager() {
        this.configPath = FabricLoader.getInstance().getConfigDir().resolve(LightweightClientMod.MOD_ID + ".json");
        this.config = new ModConfig();
    }
    
    public void load() {
        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                config = GSON.fromJson(reader, ModConfig.class);
                if (config == null) config = new ModConfig();
            } catch (Exception e) { config = new ModConfig(); }
        } else { save(); }
    }
    
    public void save() {
        try {
            Files.createDirectories(configPath.getParent());
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(config, writer);
            }
            dirty = false;
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void markDirty() { dirty = true; }
    public void saveIfDirty() { if (dirty) save(); }
    public ModConfig getConfig() { return config; }
    public void setConfig(ModConfig c) { this.config = c; markDirty(); save(); }
}
