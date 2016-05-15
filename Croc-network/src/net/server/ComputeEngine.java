package net.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import net.headers.GameStateInterface;
import net.headers.ComputeInterface;

public class ComputeEngine implements ComputeInterface {

	private int nbPlayers = 0;
	/** Constructor
	 * 
	 */
	public ComputeEngine() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see neXt.compute.Compute#playCard(neXt.compute.CallBack)
	 */
	@Override
	public void playCard(GameStateInterface client) throws RemoteException{
		try {
			System.out.println("Le client demande à jouer la carte " + client.getWannaPlay());
			client.setCard(client.getWannaPlay(), false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see neXt.compute.Compute#shutDown()
	 */
	public void shutDown() {
		System.out.println("On m'a demandé d'arreter !");
		System.exit(0);;
	}
	
	public void newGame(int nbPlayers) {
		System.out.println("Ouverture d'une partie à " + nbPlayers + " joueurs.");
	}
	
	public GameStateInterface joinGame(GameStateInterface client) {
		nbPlayers+=1;
		System.out.println("On est " + nbPlayers + " joueurs");
		return client;
	}
	
	/** Open server and wait for instructions
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("launched ComputeEngine");
//	    if (System.getSecurityManager() == null) {
//	      System.setSecurityManager(new SecurityManager());
//	    }
	    try {
	    	int numPort = Integer.parseInt(args[0]);
	    	String name = "Compute";
	    	ComputeInterface engine = new ComputeEngine();
	    	ComputeInterface stub = (ComputeInterface) UnicastRemoteObject.exportObject(engine, numPort);
	    	Registry registry = LocateRegistry.createRegistry(numPort); // default local 1099
	    	registry.rebind(name, stub);
	    } catch (Exception e) {
	    	System.err.println("ComputeEngine exception");
	    	e.printStackTrace();
	    }
	}
}
