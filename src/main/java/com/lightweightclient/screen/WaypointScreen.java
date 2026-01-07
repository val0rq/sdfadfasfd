package com.lightweightclient.screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.lightweightclient.LightweightClientMod;
import com.lightweightclient.waypoint.Waypoint;
import java.util.List;

public class WaypointScreen extends Screen {
    private final Screen parent;
    private EditBox nameInput;
    private String selectedId = null;
    public WaypointScreen(Screen parent) { super(Component.literal("Waypoints")); this.parent = parent; }
    
    @Override protected void init() {
        nameInput = new EditBox(font, width/2 - 100, 30, 200, 20, Component.literal("Name"));
        addRenderableWidget(nameInput);
        addRenderableWidget(Button.builder(Component.literal("Create"), b -> {
            LightweightClientMod.getInstance().getWaypointManager().createWaypointAtPlayer(nameInput.getValue(), 0xFFFF0000);
            nameInput.setValue("");
        }).bounds(width/2 - 105, 55, 100, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Delete"), b -> {
            if(selectedId != null) LightweightClientMod.getInstance().getWaypointManager().removeWaypoint(selectedId);
        }).bounds(width/2 + 5, 55, 100, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Done"), b -> minecraft.setScreen(parent))
            .bounds(width/2 - 50, height - 30, 100, 20).build());
    }
    
    @Override public void render(GuiGraphics g, int mx, int my, float delta) {
        renderBackground(g, mx, my, delta);
        List<Waypoint> wps = LightweightClientMod.getInstance().getWaypointManager().getAllWaypoints();
        int y = 80;
        for(Waypoint wp : wps) {
            int color = wp.getId().equals(selectedId) ? 0xFF00FF00 : 0xFFFFFFFF;
            g.drawString(font, wp.getName(), width/2 - 100, y, color);
            y += 15;
        }
        super.render(g, mx, my, delta);
    }
    
    @Override public boolean mouseClicked(double mx, double my, int btn) {
        List<Waypoint> wps = LightweightClientMod.getInstance().getWaypointManager().getAllWaypoints();
        int y = 80;
        for(Waypoint wp : wps) {
            if(mx > width/2 - 100 && mx < width/2 + 100 && my > y && my < y + 15) { selectedId = wp.getId(); return true; }
            y += 15;
        }
        return super.mouseClicked(mx, my, btn);
    }
}
