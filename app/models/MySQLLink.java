package models;

import dto.HighScoreDTO;
import dto.RoundPointsDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felix
 */
public final class MySQLLink {
    /**
     * Speichert die Instanz der Klasse.
     */
    private static MySQLLink instance;
    /**
     * Speichert die Verbindung zur Datenbank.
     */
    private Connection connect = null;
    /**
     * Speichert die Rückgabe des Querys.
     */
    private ResultSet resultSet = null;
    /**
     * Speichert die IP des Datenbank-Servers.
     */
    private final String server = "85.214.219.96";
    /**
     * Speichert den Datenbank User.
     */
    private final String user = "game";
    /**
     * Speichert die Datenbanknamen.
     */
    private final String dbname = "stadtlandfluss";
    /**
     * Speichert das Passwort für die Datenbank.
     */
    private final String pw = "StadtLand_FLUSS_!!445678";

    /**
     * Private Konstruktor wird über das Singleton Pattern instanziiert.
     *
     * @throws ClassNotFoundException wirft Exception
     * @throws SQLException wirft Exception
     */
    private MySQLLink() throws ClassNotFoundException, SQLException {
        // MySQL-DB Treiber laden
        Class.forName("com.mysql.jdbc.Driver");
        // Verbindung herstellen
        connect = DriverManager.getConnection("jdbc:mysql://" + server + "/" + dbname + "?user=" + user + "&password=" + pw);
        System.out.println("DB Verbindung steht");
    }

    /**
     * Singleton Pattern aufruf, gibt die Instanz der Klasse zurück. Es darf nämlich immer nur eine Instanz der Klasse vorhanden sein.
     *
     * @return gibt die Instanz zurück
     * @throws ClassNotFoundException wirft Exception
     * @throws SQLException wirft Exception
     */
    public static MySQLLink getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new MySQLLink();
        }
        return instance;
    }

    /**
     * Prüft ob es die eingegabene Stadt in DE / CH / AT gibt.
     *
     * @param stadt Stadt die geprüft werden soll
     * @return gibt einen Boolean wert zurück
     * @throws Exception wirft Exception
     */
    public Boolean readStadt(final String stadt) throws Exception {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM geodb_textdata WHERE text_val=?");
            ps.setString(1, stadt);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return stadt.toUpperCase().equals(resultSet.getString("text_val")) || stadt.equals(resultSet.getString("text_val"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    /**
     * Prüft ob es die eingegabene Stadt in DE / CH / AT gibt.
     *
     * @param land Land das geprüft werden soll.
     * @return gibt einen Boolean wert zurück.
     * @throws Exception wirft Exception
     */
    public Boolean readLand(final String land) throws Exception {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM countries WHERE de=?");
            ps.setString(1, land);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return land.toUpperCase().equals(resultSet.getString("de")) || land.equals(resultSet.getString("de"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    /**
     * Prüft ob es den eingegebenen Namen gibt.
     *
     * @param name Name der geprüft werden soll
     * @return gibt einen Boolean wert zurück
     * @throws Exception wirft Exception
     */
    public Boolean readName(final String name) throws Exception {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM namen WHERE name=?");
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return name.equals(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    /**
     * Prüft ob es den eingegebenen Fluss gibt.
     *
     * @param name Name des Flusses der geprüft werden soll
     * @return gibt einen boolean wert zurück
     * @throws Exception wirft Exception
     */
    public Boolean readFluss(final String name) throws Exception {
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT * FROM fluesse WHERE name=?");
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return name.equals(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return false;
    }

    /**
     * Fügt einen neuen HighScore in die Datenbank ein.
     *
     * @param dto Dto mit den Daten des Eintrags.
     * @throws Exception wirft Exception
     */
    public void writeHighScore(final RoundPointsDTO dto) throws Exception {
        try {
            PreparedStatement ps = connect.prepareStatement("INSERT INTO highscore (name, points, datum) VALUES (?,?, CURDATE());");
            ps.setString(1, dto.getUsername());
            ps.setInt(2, dto.getGamePoints());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Holt die HighScore Liste. Die besten 10 werden angezeigt.
     *
     * @return gibt eine Liste zurück. (ArrayList)
     * @throws Exception wirft Exception
     */
    public List<HighScoreDTO> getHighScore() throws Exception {
        final int eins = 1;
        final int zwei = 2;
        final int drei = 3;
        List<HighScoreDTO> scores = new ArrayList<>();
        try {
            PreparedStatement ps = connect.prepareStatement("SELECT name, points, datum FROM highscore ORDER BY points desc LIMIT 10");
            resultSet = ps.executeQuery();

            int position = 0;
            while (resultSet.next()) {
                position++;
                HighScoreDTO dto = new HighScoreDTO();
                dto.setPosition(position);
                dto.setUser(resultSet.getString(eins));
                dto.setPoints(resultSet.getInt(zwei));
                dto.setDate(resultSet.getDate(drei));
                scores.add(dto);
            }

            return scores;
        } catch (SQLException e) {
            throw e;
        }
    }
}
