package banking.my.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseHelper {

    private SQLiteDatabaseHelper INSTANCE;

    public SQLiteDatabaseHelper() {
    }

    private static final String CARD_TABLE_NAME = "card";
    private static final String CREATE_CARD_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + CARD_TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, pin TEXT, balance INTEGER DEFAULT 0);";


    public Connection createConnection(String url) throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + url);
//        SQLiteDataSource dataSource = new SQLiteDataSource();
//        dataSource.setUrl(url);
//        return dataSource.getConnection();
    }

    public void createCardsTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_CARD_TABLE_SQL);
    }
}
