package net.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import engine.models.PirateColor;
import net.headers.ComputeInterface;

/**
 * @author CrocTeam
 * Is client main. Will be used to create server if needed and connect to it.
 * Use ComputeInterface methods to interact with server.
 * Use GameStateImpl to know about the game.
 */
public class Client {
	
	public static GameStateImpl gameState;
	
	/** Initialize callBack
	 * @throws RemoteException
	 */
	public static void init() throws RemoteException {
		gameState = new GameStateImpl();
	}
	
	
	public static void testUn(ComputeInterface comp) throws RemoteException {
    	comp.newGame(gameState, 3);
    	
    	long myID = comp.joinGame(gameState, "LOLO", PirateColor.BLACK);
    	comp.addBotPlayer(gameState, PirateColor.GREEN);
    	
    	gameState.setCardToPlay(1);
    	
    	comp.playCard(myID, gameState);
    	
    	comp.startGame(gameState);
    	System.out.println("Joueurs 0 :" + gameState.getPlayers()[0].getName());
    	System.out.println("Dernier joueur "+ (gameState.getNbPlayerCo() + gameState.getNbBot() - 1) +" :" + gameState.getPlayers()[gameState.getNbPlayerCo() + gameState.getNbBot() - 1].getName());
//    	comp.shutDown();
//    	System.out.println("J'ai coupé le serveur");
	}
	public static void testDeux(ComputeInterface comp) throws RemoteException {
    	comp.newGame(gameState, 3);
    	
    	long myID = comp.joinGame(gameState, "LALA", PirateColor.ORANGE);
    	comp.addBotPlayer(gameState, PirateColor.GREEN);
    	comp.addBotPlayer(gameState, PirateColor.PURPLE);
    	System.out.println("setCardToPlay");
    	gameState.setCardToPlay(1);
    	System.out.println("startGame");
    	comp.startGame(gameState);
    	System.out.println("playCard");
    	comp.playCard(myID, gameState);
    	
    	System.out.println("Joueurs 0 :" + gameState.getPlayers()[0].getName());
    	System.out.println("Dernier joueur "+ (gameState.getNbPlayerCo() + gameState.getNbBot() - 1) +" :" + gameState.getPlayers()[gameState.getNbPlayerCo() + gameState.getNbBot() - 1].getName());
//    	comp.shutDown();
//    	System.out.println("J'ai coupé le serveur");
	}
	/** Connect to server, manipulate myself and asking things to the server.
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
//	    if (System.getSecurityManager() == null) {
//	      System.setSecurityManager(new SecurityManager());
//	    }
		System.out.println("---Starting Client---");
		
		int serverPort = Integer.parseInt(args[0]);

		init();
	    try {
	    	ComputeInterface comp = connect("", serverPort);
	    	
//	    	testUn(comp);
	    	testDeux(comp);
	    	
	    	
	    } catch (Exception e) {
	    	System.err.println("ComputeCroc exception");
	    	e.printStackTrace();
	    }
	    System.out.println("~~~Fin client~~~");
	}
	
	public static ComputeInterface connect(String host, int serverPort) throws RemoteException, NotBoundException {
		String name = "Compute";
//		System.out.println("bobo va connecter sur " + serverPort);
    	Registry registry = LocateRegistry.getRegistry(serverPort);
//    	System.out.println("baba");
//    	Registry r = new Registry().connect("localhost", serverPort);
    	ComputeInterface comp = (ComputeInterface) registry.lookup(name);
//    	System.out.println("bibi");
    	return comp;
	}

}
