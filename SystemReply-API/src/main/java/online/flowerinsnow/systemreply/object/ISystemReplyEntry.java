package online.flowerinsnow.systemreply.object;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * SystemReply的一个条目
 * 调用完操作方法后需要手动调用保存方法才能保存配置文件
 */
public interface ISystemReplyEntry {
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
    @NotNull Set<ISystemReplyPattern> getPatterns();

    /**
     * 设置规则列表
     *
     * @param patterns 规则列表
     */
    void setPatterns(@NotNull Set<ISystemReplyPattern> patterns);

    /**
     * 删除该条目
     */
    void delete();
}
