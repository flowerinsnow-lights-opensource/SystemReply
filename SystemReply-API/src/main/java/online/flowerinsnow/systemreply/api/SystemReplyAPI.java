package online.flowerinsnow.systemreply.api;

import online.flowerinsnow.systemreply.api.manager.IEntriesManager;
import org.jetbrains.annotations.NotNull;

/**
 * SystemReply API主入口
 */
public abstract class SystemReplyAPI {
    private static ISystemReply instance;

    public static ISystemReply getInstance() {
        return instance;
    }

    public static void setInstance(ISystemReply instance) {
        SystemReplyAPI.instance = instance;
    }

    public static @NotNull IEntriesManager getEntriesManager() {
        return instance.getEntriesManager();
    }
}
