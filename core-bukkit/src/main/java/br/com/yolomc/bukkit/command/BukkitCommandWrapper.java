package br.com.yolomc.bukkit.command;

import br.com.yolomc.bukkit.BukkitMain;
import br.com.yolomc.core.command.CommandSenderWrapper;
import br.com.yolomc.core.command.CommandWrapper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Wrapper que armazena um comando do BukkitAPI.
 *
 * @author skyprogrammer
 */
public abstract class BukkitCommandWrapper extends CommandWrapper {

    public BukkitCommandWrapper(String name) {
        super(new BukkitCommand(name));
        ((BukkitCommand) getHandle()).setCommandWrapper(this);
        setPermissionMessage("§cVocê não possui permissão para executar este comando!");
    }

    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

    @Getter
    @Setter
    protected static class BukkitCommand extends Command {

        private BukkitCommandWrapper commandWrapper;

        protected BukkitCommand(String name) {
            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            if (!testPermission(sender))
                return true;
            if (commandWrapper != null) {
                CommandSenderWrapper senderWrapper = new CommandSenderWrapper(sender);
                if (!commandWrapper.runAsync()) {
                    try {
                        commandWrapper.onCommand(senderWrapper, label, args);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } else {
                    Bukkit.getServer().getScheduler().runTaskAsynchronously(BukkitMain.getInstance(), () -> {
                        try {
                            commandWrapper.onCommand(senderWrapper, label, args);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });
                }
            } else {
                sender.sendMessage("§cComando não tratado! Informe este erro aos administradores.");
            }
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
            List<String> completions = null;

            if (commandWrapper != null) {
                try {
                    completions = commandWrapper.onTabComplete(sender, alias, args);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            if (completions == null)
                return super.tabComplete(sender, alias, args);
            return completions;
        }
    }
}
