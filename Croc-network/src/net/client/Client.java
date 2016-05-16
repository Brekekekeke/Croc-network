package net.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import engine.models.PirateColor;
import net.headers.ComputeInterface;

/**
 * @author tristan
 * Is client main
 */
/**
 * @author tristan
 *
 */
public class Client {
	
	public static GameStateImpl gameState;
	
	/** Initialize callBack
	 * @throws RemoteException
	 */
	public static void init() throws RemoteException {
		gameState = new GameStateImpl();
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
	    	
//	    	comp.newGame(5);
	    	comp.joinGame(gameState, "a name", PirateColor.BLACK);
	    	
	    	gameState.setCardToPlay(6);
	    	
	    	comp.playCard(gameState);
	    	
//	    	comp.shutDown();
//	    	System.out.println("J'ai coupé le serveur");
	    	
	    } catch (Exception e) {
	    	System.err.println("ComputeCroc exception");
	    	e.printStackTrace();
	    }
	    System.out.println("~~~Fin client~~~");
	}
	
	public static ComputeInterface connect(String host, int serverPort) throws RemoteException, NotBoundException {
		String name = "Compute";
		System.out.println("bobo va connecter sur " + serverPort);
    	Registry registry = LocateRegistry.getRegistry(serverPort);
    	System.out.println("baba");
//    	Registry r = new Registry().connect("localhost", serverPort);
    	ComputeInterface comp = (ComputeInterface) registry.lookup(name);
    	System.out.println("bibi");
    	return comp;
	}

}
