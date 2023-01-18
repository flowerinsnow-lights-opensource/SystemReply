package online.flowerinsnow.systemreply;

import cc.carm.lib.mineconfiguration.bukkit.MineConfiguration;
import cc.carm.lib.mineconfiguration.bukkit.source.BukkitConfigProvider;
import online.flowerinsnow.systemreply.config.Config;
import online.flowerinsnow.systemreply.config.Message;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class SystemReplyPlugin extends JavaPlugin {
    private static SystemReplyPlugin instance;

    private BukkitConfigProvider configProvider;
    private BukkitConfigProvider messageProvider;
    private YamlConfiguration entriesConfig;
    private final File entriesConfigFile = new File(getDataFolder(), "entries.yml");

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        loadConfig();
        reloadConfig();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    private void loadConfig() {
        //noinspection ResultOfMethodCallIgnored
        getDataFolder().mkdirs();

        configProvider = MineConfiguration.from(this, "config.yml");
        configProvider.initialize(Config.class);

        messageProvider = MineConfiguration.from(this, "messages.yml");
        messageProvider.initialize(Message.class);

        if (!entriesConfigFile.exists()) {
            try (InputStream in = getResource("entries.yml")) {
                Files.copy(Objects.requireNonNull(in), entriesConfigFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveEntries() {
        try {
            entriesConfig.save(entriesConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reloadConfig() {
        try {
            configProvider.reload();
            messageProvider.reload();
            entriesConfig = YamlConfiguration.loadConfiguration(entriesConfigFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public YamlConfiguration getEntriesConfig() {
        return entriesConfig;
    }

    public static SystemReplyPlugin getInstance() {
        return instance;
    }
}
