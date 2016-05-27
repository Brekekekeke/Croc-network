package net.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import engine.models.PirateColor;
import net.headers.GameStateInterface;
import net.headers.RemotePlayer;
import net.headers.ServerError;
import net.headers.ServerStep;

/**
 * @author CrocTeam
 * Implementation of GameStateInterface.
 * Added methods to easily set or find information you need to.
 */
public class GameStateImpl extends UnicastRemoteObject implements GameStateInterface {

	private static final long serialVersionUID = 1L;
	private static int maxPlayers = 7;
	
	public int nbPlayersCo;
	public void setNbPlayersCo(int co){
		nbPlayersCo = co;
	}
	public int getNbPlayerCo() {
		return nbPlayersCo;
	}
	
	public int nbBot;
	public void setNbBot(int nbBot) throws RemoteException {
		this.nbBot = nbBot;
	}
	public int getNbBot() {
		return nbBot;
	}
	
	
	private long clientID;
	private String clientName;
	private PirateColor clientColor;

	private ServerStep lastKnownStep;
	private ServerError error;
	private int nbPlayers;
	private int nbCards;
	
	private RemotePlayer players[] = new RemotePlayer[maxPlayers];

	private int cardToPlay;
	private int LastPlayedCard;
	
	
	
	protected GameStateImpl() throws RemoteException {
		super();
	}
	
	//Getters, Setters
	
	public String getClientName() {
		return clientName;
	}
	
	public PirateColor getClientColor() {
		return clientColor;
	}
	
	public ServerStep getStep() {
		return lastKnownStep;
	}
	
	public ServerError getError() {
		return this.error;
	}

	public int getnbPlayers() {
		return nbPlayers;
	}


	public int getNbCards() {
		return nbCards;
	}

	public RemotePlayer[] getPlayers() {
		return players;
	}

	public void setCardToPlay(int cardNumber) {
		this.cardToPlay = cardNumber;
	}


	public int getLastPlayedCard() {
		return LastPlayedCard;
	}
	
	
	// Internal methods
	
	public boolean amIWinner() {
		return false;
	}

	/**
	 * @param numCard Wich card i want to know state.
	 * @return The state of the card.
	 * @throws RemoteException
	 */
	public boolean getThisCardState(int numCard) {
		boolean something = true;
		return something;
	}
	
	public boolean[] getThisPlayerHand(int playerNum) {
//		try {
//			return players[playerNum].getPirate()[0].getHand();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		System.out.println("Probleme dans getThisPlayerHand");
		return null;
	}

	public String getMyName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// Implementation of GameStateInterface
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#getID()
	 */
	@Override
	public long getClientID() throws RemoteException {
		return this.clientID;
	}

	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setId(long)
	 */
	@Override
	public void setClientId(long id) throws RemoteException {
		this.clientID = id;
	}

	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#getClientName()
	 */
	@Override
	public void setClientName(String name) throws RemoteException {
		this.clientName = name;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#getClientColor()
	 */
	@Override
	public void setClientColor(PirateColor color) throws RemoteException {
		this.clientColor = color;
	}

	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setStep(net.headers.ServerStep)
	 */
	@Override
	public void setStep(ServerStep step) throws RemoteException {
		// TODO Auto-generated method stub
		this.lastKnownStep = step;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setError(net.headers.ServerError)
	 */
	@Override
	public void setError(ServerError error) throws RemoteException {
		// TODO Auto-generated method stub
		this.error = error;
	}

	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setNbPlayers(int)
	 */
	@Override
	public void setNbPlayers(int nbPlayers) throws RemoteException {
		// TODO Auto-generated method stub
		this.nbPlayers = nbPlayers; 
	}
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setNbCards(int)
	 */
	@Override
	public void setNbCards(int nbCards) throws RemoteException {
		// TODO Auto-generated method stub
		this.nbCards = nbCards;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setPlayers(engine.models.Player[])
	 */
	@Override
	public void setPlayers(RemotePlayer[] players) throws RemoteException {
		// TODO Auto-generated method stub
		this.players = players;
		
	}
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#getGardToPlay()
	 */
	@Override
	public int getGardToPlay() throws RemoteException {
		// TODO Auto-generated method stub
		return cardToPlay;
	}

	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#setLastPlayedCard(int)
	 */
	@Override
	public void setLastPlayedCard(int cardNumber) throws RemoteException {
		LastPlayedCard = cardNumber;
	}
}
