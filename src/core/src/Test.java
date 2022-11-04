import ConfigLoader.*;
import DBCore.DBAPI;
import Utils.Logger;
import Utils.Utils;

import java.util.Scanner;

/**
 * Class containing test cases.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DBAPI api = new DBAPI();

        Logger log = new Logger("system.log");

        log.log("Test message!", Logger.MessageType.LOG);
        log.log("Test warning!", Logger.MessageType.WARNING);
        log.log("Test error!", Logger.MessageType.ERROR);

        System.out.println("Is connection established: " + api.isConnectionEstablished());

        Scanner scan = new Scanner(System.in);
        System.out.println("Username: ");
        String username = scan.nextLine();
        System.out.println("Password: ");
        String password = scan.nextLine();

        api.login(username, password);

        if (args[0].equals("--tests-run=true")) {
            configLoaderTests();
            DBAPITests();
        }

        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }

        try {
            System.out.println(Utils.generateParcelID(api, "GER"));
            System.out.println(Utils.generateUsername(api, "", ""));
            System.out.println(Utils.generateUsername(api, "Majmun", "Bogoslavijević"));
        } catch (Exception e) {

        }

        api.logout();
    }

    /**
     * Tests for the configuration loader.
     */
    static void configLoaderTests() {
        ConfigLoader cfgldr = new ConfigLoader();
        cfgldr.load();
        DBData data = cfgldr.fetchData();

        System.out.println("ConfigLoader tests:");

        if (data == null) {
            System.out.println("Test 1.1 not passed.");
            System.out.println("Data is null.");
        }
        else {
            System.out.println("Test 1.1 passed.");
        }

        if (!data.toString().equals("[Name = test_tpo, ip = 127.0.0.1, port = 3306]")) {
            System.out.println("Test 1.2 not passed.");
            System.out.println("Expected: " + "[Name = test_tpo, ip = 127.0.0.1, port = 3306]" + " || Result: " + data);
        }
        else {
            System.out.println("Test 1.2 passed.");
        }
    }

    /**
     * Test for the database api.
     */
    static void DBAPITests() {
        DBAPI dbapi = new DBAPI();

        System.out.println("DBAPI tests:");

        dbapi.login("tpo-tester", "password");

        if (!dbapi.isConnectionEstablished()) {
            System.out.println("Test 2.1 not passed.");
            System.out.println("Connection is not established.");
        }
        else {
            System.out.println("Test 2.1 passed.");
        }

        dbapi.logout();

        if (dbapi.isConnectionEstablished()) {
            System.out.println("Test 2.2 not passed.");
            System.out.println("Connection is established.");
        }
        else {
            System.out.println("Test 2.2 passed.");
        }
    }
}
