public class Player {
	
	// Player properties
	private String name;
	private int money;
	private int amountBet;
	private Card[] hand;
	private boolean bigBlind;
	private boolean playingRound;
	private boolean madeAction;
	private boolean isAI;
	
	// Constructor
	public Player(String name, int money) {
		this.name = name;
		this.money = money;
		amountBet = 0;
		hand = new Card[]{null, null};
		bigBlind = false;
		playingRound = true;
		madeAction = false;
		isAI = false;
	}
	
	// Getters and setters
	public Card[] getHand() {
		return hand;
	}
	
	public void setHand(Card card1, Card card2) {
		this.hand = new Card[]{card1, card2};
	}

	public boolean isBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(boolean bigBlind) {
		this.bigBlind = bigBlind;
	}
	
	public String getName() {

		return name;
	}

	public int getMoney() {
		return money;
	}
	
	public void addMoney(int moneyAdded) {
		money += moneyAdded;
	}

	public void removeMoney(int moneyRemoved) {
		amountBet += moneyRemoved > money ? money : moneyRemoved;
		money -= moneyRemoved > money ? money : moneyRemoved;
	}

	public boolean isPlayingRound() {
		return playingRound;
	}

	public void setPlayingRound(boolean playingRound) {
		this.playingRound = playingRound;
	}
	
	public boolean hasMadeAction() {
		return madeAction;
	}

	public void setHasActioned(boolean madeAction) {
		this.madeAction = madeAction;
	}
	
	public boolean isAI() {
		return isAI;
	}
	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}
	
	// Reset all the players actions
	public static Player[] resetActions(Player[] players) {
		for (int i = 0; i < players.length; i++) {
			players[i].setHasActioned(false);
		}
		return players;
	}
	
	// Check if all players have actioned
	public static boolean checkActions(Player[] players) {
		boolean hasAllActioned = true;
		for (int i = 0; i < players.length; i++) {
			if (!players[i].hasMadeAction()) {
				hasAllActioned = false;
			}
		}
		return hasAllActioned;
	}

	// Methods to handle betting
	public int getAmountBet() {
		return amountBet;
	}
	public void returnBetMoney(int amountReturned) {
		amountBet -= amountReturned;
		money += amountReturned;
	}
	
	// Calculate AI odds of winning
	public static double analyzeOdds(Card[] river, Card[] hand, int numPlayers) {
		Card[] deck;
		Card[] riverCopy;
		
		Player[] players = new Player[numPlayers];
		Player winningPlayer;
		
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(Integer.toString(i), 0);
		}
		
		int wins = 0;
		
		// Simulate 1 million games with this hand and amount of players
		for (int i = 0; i < 1000000; i++) {
			deck = Card.generateCards();
			deck = Card.shuffle(deck);
			//System.out.println(Arrays.deepToString(deck));
			riverCopy = new Card[river.length];	
			
			for (int j = 0; j < river.length; j++) {
				riverCopy[j] = river[j];
			}
			
			for (int j = 0; j < numPlayers-1; j++) {
				Card card1 = Card.getCardFromDeck(deck);
				deck = Card.removeCardFromDeck(deck);
				Card card2 = Card.getCardFromDeck(deck);
				deck = Card.removeCardFromDeck(deck);
				players[j].setHand(card1, card2);
				players[j].setPlayingRound(true);
			}
			
			players[numPlayers-1].setHand(hand[0], hand[1]);
			
			while (riverCopy.length < 5) {
				riverCopy = Card.addCardToDeck(riverCopy, Card.getCardFromDeck(deck));
				deck = Card.removeCardFromDeck(deck);
			}
			winningPlayer = PokerUtils.getWinningPlayer(riverCopy, players);
			if (winningPlayer == players[numPlayers-1]) {
				wins++;
			}
		}
		
		return ((double)wins/1000000);
	}
}
