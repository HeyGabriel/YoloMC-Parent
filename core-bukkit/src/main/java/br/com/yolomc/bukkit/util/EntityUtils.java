package br.com.yolomc.bukkit.util;

import com.comphenix.protocol.utility.MinecraftReflection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.lang.reflect.Field;

/**
 * Classe de utilidades diversas e para criar entityIds.
 */
public class EntityUtils {

    public static synchronized int next() {
        try {
            Class<?> clazz = MinecraftReflection.getEntityClass();
            Field field = clazz.getDeclaredField("entityCount");
            field.setAccessible(true);
            int id = field.getInt(null);
            field.set(null, id + 1);
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    public static void clearDrops() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }
        }
    }

    public static void clearEntities() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item || entity instanceof Animals || entity instanceof Monster ||
                        entity instanceof NPC) {
                    entity.remove();
                }
            }
        }
    }

}
