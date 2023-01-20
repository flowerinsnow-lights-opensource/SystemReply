package online.flowerinsnow.systemreply.impl.manager;

import online.flowerinsnow.systemreply.SystemReplyPlugin;
import online.flowerinsnow.systemreply.api.manager.IEntriesManager;
import online.flowerinsnow.systemreply.api.object.IEntry;
import online.flowerinsnow.systemreply.api.object.IPattern;
import online.flowerinsnow.systemreply.api.object.PatternMode;
import online.flowerinsnow.systemreply.impl.object.EntryImpl;
import online.flowerinsnow.systemreply.impl.object.PatternImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntriesManagerImpl implements IEntriesManager {
    @NotNull public final HashSet<IEntry> entries;

    public EntriesManagerImpl() {
        this(null);
    }

    public EntriesManagerImpl(@Nullable HashSet<IEntry> entries) {
        this.entries = entries == null ? new HashSet<>() : new HashSet<>(entries);
    }

    /**
     * 从配置文件根部读取
     *
     * @param config 配置文件根部
     */
    public void load(ConfigurationSection config) {
        entries.clear();
        Set<String> keys = config.getKeys(false);
        keys.forEach(k -> {
            ConfigurationSection section = Objects.requireNonNull(config.getConfigurationSection(k));
            EntryImpl entry = new EntryImpl(k,
                    section.getStringList("replies"),
                    !section.contains("pass") || section.getBoolean("pass"), // default true
                    section.contains("block") && section.getBoolean("block"), // default false
                    section.getStringList("permissions"),
                    !section.contains("after") || section.getBoolean("after")
            );
            ArrayList<IPattern> patterns = section.getStringList("patterns").stream().collect(
                    ArrayList::new,
                    (list, line) -> {
                        boolean ignoreCase = line.startsWith(PatternMode.IGNORE_CASE);
                        if (ignoreCase) {
                            line = line.substring(PatternMode.IGNORE_CASE.length());
                        }
                        PatternMode mode = PatternMode.FUZZY_MATCH;
                        for (PatternMode value : PatternMode.values()) {
                            if (value.isThisMode(line)) {
                                mode = value;
                                line = line.substring(mode.start.length());
                                break;
                            }
                        }
                        list.add(new PatternImpl(entry, line, mode, ignoreCase));
                    }, ArrayList::addAll
            );
            entry.setPatternDirect(patterns);
            entries.add(entry);
        });
    }

    @Override
    public @NotNull HashSet<IEntry> getEntries() {
        return new HashSet<>(this.entries);
    }

    @Override
    public @NotNull EntryImpl createEntry(@NotNull String name, @Nullable Collection<String> replies, boolean pass, boolean block, @Nullable Collection<String> requiredPermissions, boolean after) {
        EntryImpl entry = new EntryImpl(name, replies, pass, block, requiredPermissions, after);
        entries.add(entry);
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        ConfigurationSection section = config.createSection(name);
        section.set("replies", entry.getReplies());
        section.set("permissions", entry.getRequiredPermissions());
        section.set("pass", pass);
        section.set("block", block);
        section.set("after", after);
        config.set(name, section);
        return entry;
    }

    @Override
    public @NotNull HashSet<IEntry> matches(String message) {
        HashSet<IEntry> matches = new HashSet<>();
        for (IEntry entry : entries) {
            if (entry.getPatterns().stream().anyMatch(pattern -> pattern.match(message))) {
                matches.add(entry);
                if (!entry.isPass()) {
                    break;
                }
            }
        }
        return matches;
    }

    @Override
    public void reload() {
        SystemReplyPlugin.getInstance().reloadConfig();
    }

    @Override
    public void save() {
        SystemReplyPlugin.getInstance().saveEntries();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntriesManagerImpl)) return false;
        EntriesManagerImpl that = (EntriesManagerImpl) o;
        return entries.equals(that.entries);
    }

    @Override
    public int hashCode() {
        return 17 * 31 + entries.hashCode();
    }

    @Override
    public String toString() {
        return "EntriesManagerImpl{" +
                "entries=" + entries +
                '}';
    }
}
