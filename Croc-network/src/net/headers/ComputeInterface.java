package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;

public interface ComputeInterface extends Remote {
//	<T> T executeTask(Task<T> t) throws RemoteException;
	
	/** Ask to play a card.
	 * @param client 
	 * @throws RemoteException
	 */
	public boolean playCard (GameStateInterface client) throws RemoteException;
	
	/** Close server.
	 * @throws RemoteException
	 */
	public void shutDown() throws RemoteException;
	
	/** Create a new game.
	 * @param nbPlayers How many slots in this game
	 * @throws RemoteException
	 */
	public void newGame(int nbPlayers) throws RemoteException;
	
	/** Join a game. Will lock your client since game is not started.
	 * @param client
	 * @param expectedName the name i want to use.
	 * @param expectedColor the color i want to use.
	 * @return Updated GameState.
	 * @throws RemoteException
	 */
	public GameStateInterface joinGame(GameStateInterface client, String expectedName, PirateColor expectedColor) throws RemoteException;
	
	/** Clean way to left the game.
	 * @param client
	 * @throws RemoteException
	 */
	public GameStateInterface quitGame(GameStateInterface client) throws RemoteException;

//	public int getNbPlayers() throws RemoteException;
	
//	public void setNbPlayers(int nbPlayers) throws RemoteException;
	
//	public int getNbCard() throws RemoteException;
	
}
