package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class MainDay7 {
    private record HandAndBid(CardHand hand, int bid) implements Comparable<HandAndBid> {
        @Override
        public int compareTo(HandAndBid o) {
            return hand.compareTo(o.hand);
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter file path: ");
        final Scanner commandLineScanner = new Scanner(System.in);
        final File file = new File(commandLineScanner.nextLine());
        final List<String> lines;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException exception) {
            System.err.println("Invalid file path.");
            return;
        }

        final List<HandAndBid> handAndBids;

        System.out.print("Chose part: ");
        switch (commandLineScanner.nextLine()) {
            case "1" -> handAndBids = lines.stream()
                    .filter(line -> !line.isBlank())
                    .map(MainDay7::parse)
                    .sorted()
                    .toList();
            case "2" -> handAndBids = lines.stream()
                    .filter(line -> !line.isBlank())
                    .map(line -> line.replaceAll(
                            Character.toString(Card.J.getCharacter()),
                            Character.toString(Card.JOKER.getCharacter())))
                    .map(MainDay7::parse)
                    .sorted()
                    .toList();
            default -> {
                System.err.println("Chose part 1 or 2.");
                return;
            }
        }
        commandLineScanner.close();

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
