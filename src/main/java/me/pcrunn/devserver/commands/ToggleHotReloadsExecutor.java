package me.pcrunn.devserver.commands;

import me.pcrunn.devserver.DevServerPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleHotReloadsExecutor implements CommandExecutor {

    private final DevServerPlugin plugin;

    public ToggleHotReloadsExecutor(DevServerPlugin devServerPlugin) {
        this.plugin = devServerPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin.getConfig().set("hotReloads", !plugin.getConfig().getBoolean("hotReloads"));
        plugin.saveConfig();
        commandSender.sendMessage("hotReloads = " + plugin.getConfig().get("hotReloads"));
        return false;
    }
}
