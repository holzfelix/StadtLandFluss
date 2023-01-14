package util;

/**
 *
 * @author Felix
 */
public interface IObservabel {

    /**
     * Registtriert einen User am Observer.
     *
     * @param gameName Spielname an dem der Spieler teil nimmt.
     * @param userName Spielername
     */
    void register(final String gameName, final String userName);

    /**
     * Unregister User vom Observer. Spieler wird aus der Liste gelöscht-
     *
     * @param gameName Spielname an dem er teilgenommen hat.
     * @param userName Name des zu löschenden Spielers.
     */
    void unregister(final String gameName, final String userName);

    /**
     * Informiert den Socket daüber, dass es neue Updates gibt.
     */
    void notifyController();
}
