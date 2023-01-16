package online.flowerinsnow.systemreply.manager;

import online.flowerinsnow.systemreply.object.IEntry;
import org.jetbrains.annotations.NotNull;

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
     * 获取消息的所有匹配项
     *
     * @return 消息的所有匹配项
     */
    Set<IEntry> matches(String message);

    /**
     * 从配置文件重载
     */
    void reload();

    /**
     * 将修改保存至配置文件
     */
    void save();
}
