package engine.controller;

import engine.exceptions.NotEveryoneChoseCardException;
import engine.models.Game;
import engine.models.Pirate;
import engine.models.Player;

public class GameSimulator {
	GameBuilder gb;
	public GameSimulator(GameBuilder gb_){
		gb = gb_;
	}
	
	//TODO: log everything to files if wanna go machine learning route.
	/**
	 * Used to play games between bots, make sure to have every player as a bot
	 * and with a bot profile.
	 * @return Gives the winning player.
	 */
	public Player PlayBotGame(){
		int round = 0;
		Boolean firstRound = true;
		Game g = gb.createGame();
		GameResolver gr = new GameResolver(g);
		gr.gameInit();
		//add pirateOrder to file.
		while(g.getWinner() == null){
			for(Pirate p : g.getPirateOrder()){
				p.botCardChooser();
				//add cards chosen to file
			}
			try {
				gr.roundResolve();
			} catch (NotEveryoneChoseCardException e) {
				System.out.println("How do bots even manage to not choose a card ?");
				e.printStackTrace();
			}
			if(!firstRound){
				gr.sharkTurn();
			}
			firstRound = false;
			gr.victoryCondition();
			System.out.println("round " + round++);
		}
		System.out.println("and the winner is: " + g.getWinner().name);
		return g.getWinner();
	}
	
}
