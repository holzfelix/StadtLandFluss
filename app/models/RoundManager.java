package models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Felix
 */
public class RoundManager {

    /**
     * Hashmap für die Runden.
     */
    private final Map<String, Round> roundMap = new HashMap<>();

    /**
     * Default Konstruktor.
     */
    public RoundManager() { }

    /**
     * Startet eine neue Runde und fügt diese der Runden Liste hinzu.
     *
     * @param gameName Spielnamen für welches eine neue Runde gestartet werden soll.
     */
    public final void startNewRound(final String gameName) {
        roundMap.put(gameName, new Round());
    }

    /**
     * Stoppt eine Runde und löscht diese aus der Runden Liste.
     *
     * @param gameName Spiel für welches die Aktion durchgeführt werden soll
     */
    public final void stopRound(final String gameName) {
        getRound(gameName).stopRound();
    }

    /**
     * Gibt den Buchstaben zurück.
     *
     * @param gameName Spielname
     * @return gibt den Buchstaben als String zurück
     */
    public final String getBuchstabe(final String gameName) {
        return getRound(gameName).getBuchstabe();
    }

    /**
     * Holt eine Runde.
     *
     * @param gameName Spielname für welches eine Runde gesucht wird.
     * @return Gibt die Runde zurück.
     */
    private Round getRound(final String gameName) {
        for (String game : roundMap.keySet()) {
            if (game.equals(gameName)) {
                return roundMap.get(game);
            }
        }
        return null;
    }
}
