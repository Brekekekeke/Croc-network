package engine.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import engine.controller.AIBox;
import engine.exceptions.UnavailableCardException;

/**
 * Holds data revelant to the Pirate.
 * @author CrocTeam
 *
 */
public class Pirate extends UnicastRemoteObject {
	boolean leftLeg;
	boolean rightLeg;
	boolean leftArm;
	boolean rightArm;
	int limbCount;
	public final PirateColor color;
	public Card[] cards;
	//TODO: add unit tests to see is available cards always work correctly.
	public ArrayList<Card> availableCards;
	private int lastPlayedCard;
	public boolean hasPlayed;
	final public Player owner;
	private AIBox ai;
	
	public int getLastPlayedCard(){
		return lastPlayedCard;
	}
	
	public void lastPlayedCardRead(){
		hasPlayed = false;
		cards[lastPlayedCard-1].playCard();
	}
	
	/**
	 * only to be used for debug purpose
	 * @param i set value of card, for debug.
	 */
	public void setlastPlayedCard(int i){
		lastPlayedCard = i;
	}
	
	public Pirate(PirateColor color_, int cardAmount, Player owner_) throws RemoteException {
		leftLeg = true;
		leftArm = true;
		rightArm = true;
		rightLeg = true;
		limbCount = 4;
		hasPlayed = false;
		color = color_;
		cards = new Card[cardAmount];
		availableCards = new ArrayList<Card>();
		for(int i = 1; i <= cardAmount; i++)
			cards[i-1] = new Card(i);
		for(int i = 0; i < cards.length; i++)
			availableCards.add(cards[i]);
		owner = owner_;
		ai = new AIBox(this);
	}
	
	public boolean isAlive(){
		if(limbCount > 0){
			return true;
		}
		
		return false;
	}
	
	public String getColor(){
		switch(color){
		case WHITE:
			return "white";
		case RED:
			return "red";
		case GREEN:
			return "green";
		case PURPLE:
			return "purple";
		case ORANGE:
			return "orange";
		case YELLOW:
			return "yellow";
		case BLACK:
			return "black";
		default:
			return "undefined color";
		}
	}
	
	public boolean popLeftLeg(){
		if(leftLeg){
			leftLeg = false;
			limbCount--;
			return true;
		}
		return false;
	}
	
	public boolean popRightLeg(){
		if(rightLeg){
			rightLeg = false;
			limbCount--;
			return true;
		}
		return false;
	}
	
	public boolean popLeftArm(){
		if(leftArm){
			leftArm = false;
			limbCount--;
			return true;
		}
		return false;
	}
	
	public boolean popRightArm(){
		if(rightArm){
			rightArm = false;
			limbCount--;
			return true;
		}
		return false;
	}

	public boolean hasLeftLeg() {
		return leftLeg;
	}

	public boolean hasRightLeg() {
		return rightLeg;
	}

	public boolean hasLeftArm() {
		return leftArm;
	}

	public boolean hasRightArm() {
		return rightArm;
	}
	
	public int getLimbCount(){
		return limbCount;
	}
	
	public void RecoverHand(){
		for(int i = 0; i < cards.length; i++){
			cards[i].recoverCard();
		}
		availableCards.clear();
		for(int i = 0; i < cards.length; i++)
			availableCards.add(cards[i]);
	}
	/**
	 * Plays a card (1-7 to 1-5 depending on amount of players).
	 * @param cardValue value of card played
	 * @return value of card played, -1 if error
	 */
	public int playCard(int cardValue) throws UnavailableCardException{
		if(cards[cardValue-1] != null){
			if(cards[cardValue-1].isInHand()){
				hasPlayed = true;
				lastPlayedCard = cards[cardValue-1].value;
				for(int i = 0; i < availableCards.size(); i++){
					if(availableCards.get(i).value == cards[cardValue-1].value)
						availableCards.remove(i);
				}
				return cards[cardValue-1].value;
			}
			else
				throw new UnavailableCardException();
		}
		else{
			throw new UnavailableCardException();
		}
	}
	
	public boolean popLimb(){
		boolean limbPopped = false;
		int limbToPop;
		while(!limbPopped && limbCount > 0){
			limbToPop = (int) (Math.random()*4);
			if(limbToPop == 0)
				limbPopped = popLeftArm();
			else if(limbToPop == 1)
				limbPopped = popLeftLeg();
			else if(limbToPop == 2)
				limbPopped = popRightArm();
			else if(limbToPop == 3)
				limbPopped = popRightLeg();
			else
				System.out.println("popLimb error, value shouldn't be reached.");
		}
		if(limbPopped){
			RecoverHand();
			return true;
		}
		else
			return false;
		
	}
	/**
	 * method to make the bot choose a card, random card available for now.
	 */
	public void botCardChooser(){
		ai.increasingValuation();
		int maxVal = 0;
		for(int i = 0; i < ai.val.size(); i++){
			maxVal += ai.val.get(i);
		}
		Random rand = new Random();
		int randChosen = rand.nextInt(maxVal);
		int counter=0;
		int loop=0;
		while(randChosen >= counter){
			counter+=ai.val.get(loop);
			if(randChosen >= counter)
				loop++;
		}
		ai.val = new ArrayList<Integer>();
		try {
			playCard(availableCards.get(loop).value); //to get value and not slot in array
		} catch (UnavailableCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
