package com.lightweightclient.config;
public class ModConfig {
    public boolean fpsEnabled = true;
    public float fpsX = 5.0f;
    public float fpsY = 5.0f;
    public int fpsColor = 0xFFFFFFFF;
    public boolean fpsChroma = false;
    public boolean fpsShadow = true;
    
    public boolean coordsEnabled = true;
    public float coordsX = 5.0f;
    public float coordsY = 20.0f;
    public int coordsColor = 0xFFFFFFFF;
    public boolean coordsChroma = false;
    public boolean coordsShadow = true;
    public boolean coordsShowDirection = true;
    public boolean coordsShowDimension = true;
    
    public boolean minimapEnabled = true;
    public float minimapX = 0.85f;
    public float minimapY = 0.02f;
    public int minimapSize = 100;
    public int minimapZoom = 2;
    public boolean minimapRotate = true;
    public boolean minimapShowEntities = true;
    public boolean minimapShowWaypoints = true;
    public int minimapUpdateInterval = 5;
    
    public boolean waypointsEnabled = true;
    public boolean waypointsShowInWorld = true;
    public boolean waypointsShowDistance = true;
    public boolean waypointsShowName = true;
    public int waypointRenderDistance = 500;
    
    public float hudScale = 1.0f;
    public int chromaSpeed = 2000;
}
