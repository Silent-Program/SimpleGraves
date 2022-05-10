package me.silentprogram.simplegraves.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.silentprogram.simplegraves.SimpleGraves;

import java.io.File;
import java.io.IOException;

public class DataManager {
	Data config;
	SimpleGraves plugin;
	File configFile;
	ObjectMapper om = new ObjectMapper();
	
	public DataManager(SimpleGraves plugin) {
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "data.json");
	}
	
	public Data initializeConfig() {
		try {
			if (!configFile.exists()) {
				om.writeValue(configFile, new Data());
			}
			config = om.readValue(configFile, Data.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public void saveConfig() {
		try {
			om.writeValue(configFile, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Data getConfig() {
		return config;
	}
}
