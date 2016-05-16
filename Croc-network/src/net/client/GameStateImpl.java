package net.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import engine.models.PirateColor;
import engine.models.Player;
import net.headers.GameStateInterface;
import net.headers.ServerStep;

/**
 * @author CrocTeam
 * Implementation of GameStateInterface.
 * Added methods to easily set or find information you need to.
 */
public class GameStateImpl extends UnicastRemoteObject implements GameStateInterface {

	private static final long serialVersionUID = 1L;

	private long clientID;
	private String clientName;
	private PirateColor clientColor;

	private ServerStep lastKnownStep;
	private int nbPlayers;
	private int nbCards;
	
	public Player players[];

	private int cardToPlay;
	private int LastPlayedCard;
	
	
	
	protected GameStateImpl() throws RemoteException {
		super();
	}
	
	//Getters, Setters
	
	public void setClientName(String name) {
		this.clientName = name;
	}
	
	public void setClientColor(PirateColor color) {
		this.clientColor = color;
	}
	
	public ServerStep getStep() throws RemoteException {
		return lastKnownStep;
	}

	public int getnbPlayers() throws RemoteException {
		return nbPlayers;
	}


	public int getNbCards() throws RemoteException {
		return nbCards;
	}

	public Player[] getPlayers() throws RemoteException {
		return players;
	}

	public void setCardToPlay(int cardNumber) throws RemoteException {
		this.cardToPlay = cardNumber;
	}


	public int getLastPlayedCard() throws RemoteException {
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
	public boolean getThisCardState(int numCard) throws RemoteException {
		boolean something = true;
		return something;
	}
	
	public boolean[] getThisPlayerHand(int playerNum) throws RemoteException {
		return players[playerNum].getCards(this.getNbCards());
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
	public String getClientName() throws RemoteException {
		return this.clientName;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.GameStateInterface#getClientColor()
	 */
	@Override
	public PirateColor getClientColor() throws RemoteException {
		return this.clientColor;
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
	public void setPlayers(Player[] players) throws RemoteException {
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
