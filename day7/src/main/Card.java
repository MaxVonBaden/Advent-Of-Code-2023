package main;

public enum Card {
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    T('T'),
    J('J'),
    Q('Q'),
    K('K'),
    A('A');


    private final char character;

    Card(char character) {
        this.character = character;
    }

    public static Card byCharacter(char character) {
        for (Card card : values()) {
            if (card.character == character) {
                return card;
            }
        }
        throw new IllegalArgumentException();
    }

    public char getCharacter() {
        return this.character;
    }
}
