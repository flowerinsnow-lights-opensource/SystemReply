package online.flowerinsnow.systemreply.config;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public class Config extends ConfigurationRoot {
    @HeaderComment("是否启用")
    public static final ConfiguredValue<Boolean> ENABLE = ConfiguredValue.of(Boolean.class, true);
}
