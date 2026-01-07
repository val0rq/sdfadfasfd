package com.lightweightclient.waypoint;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
public class Waypoint {
    private String id;
    private String name;
    private double x, y, z;
    private String dimension;
    private int color;
    private boolean visible;
    
    public Waypoint(String name, double x, double y, double z, ResourceKey<Level> dim, int color) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name; this.x = x; this.y = y; this.z = z;
        this.dimension = dim.location().toString();
        this.color = color;
        this.visible = true;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public int getColor() { return color; }
    public boolean isVisible() { return visible; }
    public void setVisible(boolean v) { this.visible = v; }
    
    public boolean matchesDimension(ResourceKey<Level> dim) {
        return dimension != null && dimension.equals(dim.location().toString());
    }
    public double distanceTo(double px, double py, double pz) {
        return Math.sqrt(Math.pow(x-px,2) + Math.pow(y-py,2) + Math.pow(z-pz,2));
    }
}
