package br.com.yolomc.bukkit.hologram.util;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class ReflectionUtils {

    public static void putInPrivateStaticMap(Class<?> clazz, String fieldName, Object key, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map<Object, Object> map = (Map)field.get((Object)null);
        map.put(key, value);
    }

    public static void setPrivateField(Class<?> clazz, Object handle, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(handle, value);
    }
}
