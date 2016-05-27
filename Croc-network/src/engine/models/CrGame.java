package engine.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import net.headers.CrGameInterface;

public class CrGame extends UnicastRemoteObject implements CrGameInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player[] players;
	Player winner;
	ArrayList<Pirate> pirateOrder;
	
	public CrGame(Player[] players_, int PirateAmount) throws RemoteException {
		players = players_;
		pirateOrder = new ArrayList<Pirate>();
		for(int i = 0; i < players.length; i++){
			for(int j = 0; j < players[i].pirates.length; j++){
				pirateOrder.add(players[i].pirates[j]);
			}
		}
		winner = null;
	}
	
	/**
	 * @return the players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * @return the pirateOrder
	 */
	public ArrayList<Pirate> getPirateOrder() {
		return pirateOrder;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
}

