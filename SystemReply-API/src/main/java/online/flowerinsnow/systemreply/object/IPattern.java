package online.flowerinsnow.systemreply.object;

import org.jetbrains.annotations.NotNull;

/**
 * SystemReply一个匹配规则
 * 调用完操作方法后需要手动调用保存方法才能保存配置文件
 */
public interface IPattern {
    /**
     * 获取包含该匹配规则的条目
     *
     * @return 包含该匹配规则的条目
     */
    @NotNull IEntry getOwnerEntry();

    /**
     * 获取匹配规则内容
     *
     * @return 匹配规则内容
     */
    @NotNull String getPattern();

    /**
     * 设置规则内容
     *
     * @param pattern 规则内容
     */
    void setPattern(@NotNull String pattern);

    /**
     * 获取匹配模式
     *
     * @return 匹配模式
     */
    @NotNull PatternMode getMode();

    /**
     * 设置匹配模式
     *
     * @param mode 匹配模式
     */
    void setMode(@NotNull PatternMode mode);
}
