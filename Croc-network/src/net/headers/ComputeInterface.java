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
	
	public void newGame(int nbPlayers) throws RemoteException;
	
	public GameStateInterface joinGame(GameStateInterface client) throws RemoteException;
}
