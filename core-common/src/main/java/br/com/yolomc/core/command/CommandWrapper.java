package br.com.yolomc.core.command;

import br.com.yolomc.core.resolver.FieldResolver;
import br.com.yolomc.core.resolver.MethodResolver;
import br.com.yolomc.core.wrapper.AbstractWrapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Wrapper que armazena Comandos do BukkitAPI e BungeeCord.
 *
 * @author skyprogrammer
 */
public abstract class CommandWrapper extends AbstractWrapper {

    public CommandWrapper(Object handle) {
        super(handle);
    }

    public String getName() {
        try {
            return (String) new FieldResolver(getHandle().getClass(), "name").resolve().get(getHandle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPermission() {
        try {
            return (String) new FieldResolver(getHandle().getClass(), "permission").resolve().get(getHandle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setPermission(String name) {
        try {
            new FieldResolver(getHandle().getClass(), "name").resolve().set(getHandle(), name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPermissionMessage() {
        try {
            return (String) new FieldResolver(getHandle().getClass(), "permissionMessage")
                    .resolve().get(getHandle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setPermissionMessage(String message) {
        try {
            new FieldResolver(getHandle().getClass(), "name").resolve().set(getHandle(), message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAliases(String... aliases) {
        Field field = new FieldResolver(getHandle().getClass(), "aliases").resolve();
        if (List.class.equals(field.getType())) {
            try {
                new MethodResolver(getHandle().getClass(), "setAliases").resolve()
                        .invoke(getHandle(), Arrays.asList(aliases));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                field.set(getHandle(), aliases);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean runAsync() {
        return false;
    }

    public abstract void onCommand(CommandSenderWrapper senderWrapper, String label, String[] args);
}
