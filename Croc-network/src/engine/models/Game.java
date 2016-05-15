package engine.models;

import java.util.ArrayList;

public class Game {
	Player[] players;
	Player winner;
	ArrayList<Pirate> pirateOrder;
	
	public Game(Player[] players_, int PirateAmount){
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

