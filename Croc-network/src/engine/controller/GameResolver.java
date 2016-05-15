package engine.controller;

import engine.exceptions.NotEveryoneChoseCardException;
import engine.models.Game;
import engine.models.Pirate;
import engine.models.Player;

/**
 * Recognize win conditions depending on amount of players
 * and resolves game turns.
 * @author sykefu
 *
 */
public class GameResolver {
	Game game;
	public GameResolver(Game game_){
		game = game_;
	}
	/**
	 *  Shuffle pirates in piratesOrder ArrayList.
	 */
	public void gameInit(){
		//just take 2 random pirate and swap, do it a few times. random enough.
	}
	/**
	 * Gives out the new order for the pirate.
	 */
	//TODO: exception if any of the players who haven't lost didn't choose a card.
	//TODO: fix infinite loop if a card wasn't chosen if creating exception doesn't fix.
	public void roundResolve()
	throws NotEveryoneChoseCardException{
		int PirateCount = 0;
		int CardCount = 0;
		for(Pirate p: game.getPirateOrder()){
			if(p.isAlive())
				PirateCount++;
			if(p.getLastPlayedCard()<8)
				CardCount++;
		}
		if(PirateCount != CardCount)
			throw new NotEveryoneChoseCardException();
		
		int getLowestCard;
		boolean isUnique;
		while(PirateCount > 0){
			isUnique = true;
			getLowestCard = 10; //higher than highest card.
			//find the lowest card, marks if the card was only played once.
			for(Pirate p: game.getPirateOrder()){
				if(p.getLastPlayedCard() < getLowestCard){
					getLowestCard = p.getLastPlayedCard();
					isUnique = true;
				}
				else if(p.getLastPlayedCard() == getLowestCard){
					isUnique = false;
				}
			}
			//change card value to higher than 10 (read) and move pirate to front.
			
			if(isUnique){
				for(int i = 0; i < game.getPirateOrder().size(); i++){
					if(game.getPirateOrder().get(i).getLastPlayedCard() == getLowestCard){
						Pirate p = game.getPirateOrder().get(i);
						p.lastPlayedCardRead();
						game.getPirateOrder().remove(i);
						game.getPirateOrder().add(0, p);
						PirateCount--;
					}
				}
			}
			//don't move pirate, change card value to higher than 10 (read)
			else{
				for(Pirate p: game.getPirateOrder()){
					if(p.getLastPlayedCard() == getLowestCard){
						p.lastPlayedCardRead();
						PirateCount--;
					}
				}
			}
		}
		//search for lowest unique value, move to front, change value to ALOT
		//if value appear multiple time, do nothing, change to ALOT
		//once done with amount of pirate, done.
	}
	/**
	 * Let the shark bite the last pirate at the end of the turn and put him to the front.
	 */
	public void sharkTurn(){
		//take last, remove limb, put first.
		Pirate pirateToBite = game.getPirateOrder().get(game.getPirateOrder().size() - 1);
		pirateToBite.popLimb();
		game.getPirateOrder().remove(game.getPirateOrder().size() - 1);
		if(pirateToBite.isAlive()){
			game.getPirateOrder().add(0, pirateToBite);
		}
		//recover cards if only 2 left to any pirate
		for(Pirate p: game.getPirateOrder()){
			if(p.availableCards.size() <= 2)
				p.RecoverHand();
		}
	}
	/**
	 * Checks if the game is won by someone, puts the winner in winner variable.
	 * @return true if the game is finished.
	 */
	public boolean victoryCondition(){
		//for many player games
		Player[] players = game.getPlayers();
		Player tempWin = null;
		int deadCounter = 0;
		if(game.getPlayers().length > 3){
			for(int i = 0; i < players.length; i++){
				if(!players[i].pirates[0].isAlive())
					deadCounter++;
				else
					tempWin = players[i];
			}
			if(deadCounter + 1 == players.length){
				game.setWinner(tempWin);
				return true;
			}
		}
		//for 3 players game
		else if(game.getPlayers().length == 3){
			//could likely just check last pirate in pirateorder
			for (int i = 0; i < players.length; i++){
				for(int j = 0; j < players[i].pirates.length; j++){
					if(!players[i].pirates[j].isAlive()){
						game.setWinner(game.getPirateOrder().get(0).owner);
						return true;
					}
						
				}
			}
		}
		//for 2 palyers game
		else if(game.getPlayers().length == 2){
			int winnerid = -1;
			//could likely just check last pirate in pirateorder
			for (int i = 0; i < players.length; i++){
				for(int j = 0; j < players[i].pirates.length; j++){
					if(!players[i].pirates[j].isAlive()){
						winnerid = (i+1)%2; //if player 0 has a dead pirate choose player 1, if player 1 has a dead pirate choose 0
						game.setWinner(game.getPlayers()[winnerid]);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns whether a player has lost the game or not. Players who have lost won't play subsequent rounds.
	 * Only interesting in 4+ player games since with 2 or 3 the game is over on the first Pirate death.
	 * @param p the player to check
	 * @return true if he lost.
	 */
	public boolean hasLost(Player p){
		for(int i = 0; i < p.pirates.length; i++){
			if(!p.pirates[i].isAlive()){
				p.hasLost = true;
				return true;
			}
		}
		return false;	
	}
}
