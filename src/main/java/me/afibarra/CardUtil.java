package me.afibarra;

// Why am I not using just the "ordinal()" method?
// Answwer: See Effective Java, 3rd Edition, Joshua Bloch, Item 35, Page 168
public interface CardUtil {
    enum SUIT {
        SPADE(0),
        HEART(1),
        CLUB(2),
        WINTER(3);

        private final int order;

        SUIT(int order) {
            this.order = order;
        }

        public int order() {
            return order;
        }
    }

    enum VALUE {
        TWO(0),
        THREE(1),
        FOUR(2),
        FIVE(3),
        SIX(4),
        SEVEN(5),
        EIGHT(6),
        NINE(7),
        TEN(8),
        JACK(9),
        QUEEN(10),
        KING(11),
        ACE(12);

        private final int order;

        VALUE(int order) {
            this.order = order;
        }

        public int order() {
            return order;
        }
    }

    // To compare a hand's ordinal value vs another hand's ordinal
    enum HAND_TYPE {
        HIGH_CARD(0),
        ONE_PAIR(1),
        TWO_PAIRS(2),
        THREE_OF_A_KIND(3),
        STRAIGHT(4),
        FLUSH(5),
        FULL_HOUSE(6),
        FOUR_OF_A_KIND(7),
        STRAIGHT_FLUSH(8);

        private final int weight;

        HAND_TYPE(int weight) {
            this.weight = weight;
        }

        public int weight() {
            return weight;
        }
    }
}
