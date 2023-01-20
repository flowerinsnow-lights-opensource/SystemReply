package online.flowerinsnow.systemreply;

import cc.carm.lib.mineconfiguration.bukkit.MineConfiguration;
import cc.carm.lib.mineconfiguration.bukkit.source.BukkitConfigProvider;
import online.flowerinsnow.systemreply.api.SystemReplyAPI;
import online.flowerinsnow.systemreply.command.MainCommand;
import online.flowerinsnow.systemreply.config.Config;
import online.flowerinsnow.systemreply.config.Message;
import online.flowerinsnow.systemreply.impl.SystemReplyCore;
import online.flowerinsnow.systemreply.impl.manager.EntriesManagerImpl;
import online.flowerinsnow.systemreply.listener.ChatListener;
import online.flowerinsnow.systemreply.util.DebugUtils;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

public class SystemReplyPlugin extends JavaPlugin {
    private static SystemReplyPlugin instance;

    private BukkitConfigProvider configProvider;
    private BukkitConfigProvider messageProvider;
    private YamlConfiguration entriesConfig;
    private final File entriesConfigFile = new File(getDataFolder(), "entries.yml");
    private static SystemReplyCore core;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        loadConfig();
        reloadConfig();

        DebugUtils.tryDebug(1, "注册监听器...");
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        DebugUtils.tryDebug(1, "注册命令");
        registerCommand("systemreply", new MainCommand());
    }

    @Override
    public void onDisable() {
        DebugUtils.tryDebug(2, "取消所有计划任务...");
        getServer().getScheduler().cancelTasks(this);
        DebugUtils.tryDebug(3, "清空API");
        core = null;
        SystemReplyAPI.setInstance(null);
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
            DebugUtils.tryDebug(1, "保存所有条目...");
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
            if (core == null) {
                core = new SystemReplyCore(new EntriesManagerImpl());
                SystemReplyAPI.setInstance(core);
            }
            core.getEntriesManager().load(entriesConfig);

            try {
                EventPriority.valueOf(Config.PRIORITY.getNotNull());
            } catch (IllegalArgumentException e) {
                getLogger().info("config.yml中的priority字段不合法，已自动设置为默认值");
                Config.PRIORITY.setDefault();
                configProvider.save();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public YamlConfiguration getEntriesConfig() {
        return entriesConfig;
    }

    @SuppressWarnings("SameParameterValue")
    private void registerCommand(String cmd, TabExecutor executor) {
        Optional.ofNullable(getCommand(cmd)).ifPresent(c -> {
            c.setExecutor(executor);
            c.setTabCompleter(executor);
        });
    }

    public static SystemReplyPlugin getInstance() {
        return instance;
    }

    public static SystemReplyCore getCore() {
        return core;
    }
}
