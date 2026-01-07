package com.lightweightclient.waypoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import com.lightweightclient.LightweightClientMod;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class WaypointManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path waypointsFile;
    private List<Waypoint> waypoints = new ArrayList<>();
    
    public WaypointManager() {
        this.waypointsFile = FabricLoader.getInstance().getConfigDir().resolve(LightweightClientMod.MOD_ID).resolve("waypoints.json");
        load();
    }
    
    public void load() {
        if (Files.exists(waypointsFile)) {
            try (Reader reader = Files.newBufferedReader(waypointsFile)) {
                List<Waypoint> loaded = GSON.fromJson(reader, new TypeToken<List<Waypoint>>(){}.getType());
                if (loaded != null) waypoints = loaded;
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public void save() {
        try {
            Files.createDirectories(waypointsFile.getParent());
            try (Writer writer = Files.newBufferedWriter(waypointsFile)) { GSON.toJson(waypoints, writer); }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public void createWaypointAtPlayer(String name, int color) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return;
        waypoints.add(new Waypoint(name, mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.level.dimension(), color));
        save();
    }
    
    public void removeWaypoint(String id) { waypoints.removeIf(w -> w.getId().equals(id)); save(); }
    public List<Waypoint> getAllWaypoints() { return waypoints; }
    
    public List<Waypoint> getWaypointsForDimension(ResourceKey<Level> dim) {
        return waypoints.stream().filter(w -> w.matchesDimension(dim)).collect(Collectors.toList());
    }
}
