package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import play.*;
import play.data.*;
import play.mvc.*;
import views.html.*;

/**
 *
 * @author Felix
 */
public abstract class StaticApplication extends Controller {

    /**
     * Rendert das Impressum.
     *
     * @return gibt die gerenderte Impressumseite zurück
     */
    public static Result impressum() {
        return ok(impressum.render());
    }

    /**
     * Rendert die Kontakt Seite.
     *
     * @return gibt die Gerenderte Contactseite zurück
     */
    public static Result contact() {
        return ok(contact.render());
    }

    /**
     * Rendert die Regel seite (Wird aber per Ajax geladen.
     *
     * @return gibt die Regeln zurück
     */
    public static Result rules() {
        return ok(rules.render());
    }

    /**
     * Sendet eine Email an info@felix-hohlwegler.de.
     *
     * @param lastname  Nachname des Absenders
     * @param firstname Vorname des Absenders
     * @param email     Email-Adresse des Absenders
     * @param subject   Betreff der Nachricht
     * @param text      Text der Nachricht
     * @return          Gibt eine Info Seite zurück
     * @throws IOException wirft Exceptions
     */
    public static Result sendMail(final String lastname, final String firstname, final String email, final String subject, final String text) throws IOException {
        InputStream in = new URL("http://felix-hohlwegler.de/stadtlandfluss/SLF_contact.php?lastname=" + lastname + "&firstname=" + firstname + "&text=" + text + "&subject=" + subject + "&email=" + email).openStream();
        return ok("");
    }

    /**
     * Sendet eine empfehlungs Mail an einen Freund.
     *
     * @param von   Name des Absenders
     * @param email Email des Empfängers
     * @return      Gibt eine Info Seite zurück
     * @throws IOException  wirft eine Exception
     */
    public static Result tellaFriend(final String von, final String email) throws IOException {
        InputStream in = new URL("http://felix-hohlwegler.de/stadtlandfluss/SLF_tellafriend.php?absender=" + von + "&email=" + email).openStream();
        return ok("");
    }
}
