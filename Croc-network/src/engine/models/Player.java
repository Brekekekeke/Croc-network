package engine.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.headers.RemotePlayer;

/**
 * Hold all data used or linked to a player
 * @author sykefu
 *
 */
public class Player extends UnicastRemoteObject implements RemotePlayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final public String name;
	public Card[] cards;
	public Pirate[] pirates;
	public Boolean hasLost;
	public Boolean isBot;
	
	public Player(int cardAmount, String name_, PirateColor color, Boolean isBot_) throws RemoteException {
		super();
		name = name_;
		
		pirates = new Pirate[1];
		pirates[0] = new Pirate(color, cardAmount, this);
		hasLost = false;
		isBot = isBot_;
	}
	
	public Player(int cardAmount, String name_, PirateColor colorOne, PirateColor colorTwo, Boolean isBot_) throws RemoteException {
		name = name_;
		
		pirates = new Pirate[2];
		pirates[0] = new Pirate(colorOne, cardAmount, this);
		pirates[1] = new Pirate(colorTwo, cardAmount, this);
		hasLost = false;
		isBot = isBot_;
	}
	
	public boolean[] getCards(int cardAmount) {
		boolean result[] = new boolean[cardAmount];
		for (int i = 0; i < cardAmount; i ++) {
			result[i] = cards[i].isInHand(); 
		}
		return result;
	}
	//TODO: recoverhand, loselimb || mostly done in pirates

	@Override
	public PirateColor getColor() throws RemoteException {
		return pirates[0].getColor();
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public Pirate[] getPirate() throws RemoteException {
		return pirates;
	}

	@Override
	public boolean isBot() throws RemoteException {
		// TODO Auto-generated method stub
		return isBot;
	}
}
