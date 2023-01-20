package online.flowerinsnow.systemreply.impl.object;

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
    @NotNull private String name;
    private HashSet<IPattern> patterns;
    @NotNull private ArrayList<String> replies;
    private boolean pass;
    private boolean block;
    @NotNull private HashSet<String> requiredPermissions;
    private boolean after;

    public EntryImpl(@NotNull String name, @Nullable Collection<String> replies, boolean pass, boolean block, @Nullable Collection<String> requiredPermissions, boolean after) {
        this.name = ValidateUtils.notNull(name, "name");
        this.replies = replies == null ? new ArrayList<>() : new ArrayList<>(replies);
        this.pass = pass;
        this.block = block;
        this.requiredPermissions = requiredPermissions == null ? new HashSet<>() : new HashSet<>(requiredPermissions);
        this.after = after;
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

    public void setPatternDirect(@Nullable Collection<IPattern> patterns) {
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
    public @NotNull HashSet<String> getRequiredPermissions() {
        return new HashSet<>(this.requiredPermissions);
    }

    @Override
    public void setRequiredPermissions(@Nullable Collection<String> requiredPermissions) {
        this.requiredPermissions = requiredPermissions == null ? new HashSet<>() : new HashSet<>(requiredPermissions);
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "permissions", this.requiredPermissions);
    }

    @Override
    public boolean isAfter() {
        return after;
    }

    @Override
    public void setAfter(boolean after) {
        this.after = after;
        YamlConfiguration config = SystemReplyPlugin.getInstance().getEntriesConfig();
        config.set(this.name + config.options().pathSeparator() + "after", this.after);
    }

    @Override
    public void delete() {
        SystemReplyPlugin.getInstance().getEntriesConfig().set(name, null);
        SystemReplyPlugin.getCore().getEntriesManager().entries.remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryImpl)) return false;
        EntryImpl that = (EntryImpl) o;
        return pass == that.pass && block == that.block && name.equals(that.name) && Objects.equals(patterns, that.patterns) && replies.equals(that.replies) && requiredPermissions.equals(that.requiredPermissions) && after == that.after;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + (patterns != null ? patterns.hashCode() : 0);
        result = 31 * result + replies.hashCode();
        result = 31 * result + Boolean.hashCode(pass);
        result = 31 * result + Boolean.hashCode(block);
        result = 31 * result + requiredPermissions.hashCode();
        result = 31 * result + Boolean.hashCode(after);
        return result;
    }

    @Override
    public String toString() {
        return "EntryImpl{" +
                "name='" + name + '\'' +
                ", patterns=" + patterns +
                ", replies=" + replies +
                ", pass=" + pass +
                ", block=" + block +
                ", requiredPermissions=" + requiredPermissions +
                ", after=" + after +
                '}';
    }
}
