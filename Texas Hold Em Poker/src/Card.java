
public class Card {

	// Card properties
	private String suit;
	private int value;
	
	// Constructor
	public Card(String suit, int value) {
		
		this.suit = suit;
		this.value = value;
	}
	
	// Get suit and value of cards
	public String getSuit() {
		
		return suit;
	}
	public int getValue() {
		
		return value;
	}
	
	// Generate a deck of cards
	public static Card[] generateCards() {
		
		Card[] cards = new Card[52];
		for (int i = 0; i < cards.length; i++) {
			
			int value = i%13+1;
			
			if (i < 13) {
				cards[i] = new Card("Spade", value);
			} else if (i >= 13 && i < 26) {
				cards[i] = new Card("Heart", value);
			} else if (i >= 26 && i < 39) {
				cards[i] = new Card("Diamond", value);
			} else if (i >= 39 && i < 52) {
				cards[i] = new Card("Club", value);
			}
		}
		
		return cards;
	}
	
	// Shuffle a deck
	public static Card[] shuffle(Card[] deck) {
		
		for (int i = 0; i < deck.length; i++) {
			
			int randomIndex = (int)(Math.random()*deck.length);
			
			Card temp = deck[i];
			deck[i] = deck[randomIndex];
			deck[randomIndex] = temp;
		}
		
		return deck;
	}
	
	// Get the top most card from the deck
	public static Card getCardFromDeck(Card[] deck) {
		Card card = deck[deck.length-1];
		Card[] temp = new Card[deck.length-1];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = deck[i];
		}
		deck = new Card[temp.length];
		for (int i = 0; i < temp.length; i++) {
			deck[i] = temp[i];
		}
		return card;
	}
	
	// Remove the top most card from the deck
	public static Card[] removeCardFromDeck(Card[] deck) {
		Card[] temp = new Card[deck.length-1];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = deck[i];
		}
		deck = new Card[temp.length];
		for (int i = 0; i < temp.length; i++) {
			deck[i] = temp[i];
		}
		return deck;
	}
	
	// Add a card to the top of the deck
	public static Card[] addCardToDeck(Card[] deck, Card card) {
		Card[] temp = new Card[deck.length+1];
		for (int i = 0; i < deck.length; i++) {
			temp[i] = deck[i];
		}
		temp[temp.length-1] = card;
		deck = new Card[temp.length];
		for (int i = 0; i < temp.length; i++) {
			deck[i] = temp[i];
		}
		return deck;
	}
	
	// Used for testing
	@Override
    public String toString() {
        return this.value + " of " + this.suit + "s";
    }
}
