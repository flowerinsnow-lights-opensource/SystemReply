package online.flowerinsnow.systemreply.api.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * SystemReply的一个条目
 * 调用完操作方法后需要手动调用保存方法才能保存配置文件
 */
public interface IEntry {
    /**
     * 获取条目名
     * 必须唯一！将作为config中的路径
     *
     * @return 条目名
     */
    @NotNull String getName();

    /**
     * 设置条目名
     * 必须唯一！将作为config中的路径。
     * 若不唯一则会覆盖旧的条目
     */
    void setName(@NotNull String name);

    /**
     * 获取规则列表
     *
     * @return 规则列表
     */
    @NotNull Set<IPattern> getPatterns();

    /**
     * 设置规则列表
     *
     * @param patterns 规则列表
     */
    void setPatterns(@Nullable Collection<IPattern> patterns);

    /**
     * 在此条目下创建规则
     *
     * @param mode 规则模式
     * @param pattern 规则内容
     * @return 创建好的规则
     */
    @NotNull IPattern createPattern(@NotNull PatternMode mode, @NotNull String pattern, boolean ignoreCase);

    /**
     * 获取回复的消息，一个代表一行
     *
     * @return 回复的消息
     */
    @NotNull List<String> getReplies();

    /**
     * 设置回复的消息，一个代表一行
     *
     * @param replies 回复的消息
     */
    void setReplies(@Nullable Collection<String> replies);

    /**
     * 获取是否允许继续匹配其他消息
     *
     * @return 是否允许继续匹配其他消息
     */
    boolean isPass();

    /**
     * 设置是否允许继续匹配其他消息
     *
     * @param pass 是否允许继续匹配其他消息
     */
    void setPass(boolean pass);

    /**
     * 是否阻止该消息发送出去
     *
     * @return 是否阻止该消息发送出去
     */
    boolean isBlock();

    /**
     * 设置是否阻止该消息发送出去
     *
     * @param block 是否阻止该消息发送出去
     */
    void setBlock(boolean block);

    /**
     * 获取触发该条目所需的权限
     *
     * @return 触发该条目所需的权限
     */
    @NotNull Set<String> getRequiredPermissions();

    /**
     * 设置触发该条目所需的权限
     *
     * @param requiredPermissions 触发该条目所需的权限
     */
    void setRequiredPermissions(@Nullable Collection<String> requiredPermissions);

    /**
     * 获取是否在玩家的消息发送出去之后将回复发送给玩家
     *
     * @return 在玩家的消息发送出去之后将回复发送给玩家
     */
    boolean isAfter();

    /**
     * 设置在玩家的消息发送出去之后将回复发送给玩家
     *
     * @param after 在玩家的消息发送出去之后将回复发送给玩家
     */
    void setAfter(boolean after);

    /**
     * 删除该条目
     */
    void delete();
}
