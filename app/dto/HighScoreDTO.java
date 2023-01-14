package dto;

import java.util.Date;

/**
 *
 * @author Felix
 */
public class HighScoreDTO {
    /**
     * Postion des Spielers.
     */
    private int position;
    /**
     * Name des Users.
     */
    private String user;
    /**
     * Punkte des Users.
     */
    private int points;
    /**
     * Datum der HighScore Eintragung.
     */
    private Date date;
    /**
     * Default Konstruktor.
     */
    public HighScoreDTO() { }

    /**
     * Gibt die Aktuelle Position zurück.
     * @return Gibt die Position zurück. Int-Wert.
     */
    public final int getPosition() {
        return position;
    }

    /**
     * Setzt die aktuelle Position im Highscore.
     * @param positionSET HighScore Position
     */
    public final void setPosition(final int positionSET) {
        this.position = positionSET;
    }

    /**
     * Gibt den User des Eintrags zurück.
     * @return Nutzername (String)
     */
    public final String getUser() {
        return user;
    }

    /**
     * Benutzer Setzen.
     * @param userName Spielername (String)
     */
    public final void setUser(final String userName) {
        this.user = userName;
    }

    /**
     * Punkte zurückgeben.
     * @return Punkte (int)
     */
    public final int getPoints() {
        return points;
    }

    /**
     * Punkte setzen.
     * @param pointScore Punkte die gespeichert werden sollen.
     */
    public final void setPoints(final int pointScore) {
        this.points = pointScore;
    }

    /**
     * Datum der HighScore eintragung zurück geben.
     * @return Datum
     */
    public final Date getDate() {
        return date;
    }

    /**
     * Datum der HighScore Eintragung setzen.
     * @param highscoreDate Datum der Eintragung (Aktuelles Datum) vom Typ Date
     */
    public final void setDate(final Date highscoreDate) {
        this.date = highscoreDate;
    }
}