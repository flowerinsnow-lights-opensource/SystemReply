package online.flowerinsnow.systemreply.config;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public class Message extends ConfigurationRoot {
    @HeaderComment("回复前缀，在entries.yml中用%(PREFIX)表示该变量")
    public static final ConfiguredValue<String> PREFIX = ConfiguredValue.of(String.class, "&6&l系统回复> ");
}
