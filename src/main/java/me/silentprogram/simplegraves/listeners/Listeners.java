package me.silentprogram.simplegraves.listeners;

import me.silentprogram.simplegraves.SimpleGraves;
import me.silentprogram.simplegraves.data.LocationData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Listeners implements Listener {
	SimpleGraves plugin;
	
	public Listeners(SimpleGraves plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		//Initialize item arrays, and player
		List<ItemStack> items = new ArrayList<>();
		List<ItemStack> items2 = new ArrayList<>();
		Player plr = event.getEntity();
		if (plr.getGameMode() == GameMode.SPECTATOR) return;
		if (!(plr.hasPermission("simplegraves.grave"))) return;
		if (event.getKeepInventory()) return;
		// Loop through all items in player inventory and add them to the array
		for (ItemStack item : plr.getInventory().getContents()) {
			if (item == null || item.getType() == Material.AIR) continue;
			ItemStack itemClone = item.clone();
			if (items.size() < 27) {
				items.add(itemClone);
			} else {
				items2.add(itemClone);
			}
			item.setAmount(0);
		}
		if (items.isEmpty()) return;
		//Create chest
		Location loc = plr.getLocation();
		Block block = loc.getBlock();
		block.setType(Material.CHEST);
		plugin.getDataConfig().addDeathLocation(block.getLocation());
		Chest chest = (Chest) block.getState();
		Inventory chestInventory = chest.getInventory();
		//Set contents of chest to items array
		chestInventory.setContents(items.toArray(new ItemStack[0]));
		if (!(items2.isEmpty())) {
			Location loc2 = plr.getLocation().add(0, 1, 0);
			loc2.getBlock().setType(Material.CHEST);
			Block block2 = loc2.getBlock();
			plugin.getDataConfig().addDeathLocation(block2.getLocation());
			Chest chest2 = (Chest) block2.getState();
			Inventory chestInventory2 = chest2.getInventory();
			//Set contents of chest to items array
			chestInventory2.setContents(items2.toArray(new ItemStack[0]));
		}
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (event.getClickedBlock() == null) return;
		if (event.getClickedBlock().getType() == Material.AIR) return;
		if (event.getClickedBlock().getType() != Material.CHEST) return;
		String blockLoc = event.getClickedBlock().getLocation().getBlockX()
				+ ", " + event.getClickedBlock().getLocation().getBlockY()
				+ ", " + event.getClickedBlock().getLocation().getBlockZ();
		for (LocationData i : plugin.getList()) {
			if (i.getLocationString().equals(blockLoc)) {
				plugin.getMap().put(event.getPlayer(), event.getClickedBlock());
				plugin.getDataConfig().removeDeathLocation(i);
				return;
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) return;
		Player plr = (Player) event.getPlayer();
		Map<Player, Block> map = plugin.getMap();
		if (!map.containsKey(plr)) return;
		map.get(plr).setType(Material.AIR);
		map.remove(plr);
		
	}
}
