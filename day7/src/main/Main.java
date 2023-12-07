package main;

import java.util.LinkedList;
import java.util.List;

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
