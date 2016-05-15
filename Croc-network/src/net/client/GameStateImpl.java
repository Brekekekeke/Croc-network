package net.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.headers.GameStateInterface;

/**
 * @author tristan
 *
 */
public class GameStateImpl extends UnicastRemoteObject implements GameStateInterface {

	/**
	 * Should be named as Player or as Game ?
	 */
	private static final long serialVersionUID = 1L;
//	private CrocGameClient thisClient;
	
	/**
	 *  the card i wanna play
	 */
	private int wannaPlay = 0;
	
	/**
	 * how many card is there in this game (depending how many players)
	 */
	private int nbCards = 7;
	/**
	 * my hand
	 */
	private boolean hand[] = new boolean[nbCards];
	
	/**
	 * @throws RemoteException
	 */
	protected GameStateImpl() throws RemoteException {
		super();
		boolean[] firstHand = {true, true, true, true, true, true, true};
		this.setState(firstHand);
//		wannaPlay = 1;
	}

	/* (non-Javadoc)
	 * @see neXt.compute.CallBack#setState(boolean[])
	 */
	public void setState(boolean[] newHand)  throws RemoteException {
		for (int i = 0; i < nbCards; i++)
			hand[i] = newHand[i];
	}

	/* (non-Javadoc)
	 * @see neXt.compute.CallBack#getState()
	 */
	@Override
	public boolean[] getState() throws RemoteException {
		return hand;
	}

	/* (non-Javadoc)
	 * @see neXt.compute.CallBack#setWannaPlay(int)
	 */
	@Override
	public void setWannaPlay(int numCard) {
		wannaPlay = numCard;		
	}

	/* (non-Javadoc)
	 * @see neXt.compute.CallBack#getWannaPlay()
	 */
	@Override
	public int getWannaPlay() throws RemoteException {
		return wannaPlay;
	}

	/* (non-Javadoc)
	 * @see neXt.compute.CallBack#setCard(int, boolean)
	 */
	@Override
	public void setCard(int cardNumber, boolean state) throws RemoteException {
		hand[cardNumber] = state;		
	}
	
	/**
	 * @param numCard Wich card i want to know state.
	 * @return The state of the card.
	 * @throws RemoteException
	 */
	public boolean getThisCardState(int numCard) throws RemoteException {
		return hand[numCard];
	}
}
