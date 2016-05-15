package net.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import engine.models.Game;
import engine.models.PirateColor;
import engine.models.Player;
import net.headers.ComputeInterface;
import net.headers.GameStateInterface;

public class ComputeEngine implements ComputeInterface {

	private static int maxPlayers = 7;
	private int nbPlayers = 0;
	private int nbPlayersCo = 0;
	private int nbBot = 0;
	private int nbCard;
	private Player players[] = new Player[maxPlayers];
	private Game game;
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
		setNbPlayers(nbPlayers);
		setNbCard();
	}
	
	public void configureGame() {
		game = new Game(players, nbPlayers);
	}
	
	public boolean addBotPlayer() {
		if (nbBot + nbPlayersCo == nbPlayers) {
			System.out.println("Toutes les places sont prises");
			return false;
		}
//		int cardAmount, String name_, PirateColor color, Boolean isBot_
		Player p = new Player (getNbCard(), "Bot", PirateColor.BLACK, true);
		for (int i = 0; i < nbPlayers; i++) {
			if (players[i] == null) {
				players[i] = p;
				nbBot += 1;
				if (nbBot + nbPlayersCo == nbPlayers) {
					configureGame();
				}
				return true;
			}
		}
		return false;
	}
	
	public GameStateInterface joinGame(GameStateInterface client) throws RemoteException {
		if (nbBot + nbPlayersCo == nbPlayers) {
			System.out.println("Toutes les places sont prises");
		} else {
	//		int cardAmount, String name_, PirateColor color, Boolean isBot_
			Player p = new Player (getNbCard(), client.getClientName(), client.getClientColor(), false);
			for (int i = 0; i < nbPlayers; i++) {
				if (players[i] == null) {
					players[i] = p;
					nbPlayersCo += 1;
					System.out.println("On est " + nbPlayersCo + " joueurs sur " + nbPlayers);
					client.update();
					break;
				}
			}
		}
//		System.out.println("Waiting players");
//		while(nbBot + nbPlayersCo < nbPlayers) {
////			sleep(10);
//		}
		if (nbBot + nbPlayersCo == nbPlayers) {
			configureGame();
		}
		client.update();
		return client;
	}
	
	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}
	
	public int getNbCard() {
		return nbCard;
	}

	/** Define how many cards has each player depending on nbPlayers
	 *  TODO implement true rules
	 */
	private void setNbCard() {
		this.nbCard = nbPlayers;
	}
	
	/** Open server and wait for instructions
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("---Launched ComputeEngine---");
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
