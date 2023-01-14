package models;

/**
 * Hier wird der buchstabe für die aktuelle Runde generiert.
 *
 * @author Felix
 */
public final class LetterGenerator {
    /**
     * Speichert den aktuellen Buchstaben.
     */
    private static String buchstabe;
    /**
     * Liste der verfügbaren Buchstaben.
     */
    private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * Default Konstruktor ruft die Generierung auf.
     */
    public LetterGenerator() {
        gen();
    }

    /**
     * Generiert einen zufälligen Buchstaben aus dem alphabet.
     *
     * @return Gibt einen String zurück, den Buchstaben
     */
    public String gen() {
        String erg;
        int anzahlBuchstaben = alphabet.length;
        int zufall = (int) (Math.random() * anzahlBuchstaben);
        erg = alphabet[zufall];
        return erg;
    }
}
