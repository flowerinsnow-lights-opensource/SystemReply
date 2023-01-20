package online.flowerinsnow.systemreply.impl;

import online.flowerinsnow.systemreply.api.ISystemReply;
import online.flowerinsnow.systemreply.impl.manager.EntriesManagerImpl;
import org.jetbrains.annotations.NotNull;

public class SystemReplyCore implements ISystemReply {
    public final EntriesManagerImpl entriesManager;

    public SystemReplyCore(EntriesManagerImpl entriesManager) {
        this.entriesManager = entriesManager;
    }

    @Override
    public @NotNull EntriesManagerImpl getEntriesManager() {
        return entriesManager;
    }
}
