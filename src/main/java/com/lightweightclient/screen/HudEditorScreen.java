package com.lightweightclient.screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.lightweightclient.LightweightClientMod;
import com.lightweightclient.hud.HudElement;

public class HudEditorScreen extends Screen {
    private final Screen parent;
    private HudElement dragging = null;
    private double dragX, dragY;
    public HudEditorScreen(Screen parent) { super(Component.literal("HUD Editor")); this.parent = parent; }
    
    @Override protected void init() {
        addRenderableWidget(Button.builder(Component.literal("Done"), b -> {
            LightweightClientMod.getInstance().getConfigManager().save();
            minecraft.setScreen(parent);
        }).bounds(width/2 - 50, height - 30, 100, 20).build());
    }
    
    @Override public void render(GuiGraphics graphics, int mx, int my, float delta) {
        renderBackground(graphics, mx, my, delta);
        graphics.drawCenteredString(font, "Drag elements to move", width/2, 10, 0xFFFFFF);
        float scale = LightweightClientMod.getInstance().getConfigManager().getConfig().hudScale;
        for(HudElement e : LightweightClientMod.getInstance().getHudManager().getElements()) {
            int x = (int)(e.getX()*scale), y = (int)(e.getY()*scale);
            int w = (int)(Math.max(e.getWidth(), 20)*scale), h = (int)(Math.max(e.getHeight(), 10)*scale);
            graphics.renderOutline(x-1, y-1, w+2, h+2, 0xFFFFFFFF);
            graphics.drawString(font, e.getName(), x, y-10, 0xFFFFFF);
        }
        super.render(graphics, mx, my, delta);
    }
    
    @Override public boolean mouseClicked(double mx, double my, int btn) {
        float scale = LightweightClientMod.getInstance().getConfigManager().getConfig().hudScale;
        for(HudElement e : LightweightClientMod.getInstance().getHudManager().getElements()) {
            if(e.isInBounds(mx, my, scale)) {
                dragging = e; dragX = mx - e.getX()*scale; dragY = my - e.getY()*scale; return true;
            }
        }
        return super.mouseClicked(mx, my, btn);
    }
    @Override public boolean mouseReleased(double mx, double my, int btn) { dragging = null; return super.mouseReleased(mx, my, btn); }
    @Override public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if(dragging != null) {
            float scale = LightweightClientMod.getInstance().getConfigManager().getConfig().hudScale;
            dragging.setX((float)((mx - dragX)/scale)); dragging.setY((float)((my - dragY)/scale)); return true;
        }
        return super.mouseDragged(mx, my, btn, dx, dy);
    }
}
