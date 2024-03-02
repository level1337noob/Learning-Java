import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

enum Rank {
    ACE ("Ace", 1), KING ("King", 13), QUEEN ("Queen", 12), JACK ("Jack", 11),
    TEN ("Ten", 10), NINE ("Nine", 9), EIGHT ("Eight", 8), SEVEN ("Seven", 7),
    SIX ("Six", 6), FIVE ("Five", 5), FOUR ("Four", 4), THREE ("Three", 3), TWO ("Two", 2);

    private String rankName;
    private int rankNumber;
    
    public static int size = 13;
    
    Rank(String rankName, int rankNumber) {
        this.rankName = rankName;
        this.rankNumber = rankNumber;
    }

    String getName() {
        return rankName;
    }
}

enum Suit {
    CLUBS ("Clubs", 0), DIAMONDS ("Diamonds", 1), HEARTS ("Hearts", 2), SPADES ("Spades", 3);
    
    public static int size = 4;

    private String suitName;
    private int suitNumber;
    Suit(String suitName, int suitNumber) {
        this.suitName = suitName;
        this.suitNumber = suitNumber;
    }

    String getName() {
        return suitName;
    }
}

class Card {
    private Rank rank;
    private Suit suit;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    void printCard() {
        System.out.println(rank.getName() + " of " + suit.getName());
    }
}

class Deck {
    final public java.util.ArrayList<Card> cards;
    final private java.util.Random randomGenerator;

    Deck() {
        cards = new java.util.ArrayList<Card>();
        randomGenerator = new java.util.Random();

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                cards.add(new Card(r, s));
            }
        }
    }

    private void swap(int i1, int i2) {
        Card firstCard = cards.get(i1);
        Card secondCard = cards.get(i2);
        cards.set(i2, firstCard);
        cards.set(i1, secondCard);
    }

    public void shuffleDeck() {
        int deckSize = Rank.size * Suit.size;
        
        for (int i = 0; i < deckSize; i++) {
            int randomIndex = randomGenerator.nextInt(deckSize);
            swap(randomIndex, i);
        }
    }

    public void printDeck() {
        swap(0, 1);
        for (ListIterator<Card> t = cards.listIterator(); t.hasNext(); ) {
            Card c = t.next();
            c.printCard();
        }
    }
}

public class Cards {
    public static void main(String[] args) {
        Deck d = new Deck();
        d.shuffleDeck();
        d.printDeck();
    }
}