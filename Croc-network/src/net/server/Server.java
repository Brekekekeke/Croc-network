package net.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import engine.controller.GameBuilder;
import engine.controller.GameResolver;
import engine.exceptions.NotEveryoneChoseCardException;
import engine.exceptions.PlayerAmountException;
import engine.models.CrGame;
import engine.models.Pirate;
import engine.models.PirateColor;
import engine.models.Player;
import net.headers.CrGameInterface;
import net.headers.ServerInterface;

public class Server implements ServerInterface {
	GameBuilder gb;
	int nbPlayers;
	CrGame croc;
	GameResolver gr;
	Integer nextPlayer = null;
	boolean turnBegin;
	int playedCount;
	Boolean firstRound;
	int selectCount;
	Boolean sharkTurn;
	
	public Server(int nbPlayers) {
//		croc = cg;
		this.nbPlayers = nbPlayers;
		
	    try {
	    	String name = "server";
	    	ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, 1099);
	    	Registry registry = LocateRegistry.createRegistry(1099); // default local 1099
	    	registry.rebind(name, stub);
	    } catch (Exception e) {
	    	System.err.println("ComputeEngine exception");
	    	e.printStackTrace();
	    }
	}
	
	public Server(int nbPlayers, int numPort) throws PlayerAmountException {
		gb = new GameBuilder(nbPlayers);
		this.nbPlayers = nbPlayers;
	    try {
	    	String name = "server";
	    	ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this, numPort);
	    	Registry registry = LocateRegistry.createRegistry(numPort); // default local 1099
	    	registry.rebind(name, stub);
	    } catch (Exception e) {
	    	System.err.println("ComputeEngine exception");
	    	e.printStackTrace();
	    }
	}
	
	public void addPlayer(String name, boolean isBot, PirateColor color) {
		gb.chooseName(name);
		gb.chooseBot(isBot);
		gb.chooseColor(color);
	}
	
	public CrGameInterface createGame() {
		try {
			gb = new GameBuilder(nbPlayers);
		} catch (PlayerAmountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		croc =  gb.createGame();
		return (CrGameInterface) croc;
	}
	
	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		gr.gameInit();
	}
	
	@Override
	public void resolveTurn() {
		if(playedCount == croc.getPirateOrder().size()){
			try {
				gr.roundResolve();
//				batch.begin();
//				batch.setColor(Color.WHITE);
//				drawBgandProps();
//				DrawPlayedCards();
//				DrawPlayerAvailableCards();
//				DrawPirates();
//				batch.end();
			} catch (NotEveryoneChoseCardException e) {
			}
			if(firstRound){
				firstRound = false;
				playedCount = 0;
				for(Pirate p : croc.getPirateOrder()){
					if(p.owner.isBot){
						p.botCardChooser();
						playedCount++;
					}
				}
			}
			else{
				sharkTurn = true;
			}
		}
		if(sharkTurn && !firstRound){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gr.sharkTurn();
			
			playedCount = 0;
			gr.victoryCondition();
			for(Player p: croc.getPlayers())
				gr.hasLost(p);
			for(Pirate p: croc.getPirateOrder())
				p.hasPlayed = false;
			for(Pirate p : croc.getPirateOrder()){
				if(p.owner.isBot){
					p.botCardChooser();
					playedCount++;
				}
			}
			sharkTurn = false;
		}
		
	}
	
	
}
