package online.flowerinsnow.systemreply.object;

import org.jetbrains.annotations.NotNull;

/**
 * SystemReply一个匹配规则
 * 调用完操作方法后需要手动调用保存方法才能保存配置文件
 */
public interface ISystemReplyPattern {
    /**
     * 获取包含该匹配规则的条目
     *
     * @return 包含该匹配规则的条目
     */
    @NotNull ISystemReplyEntry getOwnerEntry();

    /**
     * 获取匹配规则内容
     *
     * @return 匹配规则内容
     */
    @NotNull String getPattern();

    void setPattern(@NotNull String pattern);

    /**
     * 获取是否是正则表达式
     *
     * @return 是否是正则表达式
     */
    boolean isRegex();

    /**
     * 设置是否是正则表达式
     *
     * @param regex 是否是正则表达式
     */
    void setRegex(boolean regex);
}
