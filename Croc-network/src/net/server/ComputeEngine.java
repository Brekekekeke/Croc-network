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
import net.headers.ServerStep;

public class ComputeEngine implements ComputeInterface {

	private static int maxPlayers = 7;
	private int nbPlayers = 0;
	private int nbPlayersCo = 0;
	private int nbBot = 0;
	private int nbCard;
	private Player players[] = new Player[maxPlayers];
	private Game game;
	private ServerStep myStep;
	
	/**
	 * Constructor
	 */
	public ComputeEngine() {
		super();
		myStep = ServerStep.CLOSED;
	}
	
	/**
	 * Getters, Setters
	 */
	public static int getMaxPlayers() {
		return maxPlayers;
	}

	public static void setMaxPlayers(int maxPlayers) {
		ComputeEngine.maxPlayers = maxPlayers;
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public int getNbPlayersCo() {
		return nbPlayersCo;
	}

	public void setNbPlayersCo(int nbPlayersCo) {
		this.nbPlayersCo = nbPlayersCo;
	}

	public int getNbBot() {
		return nbBot;
	}

	public void setNbBot(int nbBot) {
		this.nbBot = nbBot;
	}

	public int getNbCard() {
		return nbCard;
	}


	/** Define how many cards has each player depending on nbPlayers
	 *  TODO implement true rules
	 */
	public void setNbCard() {
		this.nbCard = getNbPlayers();
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ServerStep getMyStep() {
		return myStep;
	}

	public void setMyStep(ServerStep myStep) {
		this.myStep = myStep;
	}

	
	// Internal methods
	
	private boolean canPlay(int gardToPlay) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void configureGame() {
		game = new Game(players, nbPlayers);
	}
	
	private boolean alreadyUsedColor(PirateColor expectedColor) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean alreadyUsedName(String expectedName) {
		// TODO Auto-generated method stub
		return false;
	}



	


	
	// Implementation of ComputeInterface
	
	@Override
	public GameStateInterface newGame(GameStateInterface client, int nbPlayers) {
		System.out.println("Ouverture d'une partie à " + nbPlayers + " joueurs.");
		setNbPlayers(nbPlayers);
		setNbCard();
		myStep = ServerStep.WAITCO;
		try {
			client.setStep(getMyStep());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}
	
	@Override
	public GameStateInterface joinGame(GameStateInterface client, String expectedName, PirateColor expectedColor) {
		if (myStep != ServerStep.WAITCO) {
			System.out.println("Pas de partie dispo");
			try {
				client.setStep(myStep);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return client;
		}
		if (nbBot + nbPlayersCo == nbPlayers) {
			System.out.println("Toutes les places sont prises");
		} else if (alreadyUsedName(expectedName)) {
			System.out.println("Ce nom est déjà pris");
		} else if (alreadyUsedColor(expectedColor)) {
			System.out.println("Cette couleur est prise");
		} else {
			Player p = null;
			try {
				p = new Player (getNbCard(), client.getClientName(), client.getClientColor(), false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < nbPlayers; i++) {
				if (players[i] == null) {
					players[i] = p;
					nbPlayersCo += 1;
					System.out.println("On est " + nbPlayersCo + " joueurs sur " + nbPlayers);
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
		// TODO Update le client
		return client;
	}
	
	@Override
	public GameStateInterface addBotPlayer(GameStateInterface client, PirateColor expectedColor) {
		if (nbBot + nbPlayersCo == nbPlayers) {
			System.out.println("Toutes les places sont prises");
			return client;
		}
//		int cardAmount, String name_, PirateColor color, Boolean isBot_
		Player p = new Player (getNbCard(), "Bot", PirateColor.BLACK, true);
		for (int i = 0; i < nbPlayers; i++) {
			if (players[i] == null) {
				players[i] = p;
				nbBot += 1;
				if (nbBot + nbPlayersCo == nbPlayers) {
					System.out.println("Ok pour ce bot");
//					configureGame();
				}
			}
		}
		return client;
	}
	
	@Override
	public GameStateInterface getServerStep(GameStateInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		client.setStep(myStep);
		return client;
	}
	
	@Override
	public boolean playCard(GameStateInterface client) throws RemoteException{
		try {
			System.out.println("Le client demande à jouer la carte " + client.getGardToPlay());
			if (canPlay(client.getGardToPlay())) {
				client.setLastPlayedCard(client.getGardToPlay());
				//TODO enregistrer l'info;
				return true;
			} else {
				System.out.println("Pas possible de jouer cette carte");
				return false;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public GameStateInterface quitGame(GameStateInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		return client;
	}

	@Override
	public void shutDown() {
		System.out.println("On m'a demandé d'arreter !");
		System.exit(0);
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
	    	System.out.println("BA");
	    	ComputeInterface stub = (ComputeInterface) UnicastRemoteObject.exportObject(engine, numPort);
	    	System.out.println("BE");
	    	Registry registry = LocateRegistry.createRegistry(numPort); // default local 1099
	    	System.out.println("BI");
	    	registry.rebind(name, stub);
	    	System.out.println("BO");
	    } catch (Exception e) {
	    	System.err.println("ComputeEngine exception");
	    	e.printStackTrace();
	    }
	}
}
