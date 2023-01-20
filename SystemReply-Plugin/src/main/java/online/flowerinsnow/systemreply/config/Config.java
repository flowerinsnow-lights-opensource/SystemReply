package online.flowerinsnow.systemreply.config;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public class Config extends ConfigurationRoot {
    @HeaderComment("是否启用")
    public static final ConfiguredValue<Boolean> ENABLE = ConfiguredValue.of(Boolean.class, true);

    @HeaderComment(
            {
                    "Bukkit事件优先级",
                    "由于可能会有其他插件也在监听AsyncPlayerChatEvent",
                    "为避免冲突，所以提供该选项。可用参数如下：",
                    "LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4), MONITOR(5)",
                    "优先级越低将越早执行，先执行的监听的事件取消权不如后执行的插件，后执行的插件在执行时消息可能已经被先执行的插件所修改",
                    "当优先级为MONITOR时阻止消息发出去(block)功能不可用"
            }
    )
    public static final ConfiguredValue<String> PRIORITY = ConfiguredValue.of(String.class, "NORMAL");

    @HeaderComment("是否在比priority优先级低的事件提前取消了该事件时忽略而不进行处理")
    public static final ConfiguredValue<Boolean> IGNORE_CANCELLED = ConfiguredValue.of(Boolean.class, false);

    public static class Debug extends ConfigurationRoot {
        @HeaderComment({"DEBUG模式", "开启后后台可能会比较吵"})
        public static final ConfiguredValue<Boolean> ENABLE = ConfiguredValue.of(Boolean.class, false);

        @HeaderComment({"DEBUG等级，决定了从国家大事到鸡毛蒜皮哪些需要DEBUG", "等级越高越详细，同样会越吵", "在enable为false时不生效"})
        public static final ConfiguredValue<Integer> LEVEL = ConfiguredValue.of(Integer.class, 2);
    }
}
