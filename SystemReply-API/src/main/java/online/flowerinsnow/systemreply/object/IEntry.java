package online.flowerinsnow.systemreply.object;

import org.jetbrains.annotations.NotNull;

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
    void setPatterns(@NotNull Collection<IPattern> patterns);

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
    void setReplies(@NotNull Collection<String> replies);

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
     * 获取是否在判断时异步，用于大规模匹配
     *
     * @return 是否在判断时异步
     */
    boolean isAsync();

    /**
     * 设置是否在判断时异步
     *
     * @param async 是否在判断时异步
     */
    void setAsync(boolean async);

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
     * 删除该条目
     */
    void delete();
}
