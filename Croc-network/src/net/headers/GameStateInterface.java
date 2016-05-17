package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;

/**
 * @author CrocTeam
 * All the server can tell you about the game.
 */
public interface GameStateInterface extends Remote {

	
	public void setNbPlayersCo(int nbCo) throws RemoteException;
	
	/** Private client id (security patch)
	 * @return
	 * @throws RemoteException
	 */
	public long getClientID() throws RemoteException;
	public void setClientId(long id) throws RemoteException;
	
	
	/** Players name, authorized by server.
	 * @return
	 * @throws RemoteException
	 */
//	public String getClientName();
	public void setClientName(String name) throws RemoteException;
	
	/** Players color, authorized by server.
	 * @return
	 * @throws RemoteException
	 */
//	public PirateColor getClientColor();
	public void setClientColor(PirateColor color) throws RemoteException;
	
	/** Step of the game
	 * @return
	 * @throws RemoteException
	 */
//	public ServerStep getStep();
	public void setStep(ServerStep step) throws RemoteException;
	
	/** Is there an error?
	 * @param error
	 * @throws RemoteException
	 */
//	public ServerError getError();
	public void setError(ServerError error) throws RemoteException;
	
	
	/** How many players in total. Will require step WAITCARD or WON.
	 * @return
	 * @throws RemoteException
	 */
//	public int getnbPlayers();
	public void setNbPlayers(int nbPlayers) throws RemoteException;
	
	
	/** How many cards available for this game. Will require step WAITCARD.
	 * @return
	 * @throws RemoteException
	 */
//	public int getNbCards();
	public void setNbCards(int nbCards) throws RemoteException;

	
	/** Players in the game. Will require step WAITCARD or WON.
	 * @return
	 * @throws RemoteException
	 */
//	public RemotePlayerImpl[] getPlayers();
	public void setPlayers(RemotePlayerInterface[] players) throws RemoteException;
	
	
	/** The card that client want to play. Will require step WAITCARD.
	 * @return
	 * @throws RemoteException
	 */
	public int getGardToPlay() throws RemoteException;	
//	public void setCardToPlay(int cardNumber)n;
	
	
	/** The last card from you the server admitted
	 * @param cardNumber
	 * @throws RemoteException
	 */
//	public int getLastPlayedCard();
	public void setLastPlayedCard(int cardNumber) throws RemoteException;
	
}
