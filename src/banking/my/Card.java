package banking.my;

import java.util.Objects;

public class Card {
    private int id = -1;
    private String number;
    private String pin;
    private int balance = 0;

    public Card() {
    }

    public Card(String number, String pin) {
        this.number = number;
        this.pin = pin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number.equals(card.number) &&
                pin.equals(card.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, pin);
    }
}
