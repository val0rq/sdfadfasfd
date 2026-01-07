package com.lightweightclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightweightclient.config.ConfigManager;
import com.lightweightclient.hud.HudManager;
import com.lightweightclient.keybind.ModKeybindings;
import com.lightweightclient.waypoint.WaypointManager;
import com.lightweightclient.waypoint.WaypointRenderer;

public class LightweightClientMod implements ClientModInitializer {
    
    public static final String MOD_ID = "lightweightclient";
    public static final String MOD_NAME = "Lightweight Client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static LightweightClientMod instance;
    
    private ConfigManager configManager;
    private HudManager hudManager;
    private WaypointManager waypointManager;
    private WaypointRenderer waypointRenderer;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing {}...", MOD_NAME);
        
        configManager = new ConfigManager();
        configManager.load();
        
        waypointManager = new WaypointManager();
        hudManager = new HudManager(configManager);
        waypointRenderer = new WaypointRenderer(waypointManager);
        
        ModKeybindings.register();
        
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            hudManager.render(drawContext, tickCounter);
        });
        
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            waypointRenderer.renderInWorld(context);
        });
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ModKeybindings.handleInput(client);
            hudManager.tick();
        });
        
        LOGGER.info("{} initialized successfully!", MOD_NAME);
    }
    
    public static LightweightClientMod getInstance() { return instance; }
    public ConfigManager getConfigManager() { return configManager; }
    public HudManager getHudManager() { return hudManager; }
    public WaypointManager getWaypointManager() { return waypointManager; }
}
