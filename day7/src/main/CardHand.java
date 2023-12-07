package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CardHand implements Comparable<CardHand> {
    private enum HandType {
        HIGH_CARD(HandType::allCardsAreDistinct),
        ONE_PAIR(cards -> pairCount(cardTypeToAmountMap(cards)) == 1),
        TWO_PAIR(cards -> pairCount(cardTypeToAmountMap(cards)) == 2),
        THREE_OF_A_KIND(cards ->
                cardTypeToAmountMap(cards).entrySet().stream()
                        .map(entry -> entry.getValue() == 3)
                        .reduce(Boolean::logicalOr).get()
        ),
        FULL_HOUSE(cards -> cardTypeToAmountMap(cards).entrySet().stream()
                .filter(entry -> entry.getValue() != 0).toList().size() == 2
                && cardTypeToAmountMap(cards).values().stream()
                .map(cardCount -> cardCount == 3)
                .reduce(Boolean::logicalOr).get()
        ),
        FOUR_OF_A_KIND(cards ->
                cardTypeToAmountMap(cards).values().stream()
                        .map(cardCount -> cardCount == 4)
                        .reduce(Boolean::logicalOr).get()
        ),
        FIVE_OF_A_KIND(HandType::allCardsAreTheSame);

        @FunctionalInterface
        private interface TypeIdentifier {
            boolean isOfType(List<Card> cards);
        }

        private final TypeIdentifier identifier;

        HandType(TypeIdentifier identifier) {
            this.identifier = identifier;
        }

        private static int pairCount(Map<Card, Integer> cardAmounts) {
            return cardAmounts.entrySet().stream()
                    .filter(entry -> entry.getValue() == 2)
                    .toList()
                    .size();
        }

        private static boolean allCardsAreDistinct(List<Card> cards) {
            return (new HashSet<>(cards)).size() == cards.size();
        }

        private static boolean allCardsAreTheSame(List<Card> cards) {
            return (new HashSet<>(cards)).size() == 1;
        }

        public static HandType byCardList(List<Card> cards) {
            for (int i = HandType.values().length - 1; i >= 0; i--) {
                if (values()[i].identifier.isOfType(cards)) {
                    return values()[i];
                }
            }
            throw new IllegalArgumentException("Something went wrong with your hand.");
        }
    }

    private static final int HAND_SIZE = 5;

    private final List<Card> cards;
    private final HandType type;

    public CardHand(List<Card> cards) {
        if (cards.size() != HAND_SIZE) {
            throw new IllegalArgumentException("Hands must be of size %d".formatted(HAND_SIZE));
        }
        this.cards = new ArrayList<>(cards);
        this.type = HandType.byCardList(cards);
        if (this.type == null) {
            throw new IllegalArgumentException("Something went wrong with your set of cards.");
        }
    }

    @Override
    public int compareTo(CardHand o) {
        if (this.type != o.type) {
            return this.type.compareTo(o.type);
        }

        for (int i = 0; i < HAND_SIZE; i++) {
            if (this.cards.get(i) != o.cards.get(i)) {
                return this.cards.get(i).compareTo(o.cards.get(i));
            }
        }
        return 0;
    }

    private static Map<Card, Integer> cardTypeToAmountMap(List<Card> cards) {
        final Map<Card, Integer> cardAmounts = new HashMap<>();
        Arrays.stream(Card.values()).forEach(card -> cardAmounts.put(card, 0));
        cards.forEach(card -> cardAmounts.put(card, cardAmounts.get(card) + 1));
        return cardAmounts;
    }

    public String toString() {
        final StringBuilder result =  new StringBuilder();
        this.cards.forEach(card -> result.append(card.getCharacter()));
        result.append(", %s".formatted(this.type.toString()));
        return result.toString();
    }

    private static HandType[] reverse(HandType[] input) {
        final HandType[] result = new HandType[HandType.values().length];
        for (int i = HandType.values().length - 1; i >= 0; i--) {
            result[HandType.values().length - 1 - i] = HandType.values()[i];
        }

        return result;
    }
}
