package net.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import engine.models.PirateColor;
import engine.models.Player;
import net.headers.GameStateInterface;
import net.headers.GameStep;

/**
 * @author tristan
 *
 */
public class GameStateImpl extends UnicastRemoteObject implements GameStateInterface {

	private static final long serialVersionUID = 1L;

	public Player players[];

	private int LastPlayedCard;
	
	protected GameStateImpl() throws RemoteException {
		super();
	}
	
	// Client methods
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

	public void setMyName(String name) {
		
	}
	public String getMyName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void getMyColor(PirateColor color) throws RemoteException {
		// TODO Auto-generated method stub
	}
	public PirateColor getMyColor() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	// Interface methods
	@Override
	public long getID() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public GameStep getStep() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStep(GameStep step) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public int getnbPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNbPlayers(int nbPlayers) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public int getNbCards() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNbCards(int nbCards) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public Player[] getPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayers(Player[] players) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGardToPlay() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
	public void setCardToPlay(int cardNumber) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getClientName() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PirateColor getClientColor() throws RemoteException {
		// TODO Auto-generated method stub
		return this.getMyColor();
	}
	
	public void setLastPlayedCard(int cardNumber) throws RemoteException {
		LastPlayedCard = cardNumber;
	}
	public int getLastPlayedCard() throws RemoteException {
		return LastPlayedCard;
	}
}
