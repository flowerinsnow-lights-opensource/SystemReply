package online.flowerinsnow.systemreply.command;

import org.bukkit.ChatColor;
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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            }
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
