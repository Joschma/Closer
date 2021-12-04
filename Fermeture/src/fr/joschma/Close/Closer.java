package fr.joschma.Close;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import fr.joschma.Close.Timer.ClosingTimer;

public class Closer extends JavaPlugin implements Listener {

	BukkitTask ct;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}

	public void startTimer(int timer, Player player) {
		ct = new ClosingTimer(timer, player, this).runTaskTimer(this, 0, 20);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (Bukkit.getOnlinePlayers().size() == getConfig().getInt("NumberOfPeopleToStartClosingTime") + 1) {
			if (ct != null) {
				if (!ct.isCancelled()) {
					ct.cancel();
					Bukkit.broadcastMessage("Close cancel");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (Bukkit.getOnlinePlayers().size() - 1 == getConfig().getInt("NumberOfPeopleToStartClosingTime")) {
			startTimer(getConfig().getInt("TimeBeforeClosingInSeconds"), player);
			Bukkit.broadcastMessage("Will close in " + getConfig().getInt("TimeBeforeClosingInSeconds"));
		}
	}
}
