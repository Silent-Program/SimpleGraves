package me.silentprogram.simplegraves.data;

import org.bukkit.Location;

public class LocationData {
	String locationString;
	int lifetime = 0;
	
	public LocationData(Location loc) {
		locationString = loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
	}
	
	public LocationData() {
	
	}
	
	public String getLocationString() {
		return locationString;
	}
	
	public int getLifetime(){
		return lifetime;
	}
}
