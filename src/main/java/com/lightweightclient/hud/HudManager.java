package com.lightweightclient.hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.lightweightclient.config.ConfigManager;
import java.util.ArrayList;
import java.util.List;

public class HudManager {
    private final ConfigManager configManager;
    private final List<HudElement> elements;
    private FpsHud fpsHud;
    private CoordinatesHud coordsHud;
    private MinimapHud minimapHud;
    private int tickCounter = 0;
    
    public HudManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.elements = new ArrayList<>();
        fpsHud = new FpsHud(configManager.getConfig());
        coordsHud = new CoordinatesHud(configManager.getConfig());
        minimapHud = new MinimapHud(configManager.getConfig());
        elements.add(fpsHud);
        elements.add(coordsHud);
        elements.add(minimapHud);
    }
    
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        if (client.getDebugOverlay().showDebugScreen() || client.screen != null || client.player == null) return;
        
        graphics.pose().pushPose();
        float scale = configManager.getConfig().hudScale;
        graphics.pose().scale(scale, scale, 1.0f);
        
        for (HudElement element : elements) {
            if (element.isEnabled()) element.render(graphics, deltaTracker, client);
        }
        graphics.pose().popPose();
    }
    
    public void tick() {
        tickCounter++;
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        for (HudElement element : elements) element.tick(client);
        if (tickCounter % 200 == 0) configManager.saveIfDirty();
    }
    
    public List<HudElement> getElements() { return elements; }
    public FpsHud getFpsHud() { return fpsHud; }
    public CoordinatesHud getCoordsHud() { return coordsHud; }
    public MinimapHud getMinimapHud() { return minimapHud; }
    
    public void refreshConfig() {
        var config = configManager.getConfig();
        fpsHud.updateConfig(config);
        coordsHud.updateConfig(config);
        minimapHud.updateConfig(config);
    }
}
