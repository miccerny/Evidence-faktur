/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) {
        onApplicationEvent();
        SpringApplication.run(ApplicationMain.class, args);
    }

    private static void onApplicationEvent() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "fortment")) {

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = 'invoicedatabase'");

            if (!rs.next()) {
                try {
                    conn.createStatement().execute("CREATE DATABASE \"InvoiceDatabase\";");
                    System.out.println("✅ Databáze 'InvoiceDatabase' byla vytvořena.");
                } catch (SQLException e) {
                    if (e.getMessage().contains("already exists")) {
                        System.out.println("ℹ️ Databáze už existuje (při CREATE DATABASE). Ignoruji.");
                    } else {
                        throw e; // jiná chyba, např. připojení, práva atd.
                    }
                }
            } else {
                System.out.println("ℹ️ Databáze 'InvoiceDatabase' již existuje.");
            }

        } catch (SQLException e) {
            System.err.println("⚠️ Chyba při kontrole/vytvoření databáze: " + e.getMessage());
            // ale nevyhazuj ji ven, jen zaloguj – aplikace poběží dál
        }
    }
}
