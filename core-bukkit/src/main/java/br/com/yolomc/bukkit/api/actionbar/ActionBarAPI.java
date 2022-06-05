package br.com.yolomc.bukkit.api.actionbar;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import br.com.yolomc.bukkit.protocol.ProtocolVersion;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

/**
 * Forked from https://github.com/battlebits/Commons
 */
public class ActionBarAPI {

    public static void send(Player player, String text) {
        ProtocolVersion version = ProtocolVersion.getVersion(player);
        if (version != ProtocolVersion.MC_1_7_2 && version != ProtocolVersion.MC_1_7_10) {
            PacketContainer chatPacket = new PacketContainer(PacketType.Play.Server.CHAT);
            chatPacket.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\":\"" + text + " \"}"));
            chatPacket.getBytes().write(0, (byte) 2);
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, chatPacket);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet " + chatPacket, e);
            }
        }
    }

    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, text));
    }
}
