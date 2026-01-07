package com.lightweightclient.waypoint;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import com.lightweightclient.LightweightClientMod;

public class WaypointRenderer {
    private final WaypointManager manager;
    public WaypointRenderer(WaypointManager manager) { this.manager = manager; }
    
    public void renderInWorld(WorldRenderContext context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !LightweightClientMod.getInstance().getConfigManager().getConfig().waypointsEnabled) return;
        
        Vec3 camPos = context.camera().getPosition();
        PoseStack stack = context.matrixStack();
        
        for (Waypoint wp : manager.getWaypointsForDimension(mc.level.dimension())) {
            if (!wp.isVisible()) continue;
            double dist = wp.distanceTo(mc.player.getX(), mc.player.getY(), mc.player.getZ());
            if (dist > 500) continue;
            
            stack.pushPose();
            stack.translate(wp.getX() - camPos.x, wp.getY() + 2.5 - camPos.y, wp.getZ() - camPos.z);
            stack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            float scale = (float) Math.max(0.025f, Math.min(0.1f, dist * 0.0005f));
            stack.scale(-scale, -scale, scale);
            
            String label = wp.getName() + " (" + (int)dist + "m)";
            int w = mc.font.width(label) / 2;
            mc.font.drawInBatch(label, -w, 0, wp.getColor(), false, stack.last().pose(), 
                mc.renderBuffers().bufferSource(), net.minecraft.client.gui.Font.DisplayMode.NORMAL, 0x40000000, 15728880);
            stack.popPose();
        }
    }
}
