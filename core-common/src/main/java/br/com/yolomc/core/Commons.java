package br.com.yolomc.core;

import br.com.yolomc.core.group.GroupManager;
import br.com.yolomc.core.profile.YoloPlayer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Classe API de utilidades para todos os plugins.
 *
 * @author skyprogrammer
 */
public class Commons {

    public static final UUID CONSOLE_UNIQUEID = UUID.nameUUIDFromBytes(("Console$ender").
            getBytes(StandardCharsets.UTF_8));

    @Getter
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithModifiers(Modifier.STATIC,
            Modifier.TRANSIENT, Modifier.VOLATILE).create();
    @Getter
    private static JsonParser jsonParser = new JsonParser();
    @Getter
    @Setter
    private static Logger logger;
    @Getter
    private static HashMap<UUID, YoloPlayer> accountMap = new HashMap<>();

    @Getter
    @Setter
    private static GroupManager groupManager;
}
