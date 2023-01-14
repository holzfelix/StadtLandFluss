package dto;

import play.*;
import play.data.*;
import play.libs.*;
import play.libs.F.*;
import play.mvc.*;
import views.html.*;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * DTO zum Speichern der Sockets ... entspricht eigentlich einem Bean
 *
 * @author Felix
 */
public class SocketDTO {

    /**
     * Speichert den Spielernamen.
     */
    private final String userName;
    /**
     * Speichert den Spielnamen.
     */
    private final String gameName;
    /**
     * Speichert den Websocket.
     */
    private final WebSocket.Out<JsonNode> out;

    /**
     * Konstruktor.
     *
     * @param username Spielername (String)
     * @param gamename Spielname (String)
     * @param outSocket schreibender Websocket (WebSocket)
     */
    public SocketDTO(final String username, final String gamename, final WebSocket.Out<JsonNode> outSocket) {
        this.userName = username;
        this.gameName = gamename;
        this.out = outSocket;
    }

    /**
     * Gibt den UserNamen zurück.
     *
     * @return String
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Gibt den Spielnamen zurück.
     *
     * @return (String)
     */
    public final String getGameName() {
        return gameName;
    }

    /**
     * Gibt den Websocket zurück.
     *
     * @return Socket
     */
    public final WebSocket.Out<JsonNode> getOut() {
        return out;
    }
}
