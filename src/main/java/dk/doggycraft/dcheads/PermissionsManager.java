package dk.doggycraft.dcheads;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class PermissionsManager
{
	private String			pluginName			= "null";
	private Heads			plugin;
	private Permission 		vaultPermission;
	
	public PermissionsManager(Heads p)
	{
		this.plugin = p;
			
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		vaultPermission = permissionProvider.getProvider();
	}

	public void load()
	{
		// Nothing to see here
	}

	public Plugin getPlugin()
	{
		return plugin;
	}

	public String getPermissionPluginName()
	{
		return pluginName;
	}

	public boolean hasPermission(Player player, String node)
	{
		return vaultPermission.has(player, node);
	}

	public String getGroup(String playerName)
	{
		return vaultPermission.getPrimaryGroup(plugin.getServer().getPlayer(playerName));
	}

	public void setGroup(String playerName, String groupName)
	{
		Player player = plugin.getServer().getPlayer(playerName);
		vaultPermission.playerAddGroup(player, groupName);
	}
}
