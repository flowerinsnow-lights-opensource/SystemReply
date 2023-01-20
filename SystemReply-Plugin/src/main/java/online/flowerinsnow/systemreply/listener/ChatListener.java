package online.flowerinsnow.systemreply.listener;

import online.flowerinsnow.systemreply.SystemReplyPlugin;
import online.flowerinsnow.systemreply.api.object.IEntry;
import online.flowerinsnow.systemreply.config.Config;
import online.flowerinsnow.systemreply.config.Message;
import online.flowerinsnow.systemreply.shaded.online.flowerinsnow.saussureautils.spigot.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void lowestChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.LOWEST.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void lowChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.LOW.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void normalChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.NORMAL.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void highChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.HIGH.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void highestChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.HIGHEST.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitorChat(AsyncPlayerChatEvent e) {
        if (Config.ENABLE.getNotNull() && EventPriority.MONITOR.name().equalsIgnoreCase(Config.PRIORITY.getNotNull())) {
            if (!e.isCancelled() || !Config.IGNORE_CANCELLED.getNotNull()) {
                this.postCall(e, false);
            }
        }
    }

    private void postCall(AsyncPlayerChatEvent event, boolean cancellable) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        HashSet<IEntry> matches = SystemReplyPlugin.getCore().getEntriesManager().matches(message);
        HashSet<IEntry> after = new HashSet<>();
        matches.removeIf(match -> {
            // 是否有该玩家没有的权限
            if (match.getRequiredPermissions().stream().anyMatch(perm -> !player.hasPermission(perm))) {
                return true;
            }
            if (match.isBlock() && cancellable) {
                event.setCancelled(true);
            }
            if (match.isAfter()) {
                after.add(match);
                return true;
            } else {
                return false;
            }
        });
        Bukkit.getScheduler().runTask(SystemReplyPlugin.getInstance(), () ->
            after.forEach(entry ->
                sendReplies(player, entry)
            )
        );
        matches.forEach(entry ->
                sendReplies(player, entry)
        );
    }

    private void sendReplies(CommandSender sender, IEntry entry) {
        entry.getReplies().forEach(reply ->
                sender.sendMessage(MessageUtils.parseColour(entry.getName())
                        .replace("%(PREFIX)", Message.PREFIX.getNotNull())
                )
        );
    }
}
