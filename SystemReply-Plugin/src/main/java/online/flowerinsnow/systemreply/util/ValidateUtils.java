package online.flowerinsnow.systemreply.util;

import org.jetbrains.annotations.Nullable;

public final class ValidateUtils {
    private ValidateUtils() {
    }

    public static <T> T notNull(@Nullable T object, @Nullable String name) {
        if (object == null) {
            if (name != null) {
                throw new NullPointerException(name);
            } else {
                throw new NullPointerException();
            }
        }
        return object;
    }
}
