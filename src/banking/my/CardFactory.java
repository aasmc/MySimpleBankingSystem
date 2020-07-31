package banking.my;

import java.util.Random;

public class CardFactory {

    public static int counter = 0;
    public static final String bankIdentifier = "400000";

    public static Card createCard() {
        Card card = new Card();
        card.setId(++counter);
        card.setBalance(0);
        card.setPin(generateRandomPin());
        String number = generateNumberByLuhnAlgorithm();
        card.setNumber(number);
        return card;
    }

    public static String generateNumberByLuhnAlgorithm() {
        Random generator = new Random();
        String customerAccountNumber = String.format("%09d", generator.nextInt(1000000001));
        String noCheckSum = bankIdentifier + customerAccountNumber;
        int checkDigit = generateCheckSum(noCheckSum);
        String number = noCheckSum + checkDigit;
        return number;
    }

    public static int generateCheckSum(String noCheckSum) {
        String afterStepOne = stepOneLuhnAlgorithm(noCheckSum);
        String afterStepTwo = stepTwoLuhnAlgorithm(afterStepOne);
        int sum = 0;
        for (int i = 0; i < afterStepTwo.length(); i++) {
            sum += Integer.parseInt(String.valueOf(afterStepTwo.charAt(i)));
        }
        if (sum % 10 == 0) {
            return 0;
        } else {
            return 10 - (sum % 10);
        }
    }

    private static String stepTwoLuhnAlgorithm(String afterStepOne) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < afterStepOne.length(); i++) {
            int number = Integer.parseInt(String.valueOf(afterStepOne.charAt(i)));
            if (number > 9) {
                number -= 9;
            }
            builder.append(number);
        }
        return builder.toString();    }

    private static String stepOneLuhnAlgorithm(String noCheckSum) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < noCheckSum.length(); i++) {
            //multiply every odd number by 2
            // need to multiply every even number because index starts with 0
            if (i % 2 == 0) {
                char c = noCheckSum.charAt(i);
                String strDigit = String.valueOf(c);
                int doubled = Integer.parseInt(strDigit) * 2;
                builder.append(doubled);
            } else {
                builder.append(noCheckSum.charAt(i));
            }
        }
        return builder.toString();
    }

    private static String generateRandomPin() {
        Random generator = new Random();
        return String.format("%04d", generator.nextInt(1001));
    }
}
