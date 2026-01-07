package com.lightweightclient.hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import com.lightweightclient.config.ModConfig;

public class CoordinatesHud extends HudElement {
    private double px, py, pz;
    private Direction facing;
    private String dimName;
    
    public CoordinatesHud(ModConfig config) { super(config); updateConfig(config); }
    
    public void updateConfig(ModConfig config) {
        this.config = config;
        this.enabled = config.coordsEnabled;
        this.x = config.coordsX;
        this.y = config.coordsY;
        this.color = config.coordsColor;
        this.chroma = config.coordsChroma;
        this.shadow = config.coordsShadow;
    }
    
    public void tick(Minecraft client) {
        if (client.player != null) {
            px = client.player.getX(); py = client.player.getY(); pz = client.player.getZ();
            facing = client.player.getDirection();
            String path = client.level.dimension().location().getPath();
            dimName = path.substring(0, 1).toUpperCase() + path.substring(1);
        }
    }
    
    public void render(GuiGraphics g, net.minecraft.client.DeltaTracker dt, Minecraft mc) {
        int yOff = (int) y;
        int lh = mc.font.lineHeight + 2;
        String t1 = String.format("XYZ: %.1f / %.1f / %.1f", px, py, pz);
        g.drawString(mc.font, t1, (int)x, yOff, getColor(), shadow);
        int maxW = mc.font.width(t1);
        yOff += lh;
        
        if (config.coordsShowDirection && facing != null) {
            String t2 = "Facing: " + facing.getName().toUpperCase();
            g.drawString(mc.font, t2, (int)x, yOff, getColor(), shadow);
            maxW = Math.max(maxW, mc.font.width(t2));
            yOff += lh;
        }
        
        if (config.coordsShowDimension && dimName != null) {
            String t3 = "Dimension: " + dimName;
            g.drawString(mc.font, t3, (int)x, yOff, getColor(), shadow);
            maxW = Math.max(maxW, mc.font.width(t3));
            yOff += lh;
        }
        this.width = maxW; this.height = yOff - (int)y;
    }
    
    public String getName() { return "Coordinates"; }
    public String getId() { return "coords"; }
}
