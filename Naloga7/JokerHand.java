package Naloga7;

public class JokerHand {
    private String cards;
    private int bid;
    private int strength;

    public JokerHand(String cards, int bid) {
        this.cards = cards;
        this.bid = bid;
        this.strength = getValue();
    }

    public String getCards() {
        return cards;
    }
    public void setCards(String cards) {
        this.cards = cards;
    }
    public int getBid() {
        return bid;
    }
    public void setBid(int bid) {
        this.bid = bid;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    public int getMatches() {
        int maxMatches = 0;
        int matchingCards = 0;
        for (int i = 0; i < cards.length(); i++) { // 4 > 5 > 6 > 7 > J
            int matches = 0;
            int isMatching = (cards.charAt(i) == 'J') ? 1 : -1;
            for (int j = 0; j < cards.length(); j++) { // 4 > 5 > 6 > 7 > J
                if (cards.charAt(i) == cards.charAt(j) || cards.charAt(j) == 'J') {
                    matches++;
                }
                if (cards.charAt(i) == cards.charAt(j)) {
                    isMatching++;
                }
            }
            if (matches > maxMatches) {
                maxMatches = matches;
            }
            if (isMatching > 0) {
                matchingCards++;
            }
        }
        return maxMatches * matchingCards;
    }

    public int getValue() {
        int value = 0;
        int matches = getMatches();
        switch (matches) {
            case 25, 20:
                value += 0x600000;
                break;
            case 16, 12:
                value += 0x500000;
                break;
            case 15:
                value += 0x400000;
                break;
            case 9, 6:
                value += 0x300000;
                break;
            case 8:
                value += 0x200000;
                break;
            case 4, 2:
                value += 0x100000;
                break;
            default:
                break;
        }
        int cardPosition = 0x10000;
        for (int i = 0; i < cards.length(); i++) {
            value += cardToValue(cards.charAt(i)) * cardPosition;
            cardPosition /= 0x10;
        }
        return value;
    }

    public int cardToValue(char card) {
        if (Character.isDigit(card)) {
            return card - '0';
        } else {
            switch (card) {
                case 'T':
                    return 0xA;
                case 'J':
                    return 0;
                case 'Q':
                    return 0xC;
                case 'K':
                    return 0xD;
                case 'A':
                    return 0xE;
                default:
                    System.out.println("Input not in correct format.");
                    return 0;
            }
        }
    }

    @Override
    public String toString() {
        return cards + "\t" + bid + "\t" + strength;
    }
}
