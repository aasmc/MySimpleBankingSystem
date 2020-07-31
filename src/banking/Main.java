package banking;

import banking.my.Bank;
import banking.my.Manager;
import banking.my.dao.CardDao;
import banking.my.database.SQLiteDatabaseHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbName = "";
        if ("-fileName".equals(args[0])) {
            dbName = args[1];
        }

        String url = dbName;
        Connection connection = null;
        SQLiteDatabaseHelper helper = new SQLiteDatabaseHelper();
        try {
            connection = helper.createConnection(url);
            helper.createCardsTable(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        CardDao cardDao = new CardDao(connection);
        Manager manager = new Manager(cardDao);
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank(manager, scanner);
        while (true) {
            boolean loggedIn = bank.acceptClient();
            if (loggedIn) {
                boolean loggedOut = false;
                while (!loggedOut) {
                    loggedOut = bank.showAccountInfo(scanner);
                }
            }
        }
    }
}
