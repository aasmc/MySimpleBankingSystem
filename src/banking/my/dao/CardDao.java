package banking.my.dao;

import banking.my.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDao {

    private static final String CARD_TABLE_NAME = "card";
    public static final String INSERT_INTO_CARD_SQL = "INSERT INTO " + CARD_TABLE_NAME + " (number, pin, balance) VALUES(?, ?, ?);";
    public static final String GET_ALL_SQL = "SELECT * FROM " + CARD_TABLE_NAME;
    public static final String GET_BALANCE_SQL = "SELECT balance FROM " + CARD_TABLE_NAME + " WHERE number = ?";
    public static final String ADD_INCOME_SQL = "UPDATE " + CARD_TABLE_NAME + " SET balance = balance + ? WHERE number = ?";
    public static final String DELETE_CARD_SQL = "DELETE FROM " + CARD_TABLE_NAME + " WHERE number = ?";
    public static final String DECREASE_BALANCE_SQL = "UPDATE " + CARD_TABLE_NAME + " SET balance = balance - ? WHERE number = ?";
    public static final String GET_CARD_BY_NUMBER_SQL = "SELECT * FROM " + CARD_TABLE_NAME + " WHERE number = ?";


    private final Connection connection;

    public CardDao(Connection connection) {
        this.connection = connection;
    }

    public Card getCardByNumber(String number) {
        try(final PreparedStatement stmt = connection.prepareStatement(GET_CARD_BY_NUMBER_SQL);) {
            stmt.setString(1, number);
            try (final ResultSet resultSet = stmt.executeQuery();) {
                if (resultSet.next()) {
                    return fillCard(resultSet);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Card fillCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getInt("id"));
        card.setNumber(resultSet.getString("number"));
        card.setBalance(resultSet.getInt("balance"));
        card.setPin(resultSet.getString("pin"));
        return card;
    }

    public int getBalance(Card card) {
        int balance  = 0;
        try(final PreparedStatement stmt = connection.prepareStatement(GET_BALANCE_SQL);) {
            stmt.setString(1, card.getNumber());

            try(final ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    balance = rs.getInt("balance");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return balance;
    }

    public boolean addIncome(Card card, int income) {
        boolean success = false;
        try(final PreparedStatement stmt = connection.prepareStatement(ADD_INCOME_SQL);) {
            stmt.setInt(1, income);
            stmt.setString(2, card.getNumber());
            stmt.executeUpdate();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return success;
    }

    public boolean closeAccount(Card card) {
        boolean success = false;
        try(final PreparedStatement stmt = connection.prepareStatement(DELETE_CARD_SQL);) {
            stmt.setString(1, String.valueOf(card.getNumber()));
            stmt.executeUpdate();
            success = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return success;
    }

    public void addCardToDb(Card card) {
        try(PreparedStatement stmt = connection.prepareStatement(INSERT_INTO_CARD_SQL);){
            stmt.setString(1, String.valueOf(card.getNumber()));
            stmt.setString(2, card.getPin());
            stmt.setInt(3, card.getBalance());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String removeIncome(Card cardToSend, int amount) {
        String result = "failure";
        try(final PreparedStatement stmt =  connection.prepareStatement(DECREASE_BALANCE_SQL)) {
            stmt.setInt(1, amount);
            stmt.setString(2, String.valueOf(cardToSend.getNumber()));
            stmt.executeUpdate();
            result = "money sent";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public List<Card> getAllCards() {
        List<Card> all = new ArrayList<>();
        try (final Statement stmt = connection.createStatement();) {
            try (final ResultSet rs = stmt.executeQuery(GET_ALL_SQL);) {
                while (rs.next()) {
                    Card card = fillCard(rs);
                    all.add(card);
                }
            }
            return all;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
