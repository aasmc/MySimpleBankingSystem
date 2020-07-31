package banking.my;

import banking.my.dao.CardDao;

import java.util.List;
import java.util.Scanner;

public class Manager {

    private CardDao cardDao;

    public Manager(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public void acceptClient() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public Account createAccount() {
        Card card = CardFactory.createCard();
        Account account = new Account(card);
        cardDao.addCardToDb(card);
        System.out.println();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(card.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(card.getPin());
        System.out.println();
        return account;
    }

    public Account logClientIntoAccount(Scanner scanner) {
        System.out.println("Enter your card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();
        if(checkCardCorrect(cardNumber, pin)) {
            System.out.println("You have successfully logged in!");
            System.out.println();
            return new Account(new Card(cardNumber, pin));
        } else {
            System.out.println("Wrong card number or PIN!");
            System.out.println();
            return null;
        }
    }

    private boolean checkCardCorrect(String cardNumber, String pin) {
        boolean cardCorrect = false;
        final List<Card> allCards = cardDao.getAllCards();
        long count = allCards
                .stream()
                .filter(card -> card.getNumber().equals(cardNumber) && card.getPin().equals(pin))
                .count();
        if (count != 0) {
            cardCorrect = true;
        }
        return cardCorrect;
    }

    public void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    public boolean closeAccount(Card card) {
        return cardDao.closeAccount(card);
    }

    public Card getCardByNumber(String cardNumber) {
        return cardDao.getCardByNumber(cardNumber);
    }

    public int getBalance(Card card) {
        return cardDao.getBalance(card);
    }

    public String removeIncome(Card card, int toTransfer) {
       return cardDao.removeIncome(card, toTransfer);
    }

    public boolean addIncome(Card cardByNumber, int toTransfer) {
        return cardDao.addIncome(cardByNumber, toTransfer);
    }
}
