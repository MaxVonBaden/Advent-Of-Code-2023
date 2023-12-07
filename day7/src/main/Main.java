package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("DuplicatedCode")
public class Main {
    private record HandAndBid(CardHand hand, int bid) implements Comparable<HandAndBid> {
        @Override
        public int compareTo(HandAndBid o) {
            return hand.compareTo(o.hand);
        }

        @Override
        public String toString() {
            return "(" + hand.toString() + ", " + bid + ")";
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter file path: ");
        final Scanner commandLineScanner = new Scanner(System.in);
        final File file = new File(commandLineScanner.nextLine());
        commandLineScanner.close();
        final Scanner fileScanner;

        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException exception) {
            System.err.println("Invalid file path.");
            return;
        }

        final List<HandAndBid> handAndBids = new LinkedList<>();

        while (fileScanner.hasNext()) {
            String scannerResult = fileScanner.nextLine();
            if (!scannerResult.isEmpty()) {
                handAndBids.add(parse(scannerResult));
            }
        }

        fileScanner.close();
        Collections.sort(handAndBids);

        int result = 0;

        for (int i = 1; i <= handAndBids.size(); i++) {
            result += i * handAndBids.get(i - 1).bid;
        }

        System.out.println(result);
    }
    private static HandAndBid parse(String line) {
        final String[] result = line.split(" ");
        return new HandAndBid(new CardHand(stringToCards(result[0])), Integer.parseInt(result[1]));
    }

    private static List<Card> stringToCards(String handString) {
        final List<Card> result = new LinkedList<>();
        for (char character : handString.toCharArray()) {
            result.add(Card.byCharacter(character));
        }
        return result;
    }
}
