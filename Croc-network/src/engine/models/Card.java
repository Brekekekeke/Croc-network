package engine.models;

public class Card {
	public final int value;
	private boolean inHand;
	
	public Card(int value_){
		value = value_;
		inHand = true;
	}
	
	public void playCard(){
		inHand = false;
	}
	
	public void recoverCard(){
		inHand = true;
	}
	
	public boolean isInHand(){
		return inHand;
	}
	////

}
