package br.com.yolomc.core.command;

import br.com.yolomc.core.Commons;
import br.com.yolomc.core.resolver.MethodResolver;
import br.com.yolomc.core.wrapper.AbstractWrapper;

import java.util.UUID;

/**
 * Wrapper que armazena CommandSender do BukkitAPI e BungeeCord.
 */
public class CommandSenderWrapper extends AbstractWrapper {

    public CommandSenderWrapper(Object handle) {
        super(handle);
    }

    public UUID getUniqueId() {
        try {
            if (isCraftPlayerClass()) {
                return (UUID) new MethodResolver(getHandle().getClass(), "getUniqueId").resolve()
                        .invoke(getHandle());
            } else {
                return Commons.CONSOLE_UNIQUEID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(String message) {
        try {
            new MethodResolver(getHandle().getClass(), "sendMessage", String.class)
                    .resolve().invoke(getHandle(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInGameMessage() {
        sendMessage("§cComando disponível apenas in-game!");
    }

    public boolean hasPermission(String name) {
        try {
            return (boolean) new MethodResolver(getHandle().getClass(), "hasPermission", String.class)
                    .resolve().invoke(getHandle(), name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCraftPlayerClass() {
        return getHandle().getClass().getSimpleName().equals("CraftPlayer");
    }
}
