package banking.my;

public class Account {
    private Card card;

    public Account(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void showAccountInfo() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    public void checkBalance() {
        System.out.println("Balance: " + card.getBalance());
        System.out.println();
    }

    public boolean logOut() {
        System.out.println("You have successfully logged out!");
        System.out.println();
        return true;
    }

    public void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }
}
