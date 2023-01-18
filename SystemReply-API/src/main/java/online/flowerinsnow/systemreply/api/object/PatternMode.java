package online.flowerinsnow.systemreply.api.object;

import org.jetbrains.annotations.NotNull;

/**
 * 规则的匹配规则
 */
public enum PatternMode {
    /**
     * 模糊匹配
     */
    FUZZY_MATCH(0x1, "S:"),
    EXACT_MATCH(0x2, "SE:"),
    REGEX(0x80000003, "R:");
    public final int id;
    /**
     * 起始消息
     */
    public final String start;

    PatternMode(int id, String start) {
        this.id = id;
        this.start = start;
    }

    public boolean isRegex() {
        return (id & 0x80000000) != 0;
    }

    /**
     * 获取原规则内容是否是当前模式
     *
     * @param pattern 原规则内容
     * @return 是否是当前模式
     */
    public boolean isThisMode(@NotNull String pattern) {
        if (pattern.startsWith(IGNORE_CASE)) {
            return pattern.substring(IGNORE_CASE.length()).startsWith(start);
        }
        return pattern.startsWith(start);
    }

    public static final String IGNORE_CASE = "<i>";

    public static PatternMode getFromMessage(String message) {
        if (message.startsWith(IGNORE_CASE)) {
            message = message.substring(IGNORE_CASE.length());
        }
        for (PatternMode value : values()) {
            if (message.startsWith(value.start)) {
                return value;
            }
        }
        return FUZZY_MATCH;
    }
}
