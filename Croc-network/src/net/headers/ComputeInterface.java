package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;

/**
 * @author CrocTeam
 * All you can ask to the server. Answers are in GameState return.
 */
public interface ComputeInterface extends Remote {
	

	/** Create a new game.
	 * @param nbPlayers How many slots in this game
	 * @throws RemoteException
	 */
	public GameStateInterface newGame(GameStateInterface client, int nbPlayers) throws RemoteException;
	
	/** Join a game. Maybe will lock your client since game is not started. Will require WAITCO
	 * @param client
	 * @param expectedName the name i want to use.
	 * @param expectedColor the color i want to use.
	 * @return Updated GameState.
	 * @throws RemoteException
	 */
	public GameStateInterface joinGame(GameStateInterface client, String expectedName, PirateColor expectedColor) throws RemoteException;
	
	
	/** Adding a bot player in the game. Will require WAITCO.
	 * @param expectedColor
	 * @return
	 */
	public GameStateInterface addBotPlayer(GameStateInterface client, PirateColor expectedColor) throws RemoteException;
	
	/** Ask the server to start the game. Will require READY
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public GameStateInterface startGame(GameStateInterface client) throws RemoteException;
	
	/** In which step is the server?
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public GameStateInterface getServerStep(GameStateInterface client) throws RemoteException;
	
	/** Ask to play a card. Will require WAITCARD.
	 * @param client 
	 * @throws RemoteException
	 */
	public GameStateInterface playCard (GameStateInterface client) throws RemoteException;
	
	/** Ask for a fully updated GameState
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public GameStateInterface updateMe(GameStateInterface client) throws RemoteException;
	
	/** Clean way to left the game.
	 * @param client
	 * @throws RemoteException
	 */
	public GameStateInterface quitGame(GameStateInterface client) throws RemoteException;
	
	/** Close server.
	 * @throws RemoteException
	 */
	public void shutDown() throws RemoteException;
	
//	public int getNbPlayers() throws RemoteException;
	
//	public void setNbPlayers(int nbPlayers) throws RemoteException;
	
//	public int getNbCard() throws RemoteException;
	
}
