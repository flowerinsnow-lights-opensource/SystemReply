package online.flowerinsnow.systemreply.api.object;

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

    /**
     * 获取是否忽略大小写
     *
     * @return 是否忽略大小写
     */
    boolean isIgnoreCase();

    /**
     * 设置是否忽略大小写
     *
     * @param ignoreCase 是否忽略大小写
     */
    void setIgnoreCase(boolean ignoreCase);

    /**
     * 是否匹配该消息
     *
     * @param message 消息
     * @return 是否匹配
     */
    boolean match(@NotNull String message);

    /**
     * 将该规则编写为文本内容
     *
     * @return 编写的文本内容
     */
    @NotNull String text();
}
