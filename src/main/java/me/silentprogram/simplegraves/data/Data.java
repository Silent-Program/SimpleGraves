package me.silentprogram.simplegraves.data;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Data {
	List<LocationData> deathLocation = new ArrayList<>();
	
	public List<LocationData> getDeathLocationList() {
		return deathLocation;
	}
	
	public void addDeathLocation(Location loc) {
		this.deathLocation.add(new LocationData(loc));
	}
	
	public boolean removeDeathLocation(LocationData loc) {
		return this.deathLocation.remove(loc);
	}
}
