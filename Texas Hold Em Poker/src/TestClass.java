
public class TestClass {

	public static void main(String[] args) {
		
		
//		Player player1 = new Player("Player1", 1000);
//		Player player2 = new Player("Player2", 1000);
//		
//		player1.setHand(new Card("Heart" , 5), new Card("Spade" , 8));
//		player2.setHand(new Card("Heart" , 5), new Card("Spade" , 9));
//		
//		Card[] river = {new Card("Clover" , 6),new Card("Clover" , 2),new Card("Clover" , 7),new Card("Diamond" , 2),new Card("Clover" , 2)};
//		
//		
//		for (int cardIndex = 0; cardIndex < player1.getHand().length; cardIndex++) {
//			System.out.println(player1.getHand()[cardIndex].getValue() + " of " + player1.getHand()[cardIndex].getSuit() + "s.");
//		}
//		System.out.println();
//		for (int cardIndex = 0; cardIndex < player2.getHand().length; cardIndex++) {
//			System.out.println(player2.getHand()[cardIndex].getValue() + " of " + player2.getHand()[cardIndex].getSuit() + "s.");
//		}
//		System.out.println();
//		for (int cardIndex = 0; cardIndex < river.length; cardIndex++) {
//			System.out.println(river[cardIndex].getValue() + " of " + river[cardIndex].getSuit() + "s.");
//		}
//		
//		Player winningPlayer = PokerUtils.getWinningPlayer(river, new Player[]{player1, player2});
//		
//		System.out.print(winningPlayer.getName());
		Card[] river = {new Card("Clover" , 6),new Card("Diamond" , 5),new Card("Clover" , 7), new Card("Clover" , 8)};
		Player.analyzeOdds(river, new Card[]{new Card("Heart" , 5), new Card("Spade" , 5)}, 4);
	}
}
