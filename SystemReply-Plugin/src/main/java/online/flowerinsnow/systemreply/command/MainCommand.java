package online.flowerinsnow.systemreply.command;

import online.flowerinsnow.systemreply.config.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (1 == args.length && "reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("systemreply.reload")) {
                Message.Command.RELOAD.send(sender);
            } else {
                Message.Command.NO_PERMISSION.send(sender, "systemreply.reload");
            }
        } else {
            Message.Command.USAGE.send(sender);
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (1 == args.length && sender.hasPermission("systemreply.tab")) {
            List<String> list = new ArrayList<>(Collections.singletonList("reload"));
            list.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
            return list;
        }
        return new ArrayList<>();
    }
}
