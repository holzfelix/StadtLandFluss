package util;

/**
 *
 * @author Felix
 */
public class Security {
    /**
     * Default Konstruktor.
     */
    public Security() { }

    /**
     * Validierung des Feld Inputs, Prevention für SQL Injections oder anderen Müll.
     * @param input String der Verarbeitet wird.
     * @return gibt einen sauberen String zurück
     */
    public final String escape(final String input) {
        System.out.println("BEVOR: " + input);
        final String pattern = "[^a-zA-Z0-9öäüÖÄÜ]";

        System.out.println("DANACH " + input.replaceAll(pattern, ""));
        return input.replaceAll(pattern, "");
    }
}
