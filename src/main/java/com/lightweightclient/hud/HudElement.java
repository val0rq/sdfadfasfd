package com.lightweightclient.hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.lightweightclient.config.ModConfig;
import com.lightweightclient.util.ColorUtil;

public abstract class HudElement {
    protected float x, y;
    protected int width, height;
    protected boolean enabled;
    protected int color;
    protected boolean chroma, shadow;
    protected ModConfig config;
    
    public HudElement(ModConfig config) { this.config = config; }
    
    public abstract void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker, Minecraft client);
    public abstract void tick(Minecraft client);
    public abstract void updateConfig(ModConfig config);
    public abstract String getName();
    public abstract String getId();
    
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public int getColor() {
        return chroma ? ColorUtil.getChromaColor(config.chromaSpeed) : color;
    }
    
    public boolean isInBounds(double mouseX, double mouseY, float scale) {
        float scaledX = x * scale;
        float scaledY = y * scale;
        return mouseX >= scaledX && mouseX <= scaledX + (width * scale) && mouseY >= scaledY && mouseY <= scaledY + (height * scale);
    }
}
