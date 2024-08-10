package Naloga4;

public class Card {
    private String[] winningNumbers;
    private String[] cardNumbers;
    private int copies;
    
    public Card(String[] winningNumbers, String[] cardNumbers) {
        this.winningNumbers = winningNumbers;
        this.cardNumbers = cardNumbers;
        this.copies = 1;
    }

    public String[] getWinningNumbers() {
        return winningNumbers;
    }
    public void setWinningNumbers(String[] winningNumbers) {
        this.winningNumbers = winningNumbers;
    }
    public String[] getCardNumbers() {
        return cardNumbers;
    }
    public void setCardNumbers(String[] cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
    public int getCopies() {
        return copies;
    }
    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int checkMatches() {
        int matches = 0;
        for (String cardNumber : cardNumbers) {
            for (String winningNumber : winningNumbers) {
                if (winningNumber.equals(cardNumber)) {
                    matches++;
                }
            }
        }
        return matches;
    }
}
