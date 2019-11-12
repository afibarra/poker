package me.afibarra;

import static me.afibarra.CardUtil.*;

// I don't have a real reason to use Comparable, but it feels naturally for me if need
// to compare to "Objects" of the same type.
public class PokerHandAnalyzer implements Comparable {
    private int[] suits, values;
    private HAND_TYPE handType;

    private int[] cardSortedFrequency;

    public PokerHandAnalyzer(String hand) {
        suits = new int[4];
        values = new int[13];
        cardSortedFrequency = new int[5];
        String[] cards = hand.split(",");

        analyzeHand(cards);
        determineHandType();
    }

    public HAND_TYPE getHandType() {
        return handType;
    }

    public int[] cardComparable() {
        return cardSortedFrequency;
    }

    // Determine if this hand is better than some other hand.
    @Override
    public int compareTo(Object object) {
        PokerHandAnalyzer hand = (PokerHandAnalyzer) object;

        if (this.handType.weight() == hand.getHandType().weight()) {
            int[] otherHand = hand.cardComparable();
            for (int i = 0, len = otherHand.length; i < len; i++) {
                if (cardSortedFrequency[i] > otherHand[i]) {
                    return 1;
                } else if (cardSortedFrequency[i] < otherHand[i]) {
                    return -1;
                }
            }
            return 0;
        } else {
            return this.handType.weight() > hand.getHandType().weight() ? 1 : -1;
        }
    }

    private String[] splitSuitAndValue(String card) {
        String[] suitAndValue;

        if (card.matches(".*\\d.*")) {
            suitAndValue = card.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        } else {
            suitAndValue = card.split("(?!^)");
        }

        // This way we can keep using "char" type for "switch" statements
        suitAndValue[0] = "10".equals(suitAndValue[0]) ? "T" : suitAndValue[0];

        return suitAndValue;
    }

    // How many suit-values this hand has?
    private void analyzeHand(String[] cards) {
        String[] suitAndValue;
        char suit, value;

        for (String card : cards) {
            suitAndValue = splitSuitAndValue(card);

            value = suitAndValue[0].charAt(0);
            switch (value) {
                case 'A':
                    values[VALUE.ACE.order()]++;
                    break;
                case '2':
                    values[VALUE.TWO.order()]++;
                    break;
                case '3':
                    values[VALUE.THREE.order()]++;
                    break;
                case '4':
                    values[VALUE.FOUR.order()]++;
                    break;
                case '5':
                    values[VALUE.FIVE.order()]++;
                    break;
                case '6':
                    values[VALUE.SIX.order()]++;
                    break;
                case '7':
                    values[VALUE.SEVEN.order()]++;
                    break;
                case '8':
                    values[VALUE.EIGHT.order()]++;
                    break;
                case '9':
                    values[VALUE.NINE.order()]++;
                    break;
                case 'T':
                    values[VALUE.TEN.order()]++;
                    break;
                case 'J':
                    values[VALUE.JACK.order()]++;
                    break;
                case 'Q':
                    values[VALUE.QUEEN.order()]++;
                    break;
                case 'K':
                    values[VALUE.KING.order()]++;
                    break;
                default:
            }

            suit = suitAndValue[1].charAt(0);
            switch (suit) {
                case 'S':
                    suits[SUIT.SPADE.order()]++;
                    break;
                case 'H':
                    suits[SUIT.HEART.order()]++;
                    break;
                case 'C':
                    suits[SUIT.CLUB.order()]++;
                    break;
                case 'D':
                    suits[SUIT.WINTER.order()]++;
                    break;
                default:
            }
        }
    }

    // Rules of Poker.
    private void determineHandType() {
        boolean isFlush = false;
        boolean isStraight = false;

        for (int suit : suits) {
            if (suit == 5) {
                isFlush = true;
                break;
            }
        }

        int straightCount = 0;
        for (int value : values) {
            if (value == 1) {
                straightCount++;
                if (straightCount == 5) {
                    isStraight = true;
                    break;
                }
            } else {
                straightCount = 0;
            }
        }

        if (isFlush) {
            if (isStraight) {
                handType = HAND_TYPE.STRAIGHT_FLUSH;
            } else {
                handType = HAND_TYPE.FLUSH;
            }
        } else if (isStraight) {
            handType = HAND_TYPE.STRAIGHT;
        } else {
            int[] cardRepeats = new int[3];
            for (int value : values) {
                switch (value) {
                    case 4:
                        cardRepeats[2]++;
                        break;
                    case 3:
                        cardRepeats[1]++;
                        break;
                    case 2:
                        cardRepeats[0]++;
                        break;
                }
            }

            if (cardRepeats[2] == 1) {
                handType = HAND_TYPE.FOUR_OF_A_KIND;
            } else if (cardRepeats[1] == 1) {
                if (cardRepeats[0] == 1) {
                    handType = HAND_TYPE.FULL_HOUSE;
                } else {
                    handType = HAND_TYPE.THREE_OF_A_KIND;
                }
            } else if (cardRepeats[0] == 2) {
                handType = HAND_TYPE.TWO_PAIRS;
            } else if (cardRepeats[0] == 1) {
                handType = HAND_TYPE.ONE_PAIR;
            } else {
                handType = HAND_TYPE.HIGH_CARD;
            }
        }

        cardFrequency();
    }

    private void cardFrequency() {
        int count = -1;

        for (int i = 12; i >= 0; --i) {
            if (values[i] == 4) {
                cardSortedFrequency[++count] = i;
            }
        }

        for (int i = 12; i >= 0; --i) {
            if (values[i] == 3) {
                cardSortedFrequency[++count] = i;
            }
        }

        for (int i = 12; i >= 0; --i) {
            if (values[i] == 2) {
                cardSortedFrequency[++count] = i;
            }
        }

        for (int i = 12; i >= 0; --i) {
            if (values[i] == 1) {
                cardSortedFrequency[++count] = i;
            }
        }
    }
}
