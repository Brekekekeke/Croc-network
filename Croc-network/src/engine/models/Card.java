package engine.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Card extends UnicastRemoteObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int value;
	private boolean inHand;
	
	public Card(int value_) throws RemoteException {
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
