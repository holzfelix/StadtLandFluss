package models;

import dto.ResultDTO;
import dto.RoundPointsDTO;
import java.sql.SQLException;
import java.util.HashSet;

/**
 *
 * @author Felix
 */
public final class PlayLogic {
    /**
     * Instanz des Rundenmanagers.
     */
    private final RoundManager rManager = new RoundManager();
    /**
     * Instanz der Datenbankverbindung.
     */
    private final MySQLLink mySQL;
    /**
     * Hashset für die Punktevergabe der aktuellen Runde.
     */
    private HashSet<RoundPointsDTO> roundPointList;
    /**
     * Konstruktor, erzeugt eine Instanz zur Datenbank-Verbindung.
     * @param gameName Spielname
     * @throws ClassNotFoundException wirft Exception
     * @throws SQLException wirft Exception
     */
    public PlayLogic(final String gameName) throws ClassNotFoundException, SQLException {
        game(gameName);
        mySQL = MySQLLink.getInstance();
    }
    /**
     * Startet eine neue Runde.
     * @param gameName Name des Spiels für welches eine neue Runde gestartet werden soll.
     */
    public void game(final String gameName) {
        rManager.startNewRound(gameName);
    }

    /**
     * Beendet eine Runde.
     * @param gameName Spielname für welches die Runde beendet werden soll.
     * @param ergebnisse Ergebniss DTO.
     * @return Gibt die Runden Punkte Liste zurück.
     * @throws Exception wirft Exception
     */
    public HashSet stopGame(final String gameName, final HashSet<ResultDTO> ergebnisse) throws Exception {
        System.out.println("------- Runde wurde von einem User beendet");
        checkInputs(ergebnisse);
        rManager.stopRound(gameName);
        countUserPoints(ergebnisse);
        return roundPointList;
    }

    /**
     * Holt einen neuen Buchstaben.
     * @param gameName Spielname für den ein neuer Buchstabe erzeugt werden soll
     * @return Gibt den buchstaben zurück.
     */
    public String getBuchstabe(final String gameName) {
        return rManager.getBuchstabe(gameName);
    }

    /**
     * Prüft ob die User-Eingaben Korrekt sind.
     * @param ergebnisse DTO mit den Nutzereingaben.
     * @throws Exception wirft Exception
     */
    public void checkInputs(final HashSet<ResultDTO> ergebnisse) throws Exception {
        String username;
        String game;
        String stadt;
        String land;
        String fluss;
        String name;
        String beruf;
        String pflanze;
        String buchstabe;

        for (ResultDTO ergebnis : ergebnisse) {
            username = ergebnis.getUsername();
            game = ergebnis.getGame();
            stadt = ergebnis.getStadt();
            land = ergebnis.getLand();
            fluss = ergebnis.getFluss();
            name = ergebnis.getName();
            beruf = ergebnis.getBeruf();
            pflanze = ergebnis.getPflanze();
            buchstabe = ergebnis.getBuchstabe();

            System.out.println("USER: " + username);

            // Prüft die Stadt in der DB
            if (checkFirstLetter(stadt, buchstabe)) {
                if (mySQL.readStadt(stadt)) {
                    ergebnis.setStadtPunkte(countInputs(ergebnisse, stadt, "stadt"));
                    System.out.println("Stadt: Ja " + stadt + " gibt es");
                } else {
                    ergebnis.setStadtPunkte(0);
                    System.out.println("Stadt: Nein " + stadt + " gibt es nicht");
                }
            } else {
                ergebnis.setStadtPunkte(0);
                errorLetter("stadt");
            }

            // Prüft das Land in der DB
            if (checkFirstLetter(land, buchstabe)) {
                if (mySQL.readLand(land)) {
                    ergebnis.setLandPunkte(countInputs(ergebnisse, land, "land"));
                    System.out.println("Land: Ja " + land + " gibt es");
                } else {
                    ergebnis.setLandPunkte(0);
                    System.out.println("Land: Nein " + land + " gibt es nicht");
                }
            } else {
                ergebnis.setLandPunkte(0);
                errorLetter("land");
            }

            // Prüft den Namen in der DB
            if (checkFirstLetter(name, buchstabe)) {
                if (mySQL.readName(name)) {
                    ergebnis.setNamePunkte(countInputs(ergebnisse, name, "name"));
                    System.out.println("Name: Ja " + name + " gibt es");
                } else {
                    ergebnis.setNamePunkte(0);
                    System.out.println("Name: Nein " + name + " gibt es nicht");
                }
            } else {
                ergebnis.setNamePunkte(0);
                errorLetter("name");
            }

            // Prüft den Fluss in der DB
            if (checkFirstLetter(fluss, buchstabe)) {
                if (mySQL.readFluss(fluss)) {
                    ergebnis.setFlussPunkte(countInputs(ergebnisse, fluss, "fluss"));
                    System.out.println("Fluss Ja " + fluss + " gibt es");
                } else {
                    ergebnis.setFlussPunkte(0);
                    System.out.println("Fluss Nein " + fluss + " gibt es nicht");
                }
            } else {
                ergebnis.setFlussPunkte(0);
                errorLetter("fluss");
            }

            // Prüft den Beruf
            if (checkFirstLetter(beruf, buchstabe)) {
                ergebnis.setBerufPunkte(countInputs(ergebnisse, beruf, "beruf"));
            } else {
                ergebnis.setBerufPunkte(0);
                errorLetter("beruf");
            }

            // Prüft die Pflanzen
            if (checkFirstLetter(pflanze, buchstabe)) {
                ergebnis.setPflanzePunkte(countInputs(ergebnisse, pflanze, "pflanze"));
            } else {
                ergebnis.setPflanzePunkte(0);
                errorLetter("pflanze");
            }
            // Abstand zwischen dem Nächsten Spieler
            System.out.println("-----------------------------------");
        }
    }

    /**
     * Vergleicht den ersten Buchstaben und gibt True or false zurück.
     * @param checkString String der Geprüft werden soll
     * @param letter Buchstabe mit dem Verglichen werden soll
     * @return gibt einen boolean wert zurück.
     */
    private boolean checkFirstLetter(final String checkString, final String letter) {
        if (checkString == null || "".equals(checkString) || checkString.length() == 1) {
            return false;
        }
        return letter.toUpperCase().equals(checkString.substring(0, 1).toUpperCase()) || letter.toLowerCase().equals(checkString.substring(0, 1).toUpperCase());
    }

    /**
     * Errorausgabe in der Konsole wenn die Eingabe leer war oder wenn ein falscher Buchstabe gewählt wurde.
     * @param typ String, welche Eingabe geprüft wurde.
     */
    private void errorLetter(final String typ) {
        System.out.println(typ + ": Falscher Buchstabe oder gar kein Buchstabe");
    }

    /**
     * Zählt wie oft eine Eingabe vorkommt und gibt entsprechende Punkte zurück. (Punkte vergabe in den regeln nachschlagen)
     * @param ergebnisse DTO mit den Nutzereingaben.
     * @param checkString String mit dem verglichen werden soll
     * @param typ Eingabe Typ
     * @return gibt einen Int Wert zurück. Anzahl der Vorkommnisse.
     */
    private int countInputs(final HashSet<ResultDTO> ergebnisse, final String checkString, final String typ) {
        final int keinePunkte = 0;
        final int fuenf = 5;
        final int zehn = 10;
        final int zwanzig = 20;
        int count = 0;
        int countleer = 0;
        for (ResultDTO check : ergebnisse) {
            if (typ.equals("stadt")) {
                if (check.getStadt().equals(checkString)) {
                    count++;
                }
                if (check.getStadt().equals("")) {
                    countleer++;
                }
            }
            if (typ.equals("land")) {
                if (check.getLand().equals(checkString)) {
                    count++;
                }
                if (check.getLand().equals("")) {
                    countleer++;
                }
            }
            if (typ.equals("fluss")) {
                if (check.getFluss().equals(checkString)) {
                    count++;
                }
                if (check.getFluss().equals("")) {
                    countleer++;
                }
            }
            if (typ.equals("name")) {
                if (check.getName().equals(checkString)) {
                    count++;
                }
                if (check.getName().equals("")) {
                    countleer++;
                }
            }
            if (typ.equals("beruf")) {
                if (check.getBeruf().equals(checkString)) {
                    count++;
                }
                if (check.getBeruf().equals("")) {
                    countleer++;
                }
            }
            if (typ.equals("pflanze")) {
                if (check.getPflanze().equals(checkString)) {
                    count++;
                }
                if (check.getPflanze().equals("")) {
                    countleer++;
                }
            }
        }

        if (countleer == ergebnisse.size() - 1) {
            // Nur eine Lösung andere User haben kein ergebnis.
            System.out.println("Es wurden 20 Punkte vergeben");
            return zwanzig;
        }
        if (count > 1) {
            // Ergebnis kam mehr wie einmal vor
            System.out.println("Es wurden 5 Punkte vergeben");
            return fuenf;
        }
        if (count == 1) {
            // Ergebnis kam genau einmal vor
            System.out.println("Es wurden 10 Punkte vergeben");
            return zehn;
        }
        return keinePunkte;
    }

    /**
     * Erzeugt eine Liste mit den Rundenergebnissen.
     * @param ergebnisse Liste mit Benutzereingaben.
     */
    private void countUserPoints(final HashSet<ResultDTO> ergebnisse) {
        roundPointList = null;
        roundPointList = new HashSet<>();
        String username;
        String gamename;
        int stadtpoints;
        int landpoints;
        int flusspoints;
        int namepoints;
        int berufpoints;
        int pflanzenpoints;
        int roundPoints;

        for (ResultDTO pointstoget : ergebnisse) {
            username = pointstoget.getUsername();
            gamename = pointstoget.getGame();
            stadtpoints = pointstoget.getStadtPunkte();
            landpoints = pointstoget.getLandPunkte();
            flusspoints = pointstoget.getFlussPunkte();
            namepoints = pointstoget.getNamePunkte();
            berufpoints = pointstoget.getBerufPunkte();
            pflanzenpoints = pointstoget.getPflanzePunkte();
            roundPoints = stadtpoints + landpoints + flusspoints + namepoints + berufpoints + pflanzenpoints;
            RoundPointsDTO rpd = new RoundPointsDTO();
            rpd.setGame(gamename);
            rpd.setUsername(username);
            rpd.setRoundPoints(roundPoints);
            rpd.setStadtPunkte(stadtpoints);
            rpd.setLandPunkte(landpoints);
            rpd.setFlussPunkte(flusspoints);
            rpd.setNamePunkte(namepoints);
            rpd.setBerufPunkte(berufpoints);
            rpd.setPflanzePunkte(pflanzenpoints);

            roundPointList.add(rpd);
        }
    }
}