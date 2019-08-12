package me.pcrunn.devserver.task;

import me.pcrunn.devserver.DevServerPlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class UpdatePluginsTask extends BukkitRunnable implements Runnable {

    private final DevServerPlugin plugin;
    private final HashMap<String, Long> cache;

    public UpdatePluginsTask(DevServerPlugin devServerPlugin) {
        this.plugin = devServerPlugin;
        this.cache = new HashMap<String, Long>();
    }

    public void run() {
        // don't run if hotReloads are off, there's probably a better way to do that
        // instead of just checking if they're enabled whenever the task runs.

        if(!plugin.getConfig().getBoolean("hotReloads")) return;

        // check if new plugins are added, or updated
        File pluginsDir = new File(StringUtils.removeEnd(Bukkit.getServer().getWorldContainer().getAbsolutePath(), ".") + "plugins");

        // don't run if the cache is empty
        if(!cache.isEmpty()) {
            Arrays.stream(pluginsDir.listFiles())
                    .filter(file -> file.getPath().endsWith(".jar")).forEach(file -> {
                // if the file has been modified in the last 8 seconds
                if (System.currentTimeMillis() - file.lastModified() < 8000) {
                    try {
                        // we have to reload the server because we can't find the plugin by it's file
                        plugin.reloadServer();
                    } catch (Exception e) {
                        IntStream.range(0, 5).forEach(i -> System.out.println("wtf"));
                        e.printStackTrace();
                    }
                }
            });
        }

        Arrays.stream(pluginsDir.listFiles())
                .filter(file -> file.getPath().endsWith(".jar")).forEach(file -> cache.put(file.getPath(), file.lastModified()));

    }
}
