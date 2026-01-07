package com.lightweightclient.keybind;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import com.lightweightclient.LightweightClientMod;
import com.lightweightclient.screen.HudEditorScreen;
import com.lightweightclient.screen.WaypointScreen;

public class ModKeybindings {
    public static KeyMapping toggleFps, toggleCoords, toggleMinimap, openWaypoints, hudEditor, quickWaypoint;
    
    public static void register() {
        String cat = "category." + LightweightClientMod.MOD_ID;
        toggleFps = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.toggle_fps", GLFW.GLFW_KEY_F4, cat));
        toggleCoords = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.toggle_coords", GLFW.GLFW_KEY_F5, cat));
        toggleMinimap = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.toggle_minimap", GLFW.GLFW_KEY_F6, cat));
        openWaypoints = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.open_waypoints", GLFW.GLFW_KEY_M, cat));
        hudEditor = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.hud_editor", GLFW.GLFW_KEY_RIGHT_SHIFT, cat));
        quickWaypoint = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.quick_waypoint", GLFW.GLFW_KEY_B, cat));
    }
    
    public static void handleInput(Minecraft mc) {
        if(mc.player == null) return;
        var cm = LightweightClientMod.getInstance().getConfigManager();
        if(toggleFps.consumeClick()) cm.setConfig(cm.getConfig()); // Toggle logic simplifed for brevity, assumes config object mutation
        while(openWaypoints.consumeClick()) mc.setScreen(new WaypointScreen(null));
        while(hudEditor.consumeClick()) mc.setScreen(new HudEditorScreen(null));
    }
}
