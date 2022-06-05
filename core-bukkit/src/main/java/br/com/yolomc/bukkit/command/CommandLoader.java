package br.com.yolomc.bukkit.command;

import br.com.yolomc.bukkit.BukkitMain;
import br.com.yolomc.core.Commons;
import br.com.yolomc.core.resolver.FieldResolver;
import br.com.yolomc.core.util.ClassGetter;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

@Getter
public class CommandLoader {

    private BukkitMain plugin;
    private CommandMap commandMap;

    public CommandLoader(BukkitMain plugin) {
        this.plugin = plugin;
        try {
            commandMap = (CommandMap) new FieldResolver(plugin.getServer().getClass(), "commandMap")
                    .resolve().get(plugin.getServer());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCommandsFromPackage(String pkg) {
        for (Class<?> clazz : ClassGetter.getClassesForPackageByPlugin(plugin, pkg)) {
            if (BukkitCommandWrapper.class.isAssignableFrom(clazz)) {
               try {
                   BukkitCommandWrapper commandWrapper = (BukkitCommandWrapper) clazz.getConstructor()
                           .newInstance();
                   commandMap.register(plugin.getName(), (Command) commandWrapper.getHandle());
                   Commons.getLogger().info("Comando '/" + commandWrapper.getName() + "' registrado.");
               } catch (Exception e) {
                   e.printStackTrace();
               }
            }
        }
    }
}
