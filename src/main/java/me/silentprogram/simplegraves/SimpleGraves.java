package me.silentprogram.simplegraves;

import me.silentprogram.simplegraves.data.Data;
import me.silentprogram.simplegraves.data.DataManager;
import me.silentprogram.simplegraves.data.LocationData;
import me.silentprogram.simplegraves.listeners.Listeners;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Graves no longer get destroyed when opening
//Graves dont save to file one /stop

public final class SimpleGraves extends JavaPlugin {
	Data dataConfig;
	DataManager dataManager;
	Map<Player, Block> map = new HashMap<>();
	List<LocationData> list;
	
	@Override
	public void onEnable() {
		//Initialize data storage
		getDataFolder().mkdirs();
		dataManager = new DataManager(this);
		dataConfig = dataManager.initializeConfig();
		this.list = dataConfig.getDeathLocationList();
		
		//Register Listeners
		new Listeners(this);
		
		//Schedule task to save data every 5 minutes.
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			dataManager.saveConfig();
		}, 0, 6000);
	}
	
	@Override
	public void onDisable() {
		dataManager.saveConfig();
	}
	
	public Map<Player, Block> getMap() {
		return map;
	}
	
	public Data getDataConfig() {
		return dataConfig;
	}
	
	public List<LocationData> getList() {
		return list;
	}
}
