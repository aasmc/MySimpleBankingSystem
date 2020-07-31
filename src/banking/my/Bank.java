package banking.my;

import java.util.Scanner;

public class Bank {
    private final Manager manager;
    private final Scanner scanner;
    private Account account;

    public Bank(Manager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    public boolean acceptClient() {
        manager.acceptClient();
        int command = scanner.nextInt();
        boolean loggedIn = false;
        switch (command) {
            case 1:
                account = createAccount();
                break;
            case 2:
                loggedIn = logClientIntoAccount();
                break;
            case 0:
                exit();
                break;
            default:
                throw new RuntimeException("No such command");
        }
        return loggedIn;
    }

    private void exit() {
        manager.exit();
    }

    private boolean logClientIntoAccount() {
        if ((account = manager.logClientIntoAccount(scanner)) != null) {
            return true;
        } else {
            return false;
        }
    }

    private Account createAccount() {
        return manager.createAccount();
    }

    public boolean showAccountInfo(Scanner scanner) {
        boolean loggedOut = false;
        account.showAccountInfo();
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                final int balance = manager.getBalance(account.getCard());
                System.out.println("Balance: " + balance);
                break;
            case 2:
                addIncome();
                break;
            case 3:
                transfer();
                break;
            case 4:
                if (manager.closeAccount(account.getCard())) {
                    System.out.println("The account has been closed!");
                }
                break;

            case 5:
                account.logOut();
                loggedOut = true;
                break;

            case 0:
                account.exit();
                break;
            default:
                throw new RuntimeException("No such command");
        }
        return loggedOut;
    }

    private void transfer() {
        System.out.println("Transfer");
        System.out.println("Enter card number");
        final String cardNumber = scanner.nextLine();
        final String cardNumber1 = account.getCard().getNumber();
        if (cardNumber.equals(cardNumber1)) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }
        final Card cardByNumber = manager.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("Such a card does not exist.");
            return;
        }
        String noCheckSum = cardNumber.substring(0, cardNumber.length() - 2);
        final int sum = CardFactory.generateCheckSum(noCheckSum);
        if (sum != Integer.parseInt(cardNumber.substring(cardNumber.length() - 1))) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }
        System.out.println("Enter how much money you want to transfer:");
        final int toTransfer = scanner.nextInt();
        final int balance = manager.getBalance(account.getCard());
        if (toTransfer > balance) {
            System.out.println("Not enough money!");
            return;
        }
        final String result = manager.removeIncome(account.getCard(), toTransfer);
        if ("money sent".equals(result)) {
            if(manager.addIncome(cardByNumber, toTransfer)) {
                System.out.println("Success!");
            }
        }
    }

    private void addIncome() {
        System.out.println("Enter income:");
        final int income = scanner.nextInt();
        if (manager.addIncome(account.getCard(), income)) {
            System.out.println("Income was added");
        }
    }
}
