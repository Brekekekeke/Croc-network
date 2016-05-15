package net.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
	
	public static GameStateImpl me;
	
	/** Initialize callBack
	 * @throws RemoteException
	 */
	public static void initMe() throws RemoteException {
		me = new GameStateImpl();
	}
	
	/** Connect to server, manipulate myself and asking things to the server.
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
//	    if (System.getSecurityManager() == null) {
//	      System.setSecurityManager(new SecurityManager());
//	    }
		
		int serverPort = 1349;

		initMe();
		
		System.out.println("Starting ComputeTruc");
	    try {
	    	String name = "Compute";
	    	Registry registry = LocateRegistry.getRegistry(serverPort);
//	    	Registry r = new Registry().connect("localhost", serverPort);
	    	ComputeInterface comp = (ComputeInterface) registry.lookup(name);
	    	me.setWannaPlay(5);
	    	System.out.println("Je veux jouer la carte " + me.getWannaPlay() + " et elle est à " + me.getThisCardState(me.getWannaPlay()));
	    	comp.joinGame(me);
	    	comp.playCard(me);
	    	System.out.println("Je peux désormais utiliser le " + me.getWannaPlay() + " : " + me.getThisCardState(me.getWannaPlay()));
//	    	comp.shutDown();
//	    	System.out.println("J'ai coupé le serveur");
	    	
	    } catch (Exception e) {
	    	System.err.println("ComputeCroc exception");
	    	e.printStackTrace();
	    }
	}

}
