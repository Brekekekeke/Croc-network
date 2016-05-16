package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;
import engine.models.Player;

/** What server can say about the game 
 * @author tristan
 *
 */
public interface GameStateInterface extends Remote {
//	Player me = new Player(7, "aName", PirateColor.GREEN, false);
	
	/** Private client id (security patch)
	 * @return
	 * @throws RemoteException
	 */
	public long getID() throws RemoteException;
	public void setId(long id) throws RemoteException;
	
	
	public String getClientName() throws RemoteException;
//	public void setClientName(String name) throws RemoteException;
	
	public PirateColor getClientColor() throws RemoteException;
//	public void setClientColor(PirateColor color) throws RemoteException;
	
	/** Step of the game
	 * @return
	 * @throws RemoteException
	 */
//	public GameStep getStep() throws RemoteException;
	public void setStep(GameStep step) throws RemoteException;
	
	
	/** How many players in total. Requiert step WAITCARD or WON.
	 * @return
	 * @throws RemoteException
	 */
//	public int getnbPlayers() throws RemoteException;
	public void setNbPlayers(int nbPlayers) throws RemoteException;
	
	
	/** How many cards available for this game. Requiert step WAITCARD.
	 * @return
	 * @throws RemoteException
	 */
//	public int getNbCards() throws RemoteException;
	public void setNbCards(int nbCards) throws RemoteException;

	
	/** Players in the game. Requiert step WAITCARD or WON.
	 * @return
	 * @throws RemoteException
	 */
//	public Player[] getPlayers() throws RemoteException;
	public void setPlayers(Player[] players) throws RemoteException;
	
	
	/** The card that client want to play. Requiert step WAITCARD.
	 * @return
	 * @throws RemoteException
	 */
	public int getGardToPlay() throws RemoteException;	
//	public void setCardToPlay(int cardNumber) throws RemoteException;
	
	
	/** the last card the server admitted
	 * @param cardNumber
	 * @throws RemoteException
	 */
	public void setLastPlayedCard(int cardNumber) throws RemoteException;
//	public int getLastPlayedCard() throws RemoteException;
	
	
//	public void setState(boolean[] newHand) throws RemoteException;
//	
//	/** Getting know game state
//	 * @return State of game (here players hand).
//	 * @throws RemoteException
//	 */
//	public boolean[] getState() throws RemoteException;
//
//	
//	/** Setting the card i wanna play.
//	 * @param numCard The card i wanna play.
//	 * @throws RemoteException
//	 */
//	public void setWannaPlay(int numCard) throws RemoteException;
//	
//	/** Getting the card i wanna play.
//	 * @return The card i wanna play.
//	 * @throws RemoteException
//	 */
//	public int getWannaPlay() throws RemoteException;
//
//
//	public void update() throws RemoteException;
}
