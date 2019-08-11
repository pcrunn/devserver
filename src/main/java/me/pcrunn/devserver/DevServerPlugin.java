package me.pcrunn.devserver;

import me.pcrunn.devserver.commands.ToggleHotReloadsExecutor;
import me.pcrunn.devserver.task.UpdatePluginsTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DevServerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // tasks
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new UpdatePluginsTask(this), 20, 20);

        // commands
        (Objects.requireNonNull(getCommand("toggleHotReloads"))).setExecutor(new ToggleHotReloadsExecutor(this));

    }

    public void reloadServer() {
        Bukkit.getScheduler().runTask(this, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload"));
    }
}
