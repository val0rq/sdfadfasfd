package com.lightweightclient.hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.lightweightclient.config.ModConfig;

public class FpsHud extends HudElement {
    private int currentFps = 0;
    public FpsHud(ModConfig config) { super(config); updateConfig(config); }
    
    public void updateConfig(ModConfig config) {
        this.config = config;
        this.enabled = config.fpsEnabled;
        this.x = config.fpsX;
        this.y = config.fpsY;
        this.color = config.fpsColor;
        this.chroma = config.fpsChroma;
        this.shadow = config.fpsShadow;
    }
    
    public void tick(Minecraft client) { currentFps = client.getFps(); }
    
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker, Minecraft client) {
        String fpsText = "FPS: " + currentFps;
        graphics.drawString(client.font, fpsText, (int) x, (int) y, getColor(), shadow);
        this.width = client.font.width(fpsText);
        this.height = client.font.lineHeight;
    }
    
    public String getName() { return "FPS Counter"; }
    public String getId() { return "fps"; }
}
