public class PokerUtils {
	
	// Used to create sets of 5 cards from 7 cards
	private static final String[] SEVEN_CARD_COMBINATIONS = 
	{"12345",
	"12346",
	"12347",
	"12356",
	"12357",
	"12367",
	"12456",
	"12457",
	"12467",
	"12567",
	"13456",
	"13457",
	"13467",
	"13567",
	"14567",
	"23456",
	"23457",
	"23467",
	"23567",
	"24567",
	"34567"};
	
	// Method to get the best 5 card combination from 7 cards
	public static int[] evaluateHand(Card[] river, Card[] hand) {
		Card[] sevenCardHand = new Card[7];
		
		// Tracks the best hand, where the first index is the rank, second is the highest special card (aka the value of a pair)
		// and third is the highest card outside of the special hand
		int[] evaluatedHand = new int[3];
		
		for (int i = 0; i < river.length; i++) {
			sevenCardHand[i] = river[i];
 		}
		for (int i = 0; i < hand.length; i++) {
			sevenCardHand[i+river.length] = hand[i];
 		}
		// For every combination of 5 cards in 7 cards, evaluate them
		for (int combinationIndex = 0; combinationIndex < SEVEN_CARD_COMBINATIONS.length; combinationIndex++) {
			Card[] fiveCardHand = new Card[5];
			String currentCombination = SEVEN_CARD_COMBINATIONS[combinationIndex];
			for (int i = 0; i < 5; i++) {
				int cardIndex = -1;
				try {
					cardIndex = Integer.parseInt(Character.toString(currentCombination.charAt(i))) - 1;
				} catch (NumberFormatException e) {
					System.out.println("Error");
				}
				if (cardIndex != -1) {
					fiveCardHand[i] = sevenCardHand[cardIndex];
				}
			}
			
			// Get the highest card in the hand
			for (int card = 0; card < fiveCardHand.length; card++) {
				if (fiveCardHand[card].getValue() > evaluatedHand[2]) {
					evaluatedHand[2] = fiveCardHand[card].getValue();
				}
			}
			
			// Set the ranking, highest card in the special hand, and the highest card outside the special hand
			if (isPair(fiveCardHand, evaluatedHand) != -1) {
				if (evaluatedHand[0] < 1) {
					evaluatedHand[0] = 1;
				}
				if (evaluatedHand[1] < isPair(fiveCardHand, evaluatedHand)) {
					evaluatedHand[1] = isPair(fiveCardHand, evaluatedHand);
				}
			}
			if (isTwoPair(fiveCardHand, evaluatedHand) != -1) {
				if (evaluatedHand[0] < 2) {
					evaluatedHand[0] = 2;
				}
				if (evaluatedHand[1] < isTwoPair(fiveCardHand, evaluatedHand)) {
					evaluatedHand[1] = isTwoPair(fiveCardHand, evaluatedHand);
				}
			}
			if (isThreeOfAKind(fiveCardHand, evaluatedHand) != -1) {
				if (evaluatedHand[0] < 3) {
					evaluatedHand[0] = 3;
				}
				if (evaluatedHand[1] < isThreeOfAKind(fiveCardHand, evaluatedHand)) {
					evaluatedHand[1] = isThreeOfAKind(fiveCardHand, evaluatedHand);
				}
			}
			if (isStraight(fiveCardHand)) {
				if (evaluatedHand[0] < 4) {
					evaluatedHand[0] = 4;
				}
			}
			if (isFlush(fiveCardHand)) {
				if (evaluatedHand[0] < 5) {
					evaluatedHand[0] = 5;
				}
			}
			if (isFullHouse(fiveCardHand) != -1) {
				if (evaluatedHand[0] < 6) {
					evaluatedHand[0] = 6;
				}
				if (evaluatedHand[1] < isFullHouse(fiveCardHand)) {
					evaluatedHand[1] = isFullHouse(fiveCardHand);
				}
			}
			if (isFourOfAKind(fiveCardHand, evaluatedHand) != -1) {
				if (evaluatedHand[0] < 7) {
					evaluatedHand[0] = 7;
				}
				if (evaluatedHand[1] < isFourOfAKind(fiveCardHand, evaluatedHand)) {
					evaluatedHand[1] = isFourOfAKind(fiveCardHand, evaluatedHand);
				}
			}
			if (isStraight(fiveCardHand) && isFlush(fiveCardHand)) {
				if (evaluatedHand[0] < 8) {
					evaluatedHand[0] = 8;
				}
			}
			if (isStraight(fiveCardHand) && isFlush(fiveCardHand) && evaluatedHand[2] == 13) {
				if (evaluatedHand[0] < 9) {
					evaluatedHand[0] = 9;
				}
			}
		}
		
		// Return the best 5 card combination
		return evaluatedHand;
	}
	
	// Gets the winning player by comparing their best 5 card combinations
	public static Player getWinningPlayer(Card[] river, Player[] players) {
		// Current best evaluation
		int[] bestEval = new int[]{0,0,0};
		Player winningPlayer = null;
		
		// For each player, if they are playing the round, evaluate their hand and compare it to the current best evaluation.
		for (int i = 0; i < players.length; i++) {
			if (players[i].isPlayingRound()) {
				int[] evaluatedHand = evaluateHand(river, players[i].getHand());
				if (evaluatedHand[0] > bestEval[0]) {
					for (int j = 0; j < bestEval.length; j++) {
						bestEval[j] = evaluatedHand[j];
					}
					winningPlayer = players[i];
				} else if (evaluatedHand[0] == bestEval[0]) {
					if (evaluatedHand[0] == 1 || evaluatedHand[0] == 2 || evaluatedHand[0] == 3 || evaluatedHand[0] == 7) {
						if (evaluatedHand[1] > bestEval[1]) {
							for (int j = 0; j < bestEval.length; j++) {
								bestEval[j] = evaluatedHand[j];
							}
							winningPlayer = players[i];
						} else if (evaluatedHand[1] == bestEval[1]) {
							if (evaluatedHand[2] > bestEval[2]) {
								for (int j = 0; j < bestEval.length; j++) {
									bestEval[j] = evaluatedHand[j];
								}
								winningPlayer = players[i];
							} else if (evaluatedHand[2] == bestEval[2]) {
								int coinFlip = (int)(Math.random()*2);
								if (coinFlip == 1) {
									for (int j = 0; j < bestEval.length; j++) {
										bestEval[j] = evaluatedHand[j];
									}
									winningPlayer = players[i];
								}
							}
						}
					} else {
						if (evaluatedHand[2] > bestEval[2]) {
							for (int j = 0; j < bestEval.length; j++) {
								bestEval[j] = evaluatedHand[j];
							}
							winningPlayer = players[i];
						} else if (evaluatedHand[2] == bestEval[2]) {
							int coinFlip = (int)(Math.random()*2);
							if (coinFlip == 1) {
								for (int j = 0; j < bestEval.length; j++) {
									bestEval[j] = evaluatedHand[j];
								}
								winningPlayer = players[i];
							}
						}
					}
				}
			}
		}
		
		// Return the winning player
		return winningPlayer;
	}
	
	// Sorts a hand by value (e.g 3,2,5 -> 2,3,5)
	private static Card[] sortHandRank(Card[] hand) {
		boolean sorted = false;
		boolean swapped;
		while (!sorted) {
			swapped = false;
			for (int i = 0; i < hand.length; i++) {
				if (i + 1 < hand.length) {
					if (hand[i].getValue() > hand[i+1].getValue()) {
						swapped = true;
						Card tempCard = hand[i+1];
						hand[i+1] = hand[i];
						hand[i] = tempCard;
					}
				}
			}
			if (!swapped) {
				sorted = true;
			}
		}
		return hand;
	}
	
	// Is the hand a pair?
	private static int isPair(Card[] hand, int[] eval) {
		hand = sortHandRank(hand);
		int pairRank = -1;
		Card[] remainingHand = new Card[]{};
		for (int i = 0; i < hand.length; i++) {
			if (i + 1 < hand.length && i - 1 >= 0) {
				// If the next card or the previous card are the same, its a pair
				if (hand[i+1].getValue() == hand[i].getValue() || hand[i].getValue() == hand[i-1].getValue()) {
					pairRank = hand[i].getValue();
				} else {
					Card[] temp = new Card[remainingHand.length+1];
					for (int j = 0; j < remainingHand.length; j++) {
						temp[j] = remainingHand[j];
					}
					temp[temp.length-1] = hand[i];
					remainingHand = new Card[temp.length];
					for (int j = 0; j < temp.length; j++) {
						remainingHand[j] = temp[j];
					}
				}
			}
		}
		
		for (int i = 0; i < remainingHand.length; i++) {
			if (remainingHand[i].getValue() > eval[2]) {
				// Update the highest card outside the special hand
				eval[2] = remainingHand[i].getValue();
			}
		}
		
		return pairRank;
	}
	
	// Is the hand a two pair?
	private static int isTwoPair(Card[] hand, int[] eval) {
		hand = sortHandRank(hand);
		
		int numPairs = 0;
		int highestPair = 0;
		
		Card[] remainingHand = new Card[5];
		for (int i = 0; i < hand.length; i++) {
			remainingHand[i] = hand[i];
		}
		
		for (int i = 0; i < hand.length; i++) {
			if (i + 1 < hand.length) {
				// If the next card is the same, its a pair
				if (hand[i+1].getValue() == hand[i].getValue()) {
					remainingHand[i] = null;
					remainingHand[i+1] = null;
					numPairs++;
					if (highestPair < hand[i].getValue()) {
						hand[i].getValue();
					}
					if (i + 2 < hand.length) {
						i = i+2;
					}
				}
			}
		}
		
		for (int i = 0; i < remainingHand.length; i++) {
			if (remainingHand[i] != null) {
				if (remainingHand[i].getValue() > eval[2]) {
					// Update the highest card outside the special hand
					eval[2] = remainingHand[i].getValue();
				}
			}
		}
		
		// If there were two pairs, return a real value
		if (numPairs == 2) {
			return highestPair;
		} else {
			return -1;
		}
	}
	
	// Is the hand a three of a kind?
	private static int isThreeOfAKind(Card[] hand, int[] eval) {
		hand = sortHandRank(hand);
		
		int threeKindRank = -1;
		Card[] remainingHand = new Card[5];
		for (int i = 0; i < hand.length; i++) {
			remainingHand[i] = hand[i];
		}
		for (int i = 0; i < hand.length; i++) {
			if (i + 2 < hand.length) {
				// If the next and next next card are the same, its three of a kind
				if (hand[i+1].getValue() == hand[i].getValue() && hand[i+2].getValue() == hand[i+1].getValue()) {
					remainingHand[i] = null;
					remainingHand[i+1] = null;
					remainingHand[i+2] = null;
					threeKindRank = hand[i].getValue();
				}
			}
		}
		
		for (int i = 0; i < remainingHand.length; i++) {
			if (remainingHand[i] != null) {
				if (remainingHand[i].getValue() > eval[2]) {
					// Update the highest card outside the special hand
					eval[2] = remainingHand[i].getValue();
				}
			}
		}
		
		return threeKindRank;
	}
	
	// Is the hand a straight?
	private static boolean isStraight(Card[] hand) {
		hand = sortHandRank(hand);
		
		for (int i = 0; i < hand.length; i++) {
			if (i + 1 < hand.length) {
				// If this card and the next aren't sequential, it's not a straight
				if (hand[i+1].getValue() - 1 != hand[i].getValue()) {
					return false;
				}
			}
		}
		return true;
	}
	
	// Is the hand a flush?
	private static boolean isFlush(Card[] hand) {
		hand = sortHandRank(hand);
		
		for (int i = 0; i < hand.length; i++) {
			if (i + 1 < hand.length) {
				// If this card and the next aren't the same suit, it's not a flush
				if (!hand[i+1].getSuit().equals(hand[i].getSuit())) {
					return false;
				}
			}
		}
		return true;
	} 
	
	// Is the hand a full house?
	private static int isFullHouse(Card[] hand) {
		hand = sortHandRank(hand);
		
		// Compares the first 2 cards and the last 3 cards, or vice versa
		if (hand[0].getValue() == hand[1].getValue() && hand[1].getValue() == hand[2].getValue() && hand[3].getValue() == hand[4].getValue()) {
			return hand[0].getValue();
		} else if (hand[0].getValue() == hand[1].getValue() && hand[2].getValue() == hand[3].getValue() && hand[3].getValue() == hand[4].getValue()) {
			return hand[2].getValue();
		}
		return -1;
	} 
	
	// Is the hand a four of a kind?
	private static int isFourOfAKind(Card[] hand, int[] eval) {
		hand = sortHandRank(hand);
		
		int fourKindRank = -1;
		Card[] remainingHand = new Card[5];
		for (int i = 0; i < hand.length; i++) {
			remainingHand[i] = hand[i];
		}
		
		for (int i = 0; i < hand.length; i++) {
			if (i + 3 < hand.length) {
				// If the next 3 cards are the same value as this one, it's four of a kind
				if (hand[i+1].getValue() == hand[i].getValue() && hand[i+2].getValue() == hand[i+1].getValue() && hand[i+3].getValue() == hand[i+2].getValue()) {
					remainingHand[i] = null;
					remainingHand[i+1] = null;
					remainingHand[i+2] = null;
					remainingHand[i+3] = null;
					fourKindRank = hand[i].getValue();
				}
			}
		}
		
		for (int i = 0; i < remainingHand.length; i++) {
			if (remainingHand[i] != null) {
				if (remainingHand[i].getValue() > eval[2]) {
					// Update the highest card outside the special hand
					eval[2] = remainingHand[i].getValue();
				}
			}
		}
		
		return fourKindRank;
	}
}
