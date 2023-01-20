package online.flowerinsnow.systemreply.impl.object;

import online.flowerinsnow.systemreply.api.object.IEntry;
import online.flowerinsnow.systemreply.api.object.IPattern;
import online.flowerinsnow.systemreply.api.object.PatternMode;
import online.flowerinsnow.systemreply.util.ValidateUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternImpl implements IPattern {
    @NotNull private final EntryImpl ownerEntry;
    @NotNull private String pattern;
    @NotNull private PatternMode mode;
    private boolean ignoreCase;

    public PatternImpl(@NotNull EntryImpl ownerEntry, @NotNull String pattern, @NotNull PatternMode mode, boolean ignoreCase) {
        ValidateUtils.notNull(ownerEntry, "ownerEntry");
        ValidateUtils.notNull(pattern, "pattern");
        ValidateUtils.notNull(mode, "mode");
        if (mode.isRegex()) {
            checkPattern(pattern);
        }
        this.ownerEntry = ownerEntry;
        this.pattern = pattern;
        this.mode = mode;
        this.ignoreCase = ignoreCase;
    }

    @Override
    public @NotNull IEntry getOwnerEntry() {
        return this.ownerEntry;
    }

    @Override
    public @NotNull String getPattern() {
        return this.pattern;
    }

    @Override
    public void setPattern(@NotNull String pattern) {
        ValidateUtils.notNull(pattern, "pattern");
        if (this.mode.isRegex()) {
            checkPattern(pattern);
        }
        this.pattern = pattern;
        this.ownerEntry.writePatterns();
    }

    @Override
    public @NotNull PatternMode getMode() {
        return this.mode;
    }

    @Override
    public void setMode(@NotNull PatternMode mode) {
        ValidateUtils.notNull(mode, "mode");
        this.mode = mode;
        this.ownerEntry.writePatterns();
    }

    @Override
    public boolean match(@NotNull String message) {
        if (this.ignoreCase) {
            switch (this.mode) {
                case FUZZY_MATCH:
                    return message.toLowerCase().contains(this.pattern.toLowerCase());
                case EXACT_MATCH:
                    return message.equalsIgnoreCase(this.pattern);
                case REGEX:
                    return Pattern.compile(this.pattern, Pattern.CASE_INSENSITIVE).matcher(message).find();
                default:
                    throw new RuntimeException();
            }
        } else {
            switch (this.mode) {
                case FUZZY_MATCH:
                    return message.contains(this.pattern);
                case EXACT_MATCH:
                    return message.equalsIgnoreCase(this.pattern);
                case REGEX:
                    return Pattern.compile(this.pattern).matcher(message).find();
                default:
                    throw new RuntimeException();
            }
        }
    }

    @Override
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    @Override
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        this.ownerEntry.writePatterns();
    }

    @Override
    public @NotNull String text() {
        return (ignoreCase ? PatternMode.IGNORE_CASE : "") + this.mode.start + this.pattern;
    }

    private static void checkPattern(@NotNull String pattern) throws IllegalArgumentException {
        try {
            Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern \"" + pattern + "\" not valid");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatternImpl)) return false;
        PatternImpl that = (PatternImpl) o;
        return ignoreCase == that.ignoreCase && pattern.equals(that.pattern) && mode == that.mode;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + pattern.hashCode();
        result = 31 * result + mode.hashCode();
        result = 31 * result + Boolean.hashCode(ignoreCase);
        return result;
    }

    @Override
    public String toString() {
        return "PatternImpl{" +
                "pattern='" + pattern + '\'' +
                ", mode=" + mode +
                ", ignoreCase=" + ignoreCase +
                '}';
    }
}
