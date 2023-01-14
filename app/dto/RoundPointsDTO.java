package dto;

/**
 *
 * @author Felix
 */
public class RoundPointsDTO {

    /**
     * Speichert die Runden Punkte eines Users.
     */
    private int roundPoints;
    /**
     * Speichert die komplette Punkte-Zahl eines Users.
     */
    private int gamePoints;
    /**
     * Speichert den Benutzernamen.
     */
    private String username;
    /**
     * Speichert den Spielnamen in dem der User teilnimmt.
     */
    private String game;
    /**
     * Speichert den aktuellen Buchstaben.
     */
    private String buchstabe;
    /**
     * Speichert die Punkte die für die Stadt-Eingabe in der letzten Runde vergeben wurden.
     */
    private int stadtPunkte;
    /**
     * Speichert die Punkte die für die Land-Eingabe in der letzten Runde vergeben wurden.
     */
    private int landPunkte;
    /**
     * Speichert die Punkte die für die Fluss-Eingabe in der letzten Runde vergeben wurden.
     */
    private int flussPunkte;
    /**
     * Speichert die Punkte die für die Namens-Eingabe in der letzten Runde vergeben wurden.
     */
    private int namePunkte;
    /**
     * Speichert die Punkte die für die Berufs-Eingabe in der letzten Runde vergeben wurden.
     */
    private int berufPunkte;
    /**
     * Speichert die Punkte die für die Pflanzen-Eingabe in der letzten Runde vergeben wurden.
     */
    private int pflanzePunkte;

    /**
     * Default Konstruktor.
     */
    public RoundPointsDTO() { }

    /**
     * Konstruktor.
     *
     * @param benutzername Übergabe des Benutzernamens.
     * @param spielname Übergabe des Spielnamens an dem Teilgenommen wird.
     */
    public RoundPointsDTO(final String benutzername, final String spielname) {
        this.username = benutzername;
        this.game = spielname;
    }

    /**
     * Gibt die gesamt Punkte Zahl zurück.
     * @return Int-Wert
     */
    public final int getGamePoints() {
        return gamePoints;
    }

    /**
     * Setzt die gesamt Punktzahl für einen Benutzer.
     * @param gamepoints Zu setzende Punktezahl.
     */
    public final void setGamePoints(final int gamepoints) {
        this.gamePoints = gamepoints;
    }

    /**
     * Gibt die Punktezahl für die Stadteingabe zurück.
     * @return Int-Wert
     */
    public final int getStadtPunkte() {
        return stadtPunkte;
    }

    /**
     * Gibt den Buchstaben zurück.
     * @return String
     */
    public final String getBuchstabe() {
        return buchstabe;
    }

    /**
     * Setzt den Buchstaben.
     * @param uebuchstabe Der zu übergebende Buchstabe als String
     */
    public final void setBuchstabe(final String uebuchstabe) {
        this.buchstabe = uebuchstabe;
    }

    /**
     * Setzt die Punkte für Stadt Einagbe des Users.
     * @param stadtpunkte Int-Wert
     */
    public final void setStadtPunkte(final int stadtpunkte) {
        this.stadtPunkte = stadtpunkte;
    }

    /**
     * Gibt die Punkte für die Land-Eingabe zurück.
     * @return Int-Wert
     */
    public final int getLandPunkte() {
        return landPunkte;
    }

    /**
     * Setzt die Punkte für die Land-Eingabe.
     * @param landpunkte Int-Wert der zu übergeben ist.
     */
    public final void setLandPunkte(final int landpunkte) {
        this.landPunkte = landpunkte;
    }

    /**
     * Gibt die Vergebenen Punkte für die Fluss Eingabe zurück.
     * @return Int-Wert
     */
    public final int getFlussPunkte() {
        return flussPunkte;
    }

    /**
     * Setzt die Punkte für die Fluss-Eingabe.
     * @param flusspunkte Int-Wert
     */
    public final void setFlussPunkte(final int flusspunkte) {
        this.flussPunkte = flusspunkte;
    }

    /**
     * Gibt die Punkte für die Namensauswahl zurück.
     * @return Int-Wert
     */
    public final int getNamePunkte() {
        return namePunkte;
    }

    /**
     * Speichert die Namenspunkte.
     * @param namepunkte Int-Wert
     */
    public final void setNamePunkte(final int namepunkte) {
        this.namePunkte = namepunkte;
    }

    /**
     * Gibt die Berufspunkte zurück.
     * @return Int-Wert
     */
    public final int getBerufPunkte() {
        return berufPunkte;
    }

    /**
     * Setzt die Punkte für den Beruf.
     * @param berufpunkte Übergabe der Berufspunkte.
     */
    public final void setBerufPunkte(final int berufpunkte) {
        this.berufPunkte = berufpunkte;
    }

    /**
     * Gibt die Punkte für die Pflanze zurück.
     * @return Int-Wert
     */
    public final int getPflanzePunkte() {
        return pflanzePunkte;
    }

    /**
     * Setzt die Pflanzen Punkte.
     * @param pflanzepunkte Int-Wert der zu übergeben ist.
     */
    public final void setPflanzePunkte(final int pflanzepunkte) {
        this.pflanzePunkte = pflanzepunkte;
    }

    /**
     * Gibt die Runden Punkte des Spielers zurück.
     * @return Int-Wert
     */
    public final int getRoundPoints() {
        return roundPoints;
    }

    /**
     * Setzt die Punkte die ein Spieler in der Runde bekommen hat.
     * @param roundpoints RundenPunkte als Int-Wert zu übergeben.
     */
    public final void setRoundPoints(final int roundpoints) {
        this.roundPoints = roundpoints;
    }

    /**
     * Gibt den Nutzernamen zurück.
     * @return String
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Setzt den BenutzerNamen.
     * @param userName String wird erwartet.
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    /**
     * Gibt den Spielnamen zurück.
     * @return String
     */
    public final String getGame() {
        return game;
    }

    /**
     * Setzt den Spielnamen an dem der Spieler teilnimmt.
     * @param gameName String wird erwartet.
     */
    public final void setGame(final String gameName) {
        this.game = gameName;
    }
}
