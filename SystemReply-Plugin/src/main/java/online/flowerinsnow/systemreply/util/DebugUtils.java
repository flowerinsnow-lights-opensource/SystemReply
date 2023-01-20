package online.flowerinsnow.systemreply.util;

import online.flowerinsnow.systemreply.SystemReplyPlugin;
import online.flowerinsnow.systemreply.config.Config;

import java.util.logging.Logger;

public final class DebugUtils {
    private DebugUtils() {
    }

    public static boolean shouldDebug(int level) {
        return Config.Debug.ENABLE.getNotNull() && Config.Debug.LEVEL.getNotNull() >= level;
    }

    public static boolean tryDebug(int level, Logger logger, String log) {
        if (shouldDebug(level)) {
            logger.info("[DEBUG] " + log);
            return true;
        }
        return false;
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryDebug(int level, String log) {
        return tryDebug(level, SystemReplyPlugin.getInstance().getLogger(), log);
    }
}
