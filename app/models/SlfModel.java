package models;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import dto.HighScoreDTO;
import dto.ResultDTO;
import dto.RoundPointsDTO;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import util.*;


/**
 *
 * @author Felix
 */
public final class SlfModel extends Application implements IObservabel {
    /**
     * Private Instanz (Singleton Pattern).
     */
    private static SlfModel instance;
    /**
     * Speichert die Instanz der Spiellogik.
     */
    private PlayLogic playlogic;
    /**
     * Liste der Ergebnisse.
     */
    private HashSet<ResultDTO> results = new HashSet<>();
    /**
     * Liste der Ergebnisse die Zurück gegeben werden.
     */
    private HashSet<RoundPointsDTO> userResults;
    /**
     * MYSQL instanz für Highscore schreiben.
     */
    private final MySQLLink mySQL;

    /**
     * Liste der Observer.
     */
    private final HashSet<RoundPointsDTO> observers = new HashSet<>();

    /**
     * privater Konstruktor, ist Privat wegen dem singleton Pattern.
     *
     * @throws ClassNotFoundException Wirft Exceptions
     * @throws SQLException Wirft Exceptions
     */
    private SlfModel() throws ClassNotFoundException, SQLException {
        mySQL = MySQLLink.getInstance();
    }

    /**
     * Instanz der Security Klasse.
     */
    private final Security security = new Security();

    /**
     * Singleton Pattern für das SlfModel, es kann immer nur eine Instanz erzeugt werden.
     *
     * @return gibt die Instanz zurück
     * @throws ClassNotFoundException Wirft Exceptions
     * @throws SQLException Wirft Exceptions
     */
    public static SlfModel getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new SlfModel();
        }
        return instance;
    }

    /**
     * Methode die eine Spiel-Runde Startet, erzeugt auch den neuen Buchstaben und speichert diesen in den Observern ab.
     *
     * @param gameName Der Spielname für den eine neue Runde starten soll.
     * @throws ClassNotFoundException Wirft Exceptions
     * @throws SQLException Wirft Exceptions
     */
    public void startGame(final String gameName) throws ClassNotFoundException, SQLException {
        playlogic = new PlayLogic(gameName);
        String buchstabe = playlogic.getBuchstabe(gameName);

        // Setzt für alle Spieleteilnehmer eines Spiels den neuen buchstaben
        for (RoundPointsDTO obs : observers) {
            if (obs.getGame().equals(gameName)) {
                obs.setBuchstabe(buchstabe);
            }
        }
    }

    /**
     * End Game, beendet eine Runde, wird durch einen User ausgelöst.
     *
     * @param gameName Spielname für welches eine Runde beendet werden soll
     * @param event Event mit allen notwendigen Informationen, Benutzer eingaben etc.
     * @param users Anzahl Spieler
     * @throws ClassNotFoundException Wirft Exceptions
     * @throws SQLException Wirft Exceptions
     */
    public void endGame(final String gameName, final JsonNode event, final int users) throws ClassNotFoundException, SQLException {
        try {
            System.out.println("ENDROUND");
            results = null; // alte Liste Löschen
            results = new HashSet<>(); // neue Liste anlegen
            createErgebnisList(event, users); // Liste füllen
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * Prüft die Ergebnisse alle anderen Spielteilnehmer.
     *
     * @param gameName Spielname für welches Spiel die aktion ausgeführt werden soll
     * @param event Event mit allen notwendigen Informationen, Benutzer eingaben etc.
     * @param users anzahl spieler
     * @throws ClassNotFoundException Wirft Exceptions
     * @throws SQLException Wirft Exceptions
     */
    public void checkOthers(final String gameName, final JsonNode event, final int users) throws ClassNotFoundException, SQLException {
        try {
            createErgebnisList(event, users);
        } catch (Exception e) {
            throw (e);
        }
    }

    /**
     * Fügt einen Spieler dem Highscore hinzu.
     *
     * @param gamename An welchem spiel der User teil nimmt.
     * @param username Der User selber.
     * @throws Exception Wirft Exceptions
     */
    public void setHighScore(final String gamename, final String username) throws Exception {
        for (RoundPointsDTO ergebnisse : observers) {
            if (ergebnisse.getGame().equals(gamename) && ergebnisse.getUsername().equals(username)) {
                mySQL.writeHighScore(ergebnisse);
            }
        }
    }

    /**
     * Holt die aktuele Highscore Liste aus der Datenbank.
     *
     * @return gibt eine Liste mit DTOs zurück.
     * @throws Exception Wirft Exceptions
     */
    public List<HighScoreDTO> getHighScoreeeee() throws Exception {
        return MySQLLink.getInstance().getHighScore();
    }

    /**
     * Erzeugt eine Ergebnis liste mit allen Punkten etc.
     *
     * @param event Event mit allen notwendigen Informationen, Benutzer eingaben etc.
     * @param users Anzahl spieler
     */
    public void createErgebnisList(final JsonNode event, final int users) {
        try {
            String username = security.escape(event.get("user").textValue());
            String game = security.escape(event.get("gamename").textValue());
            String stadt = security.escape(event.get("stadt").textValue());
            String land = security.escape(event.get("land").textValue());
            String fluss = security.escape(event.get("fluss").textValue());
            String name = security.escape(event.get("name").textValue());
            String beruf = security.escape(event.get("beruf").textValue());
            String pflanze = security.escape(event.get("pflanze").textValue());
            String buchstabe = security.escape(event.get("buchstabe").textValue());

            ResultDTO temp = new ResultDTO();
            temp.setUsername(username);
            temp.setBeruf(beruf);
            temp.setFluss(fluss);
            temp.setGame(game);
            temp.setLand(land);
            temp.setName(name);
            temp.setPflanze(pflanze);
            temp.setStadt(stadt);
            temp.setBuchstabe(buchstabe);

            results.add(temp);
            System.out.println("Results Size: " + results.size());

            if (results.size() == users) {
                System.out.println("Ergebnisse werden verarbeitet");
                // Verarbeitet alle Spieler eingaben! .. Alle eingaben werden als Hashset übergeben
                userResults = playlogic.stopGame(game, results);

                // Ergebnisse an die ObserverListe weitergeben
                copyResultsToObservers(userResults, observers);

                // Controller über änderungen Informieren
                notifyController();
            }
        } catch (Exception e) {
            System.out.println("KACKA GEHT NICH" + e);
        }
    }

    /* -------------------------------------------------------------------------------
     ObserverPattern Funktionen
     -------------------------------------------------------------------------------
     */
    /**
     * Kopiert die Ergebnisse des Runden-Auswertens in die ObserverListe.
     *
     * @param quelle Quelle --> Liste der Ergebnisse
     * @param ziel Ziel --> Observerliste
     */
    public void copyResultsToObservers(final HashSet<RoundPointsDTO> quelle, final HashSet<RoundPointsDTO> ziel) {
        //Foreach über die Ergebnisse
        for (RoundPointsDTO ergebnisse : quelle) {
            // Foreach über die Observer
            for (RoundPointsDTO obs : ziel) {
                if (ergebnisse.getGame().equals(obs.getGame()) && ergebnisse.getUsername().equals(obs.getUsername())) {
                    obs.setGamePoints(obs.getGamePoints() + ergebnisse.getRoundPoints());
                    obs.setRoundPoints(ergebnisse.getRoundPoints());
                    obs.setStadtPunkte(ergebnisse.getStadtPunkte());
                    obs.setLandPunkte(ergebnisse.getLandPunkte());
                    obs.setFlussPunkte(ergebnisse.getFlussPunkte());
                    obs.setNamePunkte(ergebnisse.getNamePunkte());
                    obs.setBerufPunkte(ergebnisse.getBerufPunkte());
                    obs.setPflanzePunkte(ergebnisse.getPflanzePunkte());
                }
            }
        }
    }

    /**
     * Registriert einen neuen Spieler am Observer.
     *
     * @param gameName Spielname, für welches Spiel der Benutzer sich anmeldet.
     * @param userName Nutzername des Users der sich anmelden möchte
     */
    @Override
    public void register(final String gameName, final String userName) {
        observers.add(new RoundPointsDTO(userName, gameName));
        System.out.println("Anzahl Observers: " + observers.size());
    }

    /**
     * Informiert den Controller über Updates .... Controller muss sich die
     * Updates dann holen
     */
    @Override
    public void notifyController() {
        update();
    }

    /**
     * Löscht einen Benutzer aus der ObserverListe.
     *
     * @param gameName Spielname, von welchem Spiel der Benutzer sich abmeldet.
     * @param userName Nutzername des Users der sich abmelden möchte
     */
    @Override
    public void unregister(final String gameName, final String userName) {
        for (RoundPointsDTO obs : observers) {
            if (obs.getGame().equals(gameName) && obs.getUsername().equals(userName)) {
                observers.remove(obs);
            }
        }
    }

    /**
     * Update gibt alles an den Controller sofern dieser das will.
     *
     * @param gameName Spielname für welches die Daten abgerufen werden
     * @return gibt ein HashSet zurück
     */
    public HashSet<RoundPointsDTO> getUpdate(final String gameName) {
        System.out.println("Updates von: " + gameName + " holen");
        HashSet<RoundPointsDTO> returns = new HashSet<>();

        for (RoundPointsDTO obs : observers) {
            if (obs.getGame().equals(gameName)) {
                returns.add(obs);
            }
        }

        return returns;
    }
}
