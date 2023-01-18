package online.flowerinsnow.systemreply.impl;

import online.flowerinsnow.systemreply.SystemReplyPlugin;
import online.flowerinsnow.systemreply.api.object.IEntry;
import online.flowerinsnow.systemreply.api.object.IPattern;
import online.flowerinsnow.systemreply.api.object.PatternMode;
import online.flowerinsnow.systemreply.util.ValidateUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntryImpl implements IEntry {
    private String name;
    private HashSet<IPattern> patterns;
    private ArrayList<String> replies;
    private boolean pass;
    private boolean async;
    private boolean block;
    private HashSet<String> requiredPermissions;

    public EntryImpl(@NotNull String name, @Nullable Collection<String> replies, boolean pass, boolean async, boolean block, @Nullable Collection<String> requiredPermissions) {
        this.name = ValidateUtils.notNull(name, "name");
        this.replies = replies == null ? new ArrayList<>() : new ArrayList<>(replies);
        this.pass = pass;
        this.async = async;
        this.block = block;
        this.requiredPermissions = requiredPermissions == null ? new HashSet<>() : new HashSet<>(requiredPermissions);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void setName(@NotNull String name) {
        ValidateUtils.notNull(name, "name");
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        // 将name重命名至this.name
        ConfigurationSection section = config.getConfigurationSection(this.name);
        config.set(name, section);
        config.set(this.name, null);
        this.name = name;
    }

    @Override
    public @NotNull HashSet<IPattern> getPatterns() {
        return new HashSet<>(this.patterns);
    }

    @Override
    public void setPatterns(@Nullable Collection<IPattern> patterns) {
        if (patterns == null) {
            this.patterns = new HashSet<>();
            return;
        }
        for (IPattern pattern : patterns) {
            if (!pattern.getOwnerEntry().equals(this)) {
                throw new IllegalArgumentException("Invalid pattern owner entry.");
            }
        }
        this.patterns = new HashSet<>(patterns);
        this.writePatterns();
    }

    /**
     * 将所有规则写入配置文件
     */
    public void writePatterns() {
        ArrayList<String> list = new ArrayList<>();
        this.patterns.forEach(pattern ->
            list.add(pattern.text())
        );
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "patterns", list);
    }

    private void setPatternDirect(@Nullable Collection<IPattern> patterns) {
        this.patterns = patterns == null ? new HashSet<>() : new HashSet<>(patterns);
    }

    @Override
    public @NotNull PatternImpl createPattern(@NotNull PatternMode mode, @NotNull String pattern, boolean ignoreCase) {
        PatternImpl patt = new PatternImpl(this, pattern, mode, ignoreCase);
        HashSet<IPattern> old = this.getPatterns();
        old.add(patt);
        this.setPatterns(old);
        return patt;
    }

    @Override
    public @NotNull ArrayList<String> getReplies() {
        return new ArrayList<>(this.replies);
    }

    @Override
    public void setReplies(@Nullable Collection<String> replies) {
        this.replies = replies == null ? new ArrayList<>() : new ArrayList<>(replies);
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "replies", this.replies);
    }

    @Override
    public boolean isPass() {
        return pass;
    }

    @Override
    public void setPass(boolean pass) {
        this.pass = pass;
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "pass", this.pass);
    }

    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public void setAsync(boolean async) {
        this.async = async;
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "async", this.async);
    }

    @Override
    public boolean isBlock() {
        return block;
    }

    @Override
    public void setBlock(boolean block) {
        this.block = block;
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "block", this.block);
    }

    @Override
    public @NotNull HashSet<String> requiredPermissions() {
        return new HashSet<>(this.requiredPermissions);
    }

    @Override
    public void setRequiredPermissions(@Nullable Collection<String> requiredPermissions) {
        this.requiredPermissions = requiredPermissions == null ? new HashSet<>() : new HashSet<>(requiredPermissions);
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "permissions", this.requiredPermissions);
    }

    @Override
    public void delete() {

    }
}
