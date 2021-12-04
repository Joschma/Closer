package fr.joschma.Close.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.joschma.Close.Closer;

public class ClosingTimer extends BukkitRunnable {

	int timer;
	Player player;
	Closer pl;

	public ClosingTimer(int timer, Player player, Closer pl) {
		super();
		this.timer = timer;
		this.player = player;
		this.pl = pl;
	}

	@Override
	public void run() {
		if (timer == 0) {
			for(String command : pl.getConfig().getStringList("CommandToDoToStopServer")) {
				Bukkit.broadcastMessage(command);
				Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), command);
			}
			cancel();
		} else {
			timer--;
		}
	}
}
