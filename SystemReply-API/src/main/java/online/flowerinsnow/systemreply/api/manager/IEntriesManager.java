package online.flowerinsnow.systemreply.api.manager;

import online.flowerinsnow.systemreply.api.object.IEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * 条目管理器
 */
public interface IEntriesManager {
    /**
     * 获取所有条目
     *
     * @return 所有条目
     */
    @NotNull Set<IEntry> getEntries();

    /**
     * 设置所有条目
     *
     * @param entries 所有条目
     */
    void setEntries(@NotNull Collection<IEntry> entries);

    /**
     * 创建条目
     *
     * @param name 条目名
     * @param replies 回复消息列表
     * @param pass 是否允许继续匹配其他消息
     * @param async 是否在判断时异步，用于大规模匹配
     * @param block 阻止该消息发送出去
     * @param requiredPermissions 触发该条目所需的权限
     */
    @NotNull IEntry createEntry(
            @NotNull String name,
            @Nullable Collection<String> replies,
            boolean pass,
            boolean async,
            boolean block,
            Collection<String> requiredPermissions
    );

    /**
     * 获取消息的所有匹配项
     *
     * @return 消息的所有匹配项
     */
    @NotNull Set<IEntry> matches(String message);

    /**
     * 从配置文件重载
     */
    void reload();

    /**
     * 将修改保存至配置文件
     */
    void save();
}
