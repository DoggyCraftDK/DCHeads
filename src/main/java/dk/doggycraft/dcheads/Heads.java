package dk.doggycraft.dcheads;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Heads extends JavaPlugin
{
	public boolean				vaultEnabled		= false;
	
	private PermissionsManager	permissionManager	= null;
	private HeadsManager		headsManager		= null;
	private FreshCoalAPI		freshCoalAPI		= null;
	private HeadGUIManager		headGUIManager		= null;
	private Reflections			reflections			= null;

	private FileConfiguration	config				= null;
	private Commands			commands			= null;

	public boolean				debug				= false;
	
	private static Heads		plugin;

	public HeadsManager getHeadsManager()
	{
		return this.headsManager;
	}
	
	public PermissionsManager getPermissionsManager()
	{
		return this.permissionManager;
	}
	
	public FreshCoalAPI getFreshCoalAPI()
	{
		return this.freshCoalAPI;
	}
	
	public HeadGUIManager getHeadGUIManager()
	{
		return this.headGUIManager;
	}
	
	public Reflections getReflections()
	{
		return this.reflections;
	}

	public void log(String message)
	{
		plugin.getLogger().info(message);
	}

	public void logDebug(String message)
	{
		if (this.debug)
		{
			plugin.getLogger().info(message);
		}
	}

	public void sendInfo(Player player, String message)
	{
		player.sendMessage(ChatColor.AQUA + message);
	}

	public void sendToAll(String message)
	{
		getServer().broadcastMessage(message);
	}

	public void sendMessage(String playerName, String message)
	{
		getServer().getPlayer(playerName).sendMessage(ChatColor.AQUA + message);
	}

	public void reloadSettings()
	{
		reloadConfig();
		loadSettings();
	}

	public void loadSettings()
	{
		config = getConfig();
		
		debug = config.getBoolean("Debug", false);
	}

	public void saveSettings()
	{
		config.set("Debug", debug);

		saveConfig();
	}

	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();

		// Check for Vault
		if (pm.getPlugin("Vault") != null)
		{
			this.vaultEnabled = true;

			log("Vault detected.");
		}
		else
		{
			log("Vault not found.");
		}
		
		this.permissionManager = new PermissionsManager(this);
		this.headsManager = new HeadsManager(this);
		this.freshCoalAPI = new FreshCoalAPI(this);
		this.headGUIManager = new HeadGUIManager(this);
		this.reflections = new Reflections(this);
		this.commands = new Commands(this);

		loadSettings();
		saveSettings();

		this.permissionManager.load();
		this.headsManager.load();
		this.freshCoalAPI.load();
		this.headGUIManager.load();
		
		getServer().getPluginManager().registerEvents(new HeadGUIManager(this), this);
	}

	public void onDisable()
	{
		//reloadSettings();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return commands.onCommand(sender, cmd, label, args);
	}
}
