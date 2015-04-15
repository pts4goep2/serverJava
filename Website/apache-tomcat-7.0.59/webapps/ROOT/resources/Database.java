
/**
 *
 * @author Indra
 */

package resources;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class Database {

    private Connection conn;

    public Database() {

    }

    public void initConnection() throws SQLException {

        System.setProperty("jdbc.drivers", "jdbc.mysql.Driver");

        conn = DriverManager.getConnection("jdbc:mysql:coordinatentestdb", "root", "");
    }

    public void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Incident haalCoordinatenOp() throws SQLException {

        Incident incident = null;

            Statement incidentOphaal = conn.createStatement();
            ResultSet ongeluk = incidentOphaal.executeQuery("SELECT * FROM coordinaten;");

            while (ongeluk.next()) {
                double lon = ongeluk.getDouble("Longitude");
                double lat = ongeluk.getDouble("Latitude");
                String naam = ongeluk.getString("naam");
                String beschrijving = ongeluk.getString("Beschrijving");

                incident = new Incident(lat, lon, naam, beschrijving);

            }

        return incident;
    }
}
