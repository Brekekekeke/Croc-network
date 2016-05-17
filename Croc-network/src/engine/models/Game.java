package engine.models;

import java.rmi.RemoteException;
import java.util.ArrayList;

import net.headers.RemotePlayer;

public class Game {
	RemotePlayer[] players;
	RemotePlayer winner;
	ArrayList<Pirate> pirateOrder;
	
	public Game(RemotePlayer[] players_, int PirateAmount){
		System.out.println("contructor Game " + players.length);
		players = players_;
		pirateOrder = new ArrayList<Pirate>();
		for(int i = 0; i < players.length; i++){
			try {
				System.out.println("Constr Game for " + players[i].getPirate().length);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for(int j = 0; j < players[i].getPirate().length; j++){
					System.out.println("----Constr Game for");
					pirateOrder.add(players[i].getPirate()[j]);
					System.out.println("----Constr Game for fin");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		winner = null;
		System.out.println("End Contr Game");
	}
	
	/**
	 * @return the players
	 */
	public RemotePlayer[] getPlayers() {
		return players;
	}

	/**
	 * @return the pirateOrder
	 */
	public ArrayList<Pirate> getPirateOrder() {
		return pirateOrder;
	}

	public RemotePlayer getWinner() {
		return winner;
	}

	public void setWinner(RemotePlayer winner) {
		this.winner = winner;
	}
	
}

