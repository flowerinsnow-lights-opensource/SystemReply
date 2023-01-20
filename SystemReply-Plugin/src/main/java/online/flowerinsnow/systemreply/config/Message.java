package online.flowerinsnow.systemreply.config;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;
import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessageList;

public class Message extends ConfigurationRoot {
    @HeaderComment("回复前缀，在entries.yml中用%(PREFIX)表示该变量")
    public static final ConfiguredValue<String> PREFIX = ConfiguredValue.of(String.class, "&6&l系统回复> ");

    public static class Command extends ConfigurationRoot {
        @HeaderComment("变量：%(permission)")
        public static final ConfiguredMessageList<String> NO_PERMISSION = ConfiguredMessageList.asStrings()
                .defaults("您没有使用该命令的权限")
                .params("permission")
                .build();

        public static final ConfiguredMessageList<String> USAGE = ConfiguredMessageList.ofStrings(
                "&7- &f/systemreply reload",
                "&7# 重新加载"
        );

        public static final ConfiguredMessageList<String> RELOAD = ConfiguredMessageList.ofStrings("&b重载成功");
    }
}
