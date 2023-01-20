package online.flowerinsnow.systemreply.api;

import online.flowerinsnow.systemreply.api.manager.IEntriesManager;
import org.jetbrains.annotations.NotNull;

/**
 * SystemReply API实例
 */
public interface ISystemReply {
    /**
     * 获取条目管理器
     *
     * @return 条目管理器
     */
    @NotNull IEntriesManager getEntriesManager();
}
