package com.lightweightclient.hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MapColor;
import com.lightweightclient.config.ModConfig;
import com.lightweightclient.LightweightClientMod;
import com.lightweightclient.waypoint.Waypoint;
import java.util.List;

public class MinimapHud extends HudElement {
    private int[][] terrainColors;
    private int ticksSinceUpdate = 0;
    
    public MinimapHud(ModConfig config) {
        super(config);
        updateConfig(config);
        this.terrainColors = new int[config.minimapSize][config.minimapSize];
    }
    
    public void updateConfig(ModConfig config) {
        this.config = config;
        this.enabled = config.minimapEnabled;
        this.width = config.minimapSize;
        this.height = config.minimapSize;
        if (terrainColors == null || terrainColors.length != config.minimapSize) {
            terrainColors = new int[config.minimapSize][config.minimapSize];
        }
    }
    
    public void tick(Minecraft client) {
        ticksSinceUpdate++;
        if (ticksSinceUpdate >= config.minimapUpdateInterval && client.player != null) {
            ticksSinceUpdate = 0;
            updateTerrain(client);
        }
    }
    
    private void updateTerrain(Minecraft client) {
        int radius = config.minimapSize / 2;
        int px = (int) client.player.getX();
        int pz = (int) client.player.getZ();
        int zoom = config.minimapZoom;
        
        for (int x = 0; x < config.minimapSize; x++) {
            for (int z = 0; z < config.minimapSize; z++) {
                int wx = px + (x - radius) * zoom;
                int wz = pz + (z - radius) * zoom;
                try {
                    int y = client.level.getHeight(Heightmap.Types.WORLD_SURFACE, wx, wz);
                    BlockPos pos = new BlockPos(wx, y - 1, wz);
                    MapColor color = client.level.getBlockState(pos).getMapColor(client.level, pos);
                    terrainColors[x][z] = color.col | 0xFF000000;
                } catch(Exception e) { terrainColors[x][z] = 0xFF000000; }
            }
        }
    }
    
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker, Minecraft client) {
        int mx = (int)(config.minimapX * graphics.guiWidth());
        int my = (int)(config.minimapY * graphics.guiHeight());
        this.x = mx; this.y = my;
        
        graphics.fill(mx-2, my-2, mx+width+2, my+height+2, 0xAA000000); // BG
        
        graphics.pose().pushPose();
        if (config.minimapRotate) {
             graphics.pose().translate(mx + width/2, my + height/2, 0);
             graphics.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(-client.player.getYRot() + 180));
             graphics.pose().translate(-(mx + width/2), -(my + height/2), 0);
        }
        
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                graphics.fill(mx+i, my+j, mx+i+1, my+j+1, terrainColors[i][j]);
            }
        }
        graphics.pose().popPose();
        
        // Player
        graphics.fill(mx + width/2 - 2, my + height/2 - 2, mx + width/2 + 2, my + height/2 + 2, 0xFFFFFFFF);
        
        // Waypoints
        if (config.minimapShowWaypoints) {
            List<Waypoint> wps = LightweightClientMod.getInstance().getWaypointManager().getWaypointsForDimension(client.level.dimension());
            for (Waypoint wp : wps) {
                if(!wp.isVisible()) continue;
                double dx = (wp.getX() - client.player.getX()) / config.minimapZoom;
                double dz = (wp.getZ() - client.player.getZ()) / config.minimapZoom;
                int dotX = mx + width/2 + (int)dx;
                int dotY = my + height/2 + (int)dz;
                if (dotX >= mx && dotX <= mx+width && dotY >= my && dotY <= my+height)
                    graphics.fill(dotX-2, dotY-2, dotX+2, dotY+2, wp.getColor());
            }
        }
    }
    
    public String getName() { return "Minimap"; }
    public String getId() { return "minimap"; }
}
