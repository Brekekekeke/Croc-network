package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;

/**
 * @author tristan
 *
 */
public interface GameStateInterface extends Remote {
//	Player me = new Player(7, "aName", PirateColor.GREEN, false);
	
	/** Setting game state (here players hand).
	 * @param newHand Actualized hand.
	 * @throws RemoteException
	 */
	public void setState(boolean[] newHand) throws RemoteException;
	
	/** Getting know game state
	 * @return State of game (here players hand).
	 * @throws RemoteException
	 */
	public boolean[] getState() throws RemoteException;
	
	/** Setting a card state.
	 * @param cardNumber The card i want to change state.
	 * @param state New card state.
	 * @throws RemoteException
	 */
	public void setCard(int cardNumber, boolean state) throws RemoteException;
	
	/** Setting the card i wanna play.
	 * @param numCard The card i wanna play.
	 * @throws RemoteException
	 */
	public void setWannaPlay(int numCard) throws RemoteException;
	
	/** Getting the card i wanna play.
	 * @return The card i wanna play.
	 * @throws RemoteException
	 */
	public int getWannaPlay() throws RemoteException;

	public String getClientName() throws RemoteException;

	public PirateColor getClientColor() throws RemoteException;

	public void update() throws RemoteException;
}
