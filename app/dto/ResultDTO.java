package dto;

/**
 *
 * @author Felix
 */
public final class ResultDTO {

    /**
     * Buchstabe.
     */
    private String buchstabe;
    /**
     * Spielername.
     */
    private String username;
    /**
     * Spielname.
     */
    private String game;
    /**
     * Eingegebene Stadt.
     */
    private String stadt;
    /**
     * Eingegebenes Land.
     */
    private String land;
    /**
     * Eingegebener Fluss.
     */
    private String fluss;
    /**
     * Eingegebener Name.
     */
    private String name;
    /**
     * Eingebener Beruf.
     */
    private String beruf;
    /**
     * Eingegebene Pflanze.
     */
    private String pflanze;
    /**
     * Punktevergabe für die Stadt Einagbe.
     */
    private int stadtPunkte;
    /**
     * Punktevergabe für die Land Einagbe.
     */
    private int landPunkte;
    /**
     * Punktevergabe für die Fluss Einagbe.
     */
    private int flussPunkte;
    /**
     * Punktevergabe für die Namens Einagbe.
     */
    private int namePunkte;
    /**
     * Punktevergabe für die Berufs Einagbe.
     */
    private int berufPunkte;
    /**
     * Punktevergabe für die Pflanzen Einagbe.
     */
    private int pflanzePunkte;

    /**
     * Default Konstruktor.
     */
    public ResultDTO() {
        this.username = null;
    }

    /**
     * Usernamen Setzen.
     *
     * @param nutzername Name des Speilers
     */
    public void setUsername(final String nutzername) {
        this.username = nutzername;
    }

    /**
     * Buchstaben holen.
     *
     * @return gibt den Buchstaben als String zurück.
     */
    public String getBuchstabe() {
        return buchstabe;
    }

    /**
     * Buchstaben setzen.
     *
     * @param uebuchstabe Der gesetzt werden soll.
     */
    public void setBuchstabe(final String uebuchstabe) {
        this.buchstabe = uebuchstabe;
    }

    /**
     * Spiel holen.
     *
     * @return String
     */
    public String getGame() {
        return game;
    }

    /**
     * Spiel setzen.
     *
     * @param gameNAME Spielname welcher übergeben wird.
     */
    public void setGame(final String gameNAME) {
        this.game = gameNAME;
    }

    /**
     * Usernamen holen.
     *
     * @return String Nutzername
     */
    public String getUsername() {
        return username;
    }

    /**
     * Stadt holen.
     *
     * @return String Stadt
     */
    public String getStadt() {
        return stadt;
    }

    /**
     * Stadt Setzen.
     *
     * @param stadtEingabe die gesetzt werden soll
     */
    public void setStadt(final String stadtEingabe) {
        this.stadt = stadtEingabe;
    }

    /**
     * Gibt das Land zurück.
     *
     * @return String (Stadt)
     */
    public String getLand() {
        return land;
    }

    /**
     * Setzt das Land.
     *
     * @param landEingabe Land das gesetzt werden soll.
     */
    public void setLand(final String landEingabe) {
        this.land = landEingabe;
    }

    /**
     * Holt den Fluss.
     *
     * @return String fluss
     */
    public String getFluss() {
        return fluss;
    }

    /**
     * Setzt den Fluss.
     *
     * @param flussEingabe Fluss der gesetzt werden soll.
     */
    public void setFluss(final String flussEingabe) {
        this.fluss = flussEingabe;
    }

    /**
     * Gibt den Namen zurück.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen.
     *
     * @param nameEingabe String
     */
    public void setName(final String nameEingabe) {
        this.name = nameEingabe;
    }

    /**
     * Gibt den Beruf zurück.
     *
     * @return String
     */
    public String getBeruf() {
        return beruf;
    }

    /**
     * Setzt den Beruf.
     *
     * @param berufEingabe Beruf der gesetzt werden soll.
     */
    public void setBeruf(final String berufEingabe) {
        this.beruf = berufEingabe;
    }

    /**
     * Gibt die Pflanze zurück.
     *
     * @return String
     */
    public String getPflanze() {
        return pflanze;
    }

    /**
     * Setzt die Pflanze.
     *
     * @param pflanzeEingabe Pflanze die gesetzt werden soll
     */
    public void setPflanze(final String pflanzeEingabe) {
        this.pflanze = pflanzeEingabe;
    }

    /**
     * Gibt die Punkte die für die Stadt eingabe vergeben wurden zurück.
     *
     * @return Int Punkte
     */
    public int getStadtPunkte() {
        return stadtPunkte;
    }

    /**
     * Setzt die Stadt-Punkte.
     *
     * @param stadtpunkte Int-Wert für die Punkte
     */
    public void setStadtPunkte(final int stadtpunkte) {
        this.stadtPunkte = stadtpunkte;
    }

    /**
     * Gibt die Punkte die für die Land Eingabe vergeben wurden zurück.
     *
     * @return Int-Wert
     */
    public int getLandPunkte() {
        return landPunkte;
    }

    /**
     * Setzt die Land-Punkte.
     *
     * @param landpunkte Int für die LandPunkte
     */
    public void setLandPunkte(final int landpunkte) {
        this.landPunkte = landpunkte;
    }

    /**
     * Gibt die Punkte die für die Fluss Eingabe vergeben wurden zurück.
     *
     * @return Int-Wert
     */
    public int getFlussPunkte() {
        return flussPunkte;
    }

    /**
     * Setzt die Fluss Punkte.
     *
     * @param flusspunkte Punkte die gesetzt werden sollen
     */
    public void setFlussPunkte(final int flusspunkte) {
        this.flussPunkte = flusspunkte;
    }

    /**
     * Gibt die Punkte die für die Namens Eingabe vergeben wurden zurück.
     *
     * @return Int-Wert
     */
    public int getNamePunkte() {
        return namePunkte;
    }

    /**
     * Setzt die Punkte für den Namen.
     *
     * @param namepunkte Int-Wert der gesetzt werden soll
     */
    public void setNamePunkte(final int namepunkte) {
        this.namePunkte = namepunkte;
    }

    /**
     * Gibt die Punkte die für die Beruf Eingabe vergeben wurden zurück.
     *
     * @return Int-Wert
     */
    public int getBerufPunkte() {
        return berufPunkte;
    }

    /**
     * Beruf Punkte die gesetzt werden sollen.
     *
     * @param berufpunkte Int-Wert
     */
    public void setBerufPunkte(final int berufpunkte) {
        this.berufPunkte = berufpunkte;
    }

    /**
     * Gibt die Punkte die für die Pflanzen Eingabe vergeben wurden zurück.
     *
     * @return Int-Wert
     */
    public int getPflanzePunkte() {
        return pflanzePunkte;
    }

    /**
     * Setzt die Punkte die für die Pflanzen-Eingabe vergeben wurden.
     *
     * @param pflanzepunkte Int-Wert
     */
    public void setPflanzePunkte(final int pflanzepunkte) {
        this.pflanzePunkte = pflanzepunkte;
    }
}
