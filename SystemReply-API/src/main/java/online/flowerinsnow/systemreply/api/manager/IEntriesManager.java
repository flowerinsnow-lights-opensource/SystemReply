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
     * 创建条目
     *
     * @param name 条目名
     * @param replies 回复消息列表
     * @param pass 是否允许继续匹配其他消息
     * @param block 阻止该消息发送出去
     * @param requiredPermissions 触发该条目所需的权限
     * @param after 是否在玩家消息发送出去之后再回复玩家
     */
    @NotNull IEntry createEntry(
            @NotNull String name,
            @Nullable Collection<String> replies,
            boolean pass,
            boolean block,
            @Nullable Collection<String> requiredPermissions,
            boolean after
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
