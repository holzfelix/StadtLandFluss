package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.HighScoreDTO;
import dto.RoundPointsDTO;
import dto.SocketDTO;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;
import play.*;
import play.data.*;
import play.libs.*;
import play.libs.F.*;
import play.libs.F.Callback;
import play.mvc.*;
import util.*;
import views.html.*;

/**
 *
 * @author Felix
 */
public abstract class Application extends Controller implements IObserver {
    /**
     * Klassenvariable für den titel der Seite der über die Scala
     * notation importiert wird.
     */
    private static final String TITLE = "Stadt-Land-Fluss";
    /**
     * private Klassen-Variable für den Nutzernamen.
     */
    private static String user;
    /**
     * private Klassen-Variable für den Spielnamen.
     */
    private static String game;
    /**
     * private Klassen-Variable für die Instanz des SlfModels.
     */
    private static SlfModel slfmodel;

    /**
     * Liste der WebSockets --> gespeichert in einer DTO.
     */
    private static final Map<Integer, SocketDTO> SOCKETLIST = new HashMap<>();

    /**
     * Rendern der Startseite.
     *
     * @return Gibt eine Gerenderte Seite zurück
     */
    public static Result index() {
        return ok(index.render(TITLE));
    }

    /**
     * Rendert die Spielfäche.
     *
     * @param gameName  Erwartet den Spielnamen
     * @param userName  Erwartet den Spielernamen
     * @return  Rendert die Spielfläche
     */
    public static Result initGame(final String gameName, final String userName) {
        if (!gameName.equals("") && !userName.equals("")) {
            // Spielname + Username in Session speichern
            session("connected", userName);
            session("connectedGame", gameName);
            user = session("connected");
            game = session("connectedGame");
            out.println("\n\nUsername: " + user + " - Spielname: " + game);
            return ok(spiel.render(gameName, userName));  // Rendert die spielfläche
        } else {
            return ok(index.render(TITLE));
        }
    }

    /**
     * Highscore Ausgeben - wird per Ajax geladen.
     *
     * @return  Gibt den aktuellen HighScore zurück
     */
    public static Result getHighScore() {
        // Auslesen der HighScores
        try {
            slfmodel = SlfModel.getInstance();
            int i = 0;
            List<HighScoreDTO> high = slfmodel.getHighScoreeeee();
            ObjectNode transfer = Json.newObject();

            for (HighScoreDTO score : high) {
                i++;
                ObjectNode gamer = Json.newObject();
                gamer.put("position", score.getPosition());
                gamer.put("name", score.getUser());
                gamer.put("score", score.getPoints());
                gamer.put("date", score.getDate().toString());

                transfer.put("gamer" + i, gamer);
            }
            return ok(transfer);
        } catch (Exception e) {
            System.out.println("Highscore EXCEPTION: " + e);
        }

        return ok("BLUBB");
    }

    // --------------------------------------------------
    // Web Socket ersetllen
    // --------------------------------------------------
    /**
     * Erzeugen des Sockets.
     *
     * @param username Erwartet den Spielnamen
     * @param gamename Erwartet den Spielernamen
     * @return Gibt den Websocket zurück
     */
    public static WebSocket<JsonNode> game(final String username, final String gamename) {
        WebSocket<JsonNode> ws = null;
        try {
            ws = new WebSocket<JsonNode>() {
                public void onReady(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
                    System.out.println("\n----------------------------\nHallo der Websocket lebt\n");
                    try {
                        join(in, out, gamename, username);
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                }
            };
        } catch (Exception e) {
            System.err.println("Exception while creating WebSocket: " + e.getMessage());
        }
        return ws;
    }

    /**
     * Verarbeiten der Socket anfragen.
     *
     * @param in    Erwartet Den Websocket in (empfangend)
     * @param out   Erwartet den Websocket out (sendend)
     * @param gameName  Erwartet den Spielnamen
     * @param userName  Erwartet den Spielernamen
     * @throws Exception Gibt im Fehlerfall eine Exception zurück
     */
    public static void join(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out, final String gameName, final String userName) throws Exception {
        SocketDTO socket = new SocketDTO(userName, gameName, out);
        SOCKETLIST.put(SOCKETLIST.size() + 1, socket);

        System.out.println("Anzahl sockets: " + SOCKETLIST.size());

        // Reaktion auf jedes ereignis
        in.onMessage(new Callback<JsonNode>() {
            public void invoke(final JsonNode event) throws Exception {
                // StartGame
                String gamestatus = null;
                if ("startGame".equals(event.get("action").textValue())) {
                    if (getUserCount(event.get("gamename").textValue()) < 2) {
                        slfmodel = SlfModel.getInstance();
                        slfmodel.register(event.get("gamename").textValue(), event.get("user").textValue());
                        notifyAllDTO(event.get("user").textValue(), "New User Joined", event.get("gamename").textValue(), "notstarted"); // Meldung an alle dass ein Neuer User gejoined ist
                    } else {
                        slfmodel = SlfModel.getInstance();
                        slfmodel.register(event.get("gamename").textValue(), event.get("user").textValue());
                        slfmodel.startGame(event.get("gamename").textValue());
                        notifyAllDTO(event.get("user").textValue(), "New User Joined", event.get("gamename").textValue(), "started"); // Meldung an alle dass ein Neuer User gejoined ist
                    }
                }

                // Verarbeiten der Chat Nachricht
                if ("chat".equals(event.get("action").textValue())) {
                    chatNotifyDTO(event.get("user").textValue(), event.get("text").textValue(), event.get("gamename").textValue());
                }

                // Eintragen eines HighScore eintrag
                if ("highscore".equals(event.get("action").textValue())) {
                    try {
                        System.out.println("HighScore wird eingetragen");
                        slfmodel = SlfModel.getInstance();
                        slfmodel.setHighScore(event.get("gamename").textValue(), event.get("user").textValue());
                    } catch (Exception e) {
                        System.out.println("Highscore EXCEPTION: " + e);
                    }
                }

                // Wenn Runde beendet wird
                if ("endround".equals(event.get("action").textValue())) {
                    try {
                        slfmodel = SlfModel.getInstance();
                        slfmodel.endGame(gameName, event, getUserCount(event.get("gamename").textValue()));
                        slfmodel.startGame(event.get("gamename").textValue());
                        notifyRoundEnde(event.get("user").textValue(), event.get("gamename").textValue());
                    } catch (ClassNotFoundException | SQLException e) {
                        System.err.println(e);
                    }
                }

                if ("endroundrest".equals(event.get("action").textValue())) {
                    try {
                        slfmodel = SlfModel.getInstance();
                        slfmodel.checkOthers(gameName, event, getUserCount(event.get("gamename").textValue()));
                    } catch (ClassNotFoundException | SQLException e) {
                        System.err.println(e);
                    }
                    notifyBuchstabe(event.get("gamename").textValue());
                }

                System.out.println(event);
                System.out.println("");
                out.write(event);
            }
        });
        // Meldung wenn der Socket geschlossen wird! --> User bzw Socket wird
        // geschlossen/aus der Socketliste gelöscht
        in.onClose(new Callback0() {
            public void invoke() throws ClassNotFoundException, SQLException {
                String gamestatus = "";
                userQuit(userName, gameName, gamestatus);
                System.out.println("Socket ist jetzt geschlossen");
            }
        });
    }

    /**
     * Benachrichtigt alle Socket Teilnehmer über das Chat-Update.
     *
     * @param player  Erwartet den Spielernamen
     * @param text  Erwartet den zu sendenden Text
     * @param gamename  Erwartet den Spielnamen
     */
    public static void chatNotifyDTO(final String player, final String text, final String gamename) {
        for (Integer us : SOCKETLIST.keySet()) {
            if (!SOCKETLIST.get(us).getUserName().equals(player) && SOCKETLIST.get(us).getGameName().equals(gamename)) {
                ObjectNode event = Json.newObject();
                event.put("user", player);
                event.put("text", text);
                event.put("usercount", getUserCount(gamename));
                SOCKETLIST.get(us).getOut().write(event);
            }
        }
    }

    /**
     * Benachrichtigt alle Socket Teilnehmer.
     *
     * @param player  Erwartet den Spielernamen
     * @param text  Erwartet einen Text z.B. das ein neuer Spieler gejoined ist
     * @param gamename  Erwartet den Spielnamen
     * @param gameStatus    Status in dem sich das Spiel gerade befindet, gestertart oder nicht
     * @throws java.lang.ClassNotFoundException Gibt Fehlermeldung zurück sofern Probleme
     * @throws java.sql.SQLException    Wirft SQL exception
     */
    public static void notifyAllDTO(final String player, final String text, final String gamename, final String gameStatus) throws ClassNotFoundException, SQLException {
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getGameName().equals(gamename)) {
                ObjectNode event = Json.newObject();
                event.put("user", player);
                event.put("text", text);
                event.put("usercount", getUserCount(gamename));
                event.put("userlist", getUserList(gamename));
                event.put("action", "all");
                event.put("buchstabe", getBuchstabe());
                SOCKETLIST.get(us).getOut().write(event);
            }
        }

        if (gameStatus.equals("started")) {
            notifyAllDTOStarting(gamename);
        }
    }

    /**
     * Gibt allen spielern den Hinweis hey das spiel beginnt!!
     *
     * @param gamename Erwartet einen Übergebenen Spielnamen
     * @throws java.lang.ClassNotFoundException Wirft Exception
     * @throws java.sql.SQLException Wirft SQL Exception
     */
    public static void notifyAllDTOStarting(final String gamename) throws ClassNotFoundException, SQLException {
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getGameName().equals(gamename)) {
                ObjectNode event = Json.newObject();
                event.put("usercount", getUserCount(gamename));
                event.put("userlist", getUserList(gamename));
                event.put("userListPoints", getuserPoints(gamename));
                event.put("action", "starting");
                event.put("buchstabe", getBuchstabe());
                SOCKETLIST.get(us).getOut().write(event);
            }
        }
    }

    /**
     * Benachrichtigt alle Socket Teilnehmer darüber dass eine Runde beendet
     * wurde.
     *
     * @param player Erwartet den Spielernamen
     * @param gamename  Erwartet den Spielnamen
     * @throws java.lang.ClassNotFoundException Wirft exception
     * @throws java.sql.SQLException    Wirft exception
     */
    public static void notifyRoundEnde(final String player, final String gamename) throws ClassNotFoundException, SQLException {
        for (Integer us : SOCKETLIST.keySet()) {
            if (!SOCKETLIST.get(us).getUserName().equals(player) && SOCKETLIST.get(us).getGameName().equals(gamename)) {
                ObjectNode event = Json.newObject();
                event.put("user", player);
                event.put("usercount", getUserCount(gamename));
                event.put("userlist", getUserList(gamename));
                event.put("action", "endround");
                event.put("buchstabe", getBuchstabe());
                SOCKETLIST.get(us).getOut().write(event);
            }
        }
    }

    /**
     * Sendet die Punkte aller User über den Socket um die WUI zu aktualisieren.
     *
     * @param gamename  Erwartet den Spielnamen
     * @param userResults   Erwartet ein Hashset mit den Spieler Ergebnissen
     */
    public static void notifyPoints(final String gamename, final HashSet<RoundPointsDTO> userResults) {
        String g;
        String u;

        for (RoundPointsDTO result : userResults) {
            g = result.getGame();
            u = result.getUsername();

            for (Integer us : SOCKETLIST.keySet()) {
                if (SOCKETLIST.get(us).getGameName().equals(g) && SOCKETLIST.get(us).getUserName().equals(u)) {
                    ObjectNode event = Json.newObject();
                    event.put("type", "sendroundpoints");
                    event.put("action", "all");
                    event.put("user", u);
                    event.put("rundenpunkte", result.getRoundPoints());
                    event.put("stadtpunkte", result.getStadtPunkte());
                    event.put("landpunkte", result.getLandPunkte());
                    event.put("flusspunkte", result.getFlussPunkte());
                    event.put("namepunkte", result.getNamePunkte());
                    event.put("berufpunke", result.getBerufPunkte());
                    event.put("pflanzepunkte", result.getPflanzePunkte());
                    event.put("usercount", getUserCount(g));
                    event.put("userlist", getUserList(g));
                    event.put("userListPoints", getuserPoints(gamename));
                    event.put("roundpoints", result.getGamePoints());
                    SOCKETLIST.get(us).getOut().write(event);
                }
            }
        }
    }

    /**
     * Benachrichtigt alle Socket Teilnehmer über den neuen Buchstaben.
     *
     * @param gamename  Erwartet den Spielnamen
     * @throws java.lang.ClassNotFoundException Wirft Exceptions
     * @throws java.sql.SQLException Wirft Exceptions
     */
    public static void notifyBuchstabe(final String gamename) throws ClassNotFoundException, SQLException {
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getGameName().equals(gamename)) {
                ObjectNode event = Json.newObject();
                event.put("buchstabe", getBuchstabe());
                SOCKETLIST.get(us).getOut().write(event);
            }
        }
    }

    /**
     * Holt die Punkte aller Spieler und speichert diese in einem JSON-ARRAY.
     *
     * @param gameName Erwartet den Spielnamen
     * @return userlistCOUNT (JSON-ARRAY)
     */
    public static ObjectNode getuserPoints(final String gameName) {
        ObjectNode userlistCOUNT = Json.newObject();
        HashSet<RoundPointsDTO> pointlist = slfmodel.getUpdate(gameName);
        for (RoundPointsDTO result : pointlist) {
            userlistCOUNT.put(result.getUsername(), result.getGamePoints());
        }
        return userlistCOUNT;
    }

    /**
     * Gibt die Anzahl der Spieler zurück.
     *
     * @param gameName Erwartet den Spielnamen
     * @return counter (int)
     */
    public static int getUserCount(final String gameName) {
        int counter = 0;
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getGameName().equals(gameName)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gibt eine SpielerListe zurück.
     *
     * @param gameName Erwartet den Spielnamen
     * @return userlist (JSON-Array)
     */
    public static ObjectNode getUserList(final String gameName) {
        ObjectNode userlist = Json.newObject();
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getGameName().equals(gameName)) {
                userlist.put("user" + us, SOCKETLIST.get(us).getUserName());
            }
        }
        return userlist;
    }

    /**
     * Löscht einen User aus der Socket-Liste und aus der Observerliste.
     *
     * @param username Erwartet den Spielernamen der das Spielverlässt
     * @param gamename Erwartet den Spielnamen aus dem er Aussteigt
     * @param gameStatus Erwartet einen Spielstatus
     * @throws ClassNotFoundException   Wift Exceptions
     * @throws SQLException Wift Exceptions
     */
    public static void userQuit(final String username, final String gamename, final String gameStatus) throws ClassNotFoundException, SQLException {
        // vom Observer abmelden
        slfmodel = SlfModel.getInstance();
        slfmodel.unregister(gamename, username);

        // Aus der SocketListe schmeissen
        for (Integer us : SOCKETLIST.keySet()) {
            if (SOCKETLIST.get(us).getUserName().equals(username)) {
                SOCKETLIST.remove(us);
                System.out.println("GAMESTATUS " + gameStatus);
                notifyAllDTO(username, "User Left", gamename, gameStatus); // Meldung an alle dass ein User das Spielverlassen hat
            }
        }
    }

    /* -------------------------------------------------------------------------------
     ObserverPattern Funktionen
     -------------------------------------------------------------------------------
     */
    /**
     * empfängt die Updates vom Model und holt sich diese dann vom Model und
     * führt das Update aus.
     */
    @Override
    public final void update() {
        System.out.println("Update message erhalten");
        try {
            // Instanz vom Model holen
            slfmodel = SlfModel.getInstance();
            // Ergebnisse holen und über den Socket an alle verteilen
            notifyPoints(game, slfmodel.getUpdate(game));

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Holt den Aktuellen Buchstaben vom Observer.
     * @return gibt einen Buchstabem zurück (String)
     * @throws ClassNotFoundException wirft eine Exception
     * @throws SQLException wirft eine Exception
     */
    public static String getBuchstabe() throws ClassNotFoundException, SQLException {

        slfmodel = SlfModel.getInstance();
        HashSet<RoundPointsDTO> round;
        round = slfmodel.getUpdate(game);
        for (RoundPointsDTO result : round) {
            return result.getBuchstabe();
        }
        return null;
    }
}
