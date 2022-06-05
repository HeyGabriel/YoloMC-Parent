package br.com.yolomc.bukkit.hologram.api.line;

import br.com.yolomc.bukkit.hologram.api.handler.TouchHandler;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface TouchableLine extends HologramLine {

    void setTouchHandler(TouchHandler touchHandler);

    TouchHandler getTouchHandler();
}
