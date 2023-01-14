package models;

/**
 *
 * @author Felix
 */
public class Round {
    /**
     * Instanz des Buchstaben generators.
     */
    private LetterGenerator generator;
    /**
     * Speichert den buchstaben.
     */
    private final String buchstabe;

    /**
     * Default Konstruktor generiert einen neuen Buchstaben und Startet den
     * Counter.
     */
    public Round() {
        generator = new LetterGenerator();
        buchstabe = generator.gen();
    }

    /**
     * Stoppt eine Runde.
     */
    public final void stopRound() {
        generator = null;
    }

    /**
     * Gibt den Buchstaben zurück.
     *
     * @return String (Buchstabe)
     */
    public final String getBuchstabe() {
        return this.buchstabe;
    }
}
