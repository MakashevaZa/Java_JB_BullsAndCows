package bullscows;
import com.sun.source.doctree.SeeTree;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static int turnI = 0;
    static int bulls = 0;
    static int cows = 0;
    static Scanner scanner = new Scanner(System.in);
    static String table = "";
    static int length = 0;
    static int rangeSecret = 0;

    public static void newTable(){
        int i = 0;
        while (i < 10) {
            table += i++;
        }
        i = 97;
        while (i < 123) {
            table += Character.toString(i);
            i++;
        }
    }

    public static String countGrade(String answer, String secret) {
        char[] answerChars = answer.toCharArray();
        char[] secretChars = secret.toCharArray();
        String secretStr = new String(secretChars);
        for (int i = 0; i < answer.length(); i++) {
            if (answerChars[i] == secretChars[i]) {
                bulls++;
                continue;
            }
            cows = secretStr.contains(String.valueOf(answerChars[i]))
                    ? cows + 1 : cows;
        }
        String bullReturn = switch (bulls) {
            case 0 -> "";
            case 1 -> "1 bull(s)";
            default -> String.format("%d bull(s)", bulls);
        };
        return switch (cows) {
            case 0 -> bullReturn.length() == 0 ? "None" : bullReturn;
            case 1 -> bullReturn.length() == 0 ? "1 cow(s)" : bullReturn + " and 1 cow(s)";
            default ->
                    bullReturn.length() == 0
                            ? String.format("%d cow(s)", cows)
                            : bullReturn + String.format(" and %d cow(s)", cows);
        };
    }

    public static void turn(String answer, String secret) {
        System.out.printf("Grade: %s\n", countGrade(answer, secret));
    }

    public static String getRandom() {
        String result = new String();
        Random rand = new Random();
        String input = "";
        String mes;
        int iter = 0;

        while (true) {
            System.out.println("Input the length of the secret code:");
            try {
                input = scanner.nextLine();
                length = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Error: " + input + "isn't a valid number.");
                System.exit(2);
            }
            if (length >= 37 || length < 1) {
                System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(2);
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Input the number of possible symbols in the code:");
            try {
                input = scanner.nextLine();
                rangeSecret = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Error: " + input + "isn't a valid number.");
                System.exit(2);
            }
            if (rangeSecret >= 37 || rangeSecret < 1 || rangeSecret < length) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", length, rangeSecret);
                System.exit(2);
            } else {
                break;
            }
        }
        while (iter < length) {
            String symbol = table.charAt(rand.nextInt(rangeSecret)) + "";
            if (!result.contains(symbol)) {
                result += symbol;
                iter++;
            }
        }
        if (rangeSecret <= 9) {
            mes = "(0-" + table.charAt(rangeSecret - 1) + ")";
        } else {
            mes = rangeSecret == 11 ? "(0-9, a)" : "(0-9, a-" + table.charAt(rangeSecret - 1) + ")";
        }

        System.out.println("The secret is prepared: " + "*".repeat(length) + " " + mes + "\n");
        return result;
    }


    public static void main(String[] args) {
        newTable();
        String secret = getRandom();
        System.out.println("Okay, let's start a game!");
        while (bulls != secret.length()) {
            bulls = 0;
            cows = 0;
            turnI++;
            System.out.println("Turn " + turnI + ":");
            String answer = scanner.nextLine();
            for (int i = 0; i < answer.length(); i++) {
                if (answer.length() != length || !table.contains(Character.toString(answer.charAt(i)))) {
                    System.out.println("sor: \"" + answer + "\" isn't a valid answer.");
                    System.exit(2);
                }
            }
            turn(answer, secret);
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }
}
