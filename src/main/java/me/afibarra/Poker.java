package me.afibarra;

public class Poker {
    /*
     * Given a set of 5 playing card identifiers such as 2H, 7C, QS, 10D, 2D;
     * determine if this hand is better than some other hand, according to the rules of poker.
     *
     * Hands will be a string with 5 cards comma separated,
     * each card will have 1-2 digits or JQKA and a suit indicator C,D,S,H (i.e. 10C, KH)
     *
     * Possible Hand Types Below:
     *   Straight flush
     *   Four of a kind
     *   Full house
     *   Flush
     *   Straight
     *   Three of a kind
     *   Two pair
     *   One pair
     *
     * The goal of this is to compare between the hand types.
     * Comparing 2 of the same type (i.e. 2 straights) to determine a winner is outside the scope
     * and will not be tested.
     *
     * Implement PokerHand.isGreaterThan(...) method and return whether or not the first hand wins over the second hand.
     */

    static class PokerHand {

        private String handAsString;
        private PokerHandAnalyzer pokerHandAnalyzer;

        public PokerHand(String hand) {
            handAsString = hand;
        }

        public Boolean isGreaterThan(PokerHand hand2) {
            PokerHandAnalyzer pokerHandAnalyzerP1 = new PokerHandAnalyzer(handAsString);
            PokerHandAnalyzer pokerHandAnalyzerP2 = new PokerHandAnalyzer(hand2.handAsString);

            return pokerHandAnalyzerP1.compareTo(pokerHandAnalyzerP2) == 1 ? Boolean.TRUE : Boolean.FALSE;
        }

        @Override
        public String toString() {
            return handAsString;
        }
    }

    public static void testHand1IsGreaterThanHand2(String hand1AsString,
                                                   String hand2AsString,
                                                   Boolean expectedResult) {
        PokerHand hand1 = new PokerHand(hand1AsString);
        PokerHand hand2 = new PokerHand(hand2AsString);
        System.out.println("Hand1[" + hand1 + "] > Hand2[" + hand2 + "] \t-- " +
                "expected: " + expectedResult + ", actual: " + hand1.isGreaterThan(hand2));
    }

    public static void main(String[] args) {
        testHand1IsGreaterThanHand2(
                "8C,9C,10C,JC,QC", // straight flush
                "6S,7H,8D,9H,10D",
                true);

        testHand1IsGreaterThanHand2(
                "4H,4D,4C,4S,JS", //four of a kind
                "6C,6S,KH,AS,AD",
                true);

        testHand1IsGreaterThanHand2(
                "6C,6D,6H,9C,KD",
                "5C,3C,10C,KC,7C", // flush
                false);

        testHand1IsGreaterThanHand2(
                "4H,4D,4C,KC,KD", // full house
                "9D,6S,KH,AS,AD",
                true);

        testHand1IsGreaterThanHand2(
                "6C,6D,6H,9C,KD",
                "2C,3C,4S,5S,6S", // straight
                false);

        testHand1IsGreaterThanHand2(
                "7C,7D,7S,3H,4D", // three of a kind
                "9S,6S,10D,AS,AD",
                true);

        testHand1IsGreaterThanHand2(
                "2S,2D,JH,7S,AC",
                "8C,8H,10S,KH,KS", // two pair
                false);

        testHand1IsGreaterThanHand2(
                "AC,AH,3C,QH,10C", // one pair
                "3S,2D,KH,JS,AD",
                true);
    }
}
