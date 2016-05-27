package engine.controller;


import java.rmi.RemoteException;

import engine.exceptions.PlayerAmountException;
import engine.models.CrGame;
import engine.models.PirateColor;
import engine.models.Player;
/**
 * Used to build a game and contains data to initialize another one at later time.
 * @author CrocTeam
 *
 */
public class GameBuilder {
	/**
	 * Amount of players, will be used to know
	 * how many colors/name need to be asked
	 * and special rules.
	 */
	private int playerCount;
	/**
	 * Count how many player have chosen a color.
	 */
	private int chosenColorCount;
	/**
	 * Count how many player have chosen a name.
	 */
	private int chosenNameCount;
	/**
	 * Hold the players' names.
	 */
	String[] names;
	/**
	 * Hold the players' pirates' color.
	 */
	PirateColor[] colors;
	
	Boolean[] areBots;
	
	Boolean[] areRemotes;

	private int chosenBotCount;

	private int chosenRemoteCount;
	
	//to pair with a selection of player amount in gui
	public GameBuilder(int playerCount_)
		throws PlayerAmountException{
			if(playerCount_ < 2 || playerCount_ > 7)
				throw new PlayerAmountException();
			else{
			playerCount = playerCount_;
			chosenBotCount = 0;
			chosenColorCount = 0;
			chosenNameCount = 0;
			chosenRemoteCount = 0;
			areBots = new Boolean[playerCount_];
			names = new String[playerCount_];
			areRemotes = new Boolean[playerCount_];
			if(playerCount_ > 3){
				colors = new PirateColor[playerCount_];
			}
			else{
				colors = new PirateColor[playerCount_*2];
			}
		}
	}
	

	//add a screen for each player to pick a name (and a color maybe if same screen)
	public boolean chooseName(String name){
		for(String s: names){
			if(name == s)
				return false;//name taken
		}
		try {
			names[chosenNameCount] = name;
			chosenNameCount++;
			return true; //success
			
		} catch (ArrayIndexOutOfBoundsException e) {
			e.getMessage();
			return false;
		}
	}
	
	public void chooseBot(Boolean isBot){
		areBots[chosenBotCount] = isBot;
		chosenBotCount++;
	}
	
	public void chooseRemote(Boolean isRemote){
		areRemotes[chosenRemoteCount] = isRemote;
		chosenRemoteCount++;
	}
	
	//add a screen for each player to pick a color (and a name maybe if same screen)
	//2 colors needed per player if it's a 3 or 2 player game.
	public boolean chooseColor(PirateColor color){
		for(PirateColor c: colors){
			if(c == color)
				return false;//color taken (might want to disable taken color in gui but w/e)
		}
		try {
			colors[chosenColorCount] = color;
			chosenColorCount++;
			return true; //success
			
		} catch (ArrayIndexOutOfBoundsException e) {
			e.getMessage();
			return false;
		}
	}
	/**
	 * Creates the player array needed to play a round of Croc! contains the cards, pirates and name of players
	 * @return The player array for the next round of Croc!
	 */
	public CrGame createGame(){
		int cardamount = 7;
		Player[] players = new Player[playerCount];
		if(playerCount == 3 || playerCount == 2){
			for(int i = 0; i < playerCount; i++){
				try {
					players[i] = new Player(cardamount,names[i],colors[i*2],colors[i*2+1],areBots[i],areRemotes[i]);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else{
			if(playerCount == 5)
				cardamount = 6;
			if(playerCount == 4)
				cardamount = 5;
			for(int i = 0; i < playerCount; i++){
				try {
					players[i] = new Player(cardamount,names[i],colors[i],areBots[i],areRemotes[i]);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		try {
			return new CrGame(players, chosenColorCount);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @return the playerCount
	 */
	public int getPlayerCount() {
		return playerCount;
	}


	/**
	 * @return the chosenColorCount
	 */
	public int getChosenColorCount() {
		return chosenColorCount;
	}


	/**
	 * @return the chosenNameCount
	 */
	public int getChosenNameCount() {
		return chosenNameCount;
	}
}
