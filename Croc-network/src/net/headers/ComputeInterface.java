package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComputeInterface extends Remote {
//	<T> T executeTask(Task<T> t) throws RemoteException;
	
	/** Play a card.
	 * @param client 
	 * @throws RemoteException
	 */
	public void playCard (GameStateInterface client) throws RemoteException;
	
	/** Close server.
	 * @throws RemoteException
	 */
	public void shutDown() throws RemoteException;
	
	/** Create a new game
	 * @param nbPlayers How many slots in this game
	 * @throws RemoteException
	 */
	public void newGame(int nbPlayers) throws RemoteException;
	
	/** Join a game
	 * @param client callback
	 * @return updated callback
	 * @throws RemoteException
	 */
	public GameStateInterface joinGame(GameStateInterface client) throws RemoteException;

	public int getNbPlayers() throws RemoteException;
	
	public void setNbPlayers(int nbPlayers) throws RemoteException;
	
	public int getNbCard() throws RemoteException;
	
}
