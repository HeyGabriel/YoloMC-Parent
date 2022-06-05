package br.com.yolomc.bukkit.event.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Evento que representa o envio de uma mensagem de jogadores ao
 * servidor.
 *
 * @author skyprogrammer
 */
public class PlayerChatResponseEvent extends PlayerEvent implements Cancellable {

	private final static HandlerList handlerList = new HandlerList();
	
	private String format;
	private String message;
	private boolean cancelled = false;
	
	public PlayerChatResponseEvent(Player player, String message) {
		super(player);
		this.format = "";
		this.message = message;
	}
	
	public String getFormat() {
		return this.format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}
}
