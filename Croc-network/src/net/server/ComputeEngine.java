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
import net.headers.ServerError;
import net.headers.ServerStep;

/**
 * @author CrocTeam
 * Servers implementation of ComputeInterface.
 * Contains all methods run by the server including creating and resolving game. 
 */
public class ComputeEngine implements ComputeInterface {

	private static int maxPlayers = 7;
	private int nbPlayers = 0;
	private int nbPlayersCo = 0;
	private int nbBot = 0;
	private int nbCard;
	private Player players[] = new Player[maxPlayers];
	private long IDs[] = new long[maxPlayers];
	private Game game;
	private ServerStep myStep;
	
	/**
	 * Constructor
	 */
	public ComputeEngine() {
		super();
		myStep = ServerStep.CLOSED;
	}
	
//	Getters, Setters
	
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

	private long getID(int indice) {
		return IDs[indice];
	}
	
	private void setID(int indice, long id) {
		IDs[indice] = id;
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

	
//	 Internal methods
	
	private boolean canPlay(int cardToPlay) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void startGame() {
		
	}
	
	private boolean alreadyUsedColor(PirateColor expectedColor) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean alreadyUsedName(String expectedName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void configureGame() {
		game = new Game(players, nbPlayers);
	}
	
	private boolean isReadyToStart() {
		if (getMyStep() == ServerStep.WAITCO && hasEnoughPlayers()) {
			return true;
		}
		return false;	
	}
	
	private boolean hasEnoughPlayers() {
		for (int i = 0; i < (nbPlayersCo + nbBot); i++) {
			if (players[i] == null) {
				return false;
			}
		}
		return true;
	}

	private long generateID() {
		//TODO at least random unique id
		return 1;
	}

	


	
//	 Implementation of ComputeInterface
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#newGame(net.headers.GameStateInterface, int)
	 */
	@Override
	public GameStateInterface newGame(GameStateInterface client, int nbPlayers) {
		System.out.println("Demande d'ouverture de partie pour " + nbPlayers + " joueurs");
		if (getMyStep() == ServerStep.CLOSED) {
			System.out.println("Ouverture d'une partie à " + nbPlayers + " joueurs.");
			setNbPlayers(nbPlayers);
			setNbCard();
			setMyStep(ServerStep.WAITCO);
			try {
				client.setStep(getMyStep());
				client.setError(ServerError.NOERROR);
				client.setNbPlayers(getNbPlayers());
				client.setNbCards(getNbCard());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Impossible de créer une partie.");
			try {
				client.setStep(getMyStep());
				client.setError(ServerError.NOAVAILABLEGAME);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#joinGame(net.headers.GameStateInterface, java.lang.String, engine.models.PirateColor)
	 */
	@Override
	public GameStateInterface joinGame(GameStateInterface client, String expectedName, PirateColor expectedColor) {
		System.out.println(expectedName + " demande a rejoindre");
		
		long id = generateID();
		
		if (getMyStep() == ServerStep.CLOSED) {
			// La partie est fermée
			System.out.println("Pas de partie dispo");
			try {
				client.setError(ServerError.NOAVAILABLEGAME);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		if (myStep != ServerStep.WAITCO) {
			// La partie est complete
			System.out.println("La partie est pleine");
			try {
				client.setError(ServerError.GAMEISFULL);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		if (nbBot + nbPlayersCo == nbPlayers) {
			// La partie est aussi complete
			// TODO est-ce vraiment utile? A deplacer?
			System.out.println("Toutes les places sont prises");
			try {
				client.setError(ServerError.GAMEISFULL);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (alreadyUsedName(expectedName)) {
			//Le nom est pris
			System.out.println("Ce nom est déjà pris");
			try {
				client.setError(ServerError.INVALIDNAME);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (alreadyUsedColor(expectedColor)) {
			// La couleur est prise
			System.out.println("Cette couleur est prise");
			try {
				client.setError(ServerError.INVALIDCOLOR);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			// Ok pour rejoindre
			System.out.println("Ok pour rejoindre");
			Player p = null;
			try {
				client.setClientName(expectedName);
				client.setClientColor(expectedColor);
				client.setClientId(id);
				client.setError(ServerError.NOERROR);
				p = new Player (getNbCard(), expectedName, expectedColor, false);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < nbPlayers; i++) {
				if (players[i] == null) {
					players[i] = p;
					setID(i, id);
					nbPlayersCo += 1;
					System.out.println("On est " + nbPlayersCo + " joueurs sur " + nbPlayers);
					break;
				}
			}
		}
//		if (nbBot + nbPlayersCo == nbPlayers) {
//			startGame();
//		}
		try {
			System.out.println("client update");
			//TODO handle an error about non serializable Player
			client.setPlayers(getPlayers());
			System.out.println("client update 2");
			client.setStep(getMyStep());
			System.out.println("client update");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("End of join game");
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#addBotPlayer(net.headers.GameStateInterface, engine.models.PirateColor)
	 */
	@Override
	public GameStateInterface addBotPlayer(GameStateInterface client, PirateColor expectedColor) {
		if (nbBot + nbPlayersCo == nbPlayers) {
			System.out.println("Toutes les places sont prises");
			try {
				client.setError(ServerError.GAMEISFULL);
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		}
		Player p = new Player (getNbCard(), "Bot", PirateColor.BLACK, true);
		for (int i = 0; i < nbPlayers; i++) {
			if (players[i] == null) {
				players[i] = p;
				nbBot += 1;
				if (nbBot + nbPlayersCo == nbPlayers) {
					System.out.println("Ok pour ce bot");
					try {
						client.setError(ServerError.NOERROR);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			client.setStep(getMyStep());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#startGame(net.headers.GameStateInterface)
	 */
	@Override
	public GameStateInterface startGame(GameStateInterface client) {
		if (isReadyToStart()) {
			setMyStep(ServerStep.READY);
			configureGame();
		} else {
			try {
				client.setError(ServerError.NOTREADY);
				client.setStep(getMyStep());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#getServerStep(net.headers.GameStateInterface)
	 */
	@Override
	public GameStateInterface getServerStep(GameStateInterface client) {
		// TODO Auto-generated method stub
		try {
			client.setStep(myStep);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#playCard(net.headers.GameStateInterface)
	 */
	@Override
	public GameStateInterface playCard(GameStateInterface client) {
		int wannaPlay = -1;
		try {
			System.out.println("Wannaplay");
			wannaPlay = client.getGardToPlay();
			System.out.println("WannaplayED");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Le client demande à jouer la carte " + wannaPlay);
		if (getMyStep() == ServerStep.PROCESSING) {
			System.out.println("Tour en cours de résolution");
			try {
				client.setError(ServerError.NOTYOURTURN);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		else if (canPlay(wannaPlay)) {
			try {
				client.setLastPlayedCard(client.getGardToPlay());
				client.setError(ServerError.NOERROR);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			//TODO enregistrer l'info;
			
		} else {
			System.out.println("Pas possible de jouer cette carte");
			try {
				client.setError(ServerError.INVALIDCARD);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		try {
			client.setStep(getMyStep());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#updateMe(net.headers.GameStateInterface)
	 */
	public GameStateInterface updateMe(GameStateInterface client) {
		try {
//			client.setClientId(id);
//			client.setClientName(name);
//			client.setClientColor(color);
			client.setStep(getMyStep());
			client.setError(ServerError.NOERROR);
			client.setNbPlayers(getNbPlayers());
			client.setNbCards(getNbCard());
			client.setPlayers(getPlayers());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return client;
	}
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#quitGame(net.headers.GameStateInterface)
	 */
	@Override
	public GameStateInterface quitGame(GameStateInterface client) {
		// TODO Auto-generated method stub
		return client;
	}

	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#shutDown()
	 */
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
	    	
	    	ComputeInterface stub = (ComputeInterface) UnicastRemoteObject.exportObject(engine, numPort);
	    	
	    	Registry registry = LocateRegistry.createRegistry(numPort); // default local 1099
	    	
	    	registry.rebind(name, stub);
	    	
	    } catch (Exception e) {
	    	System.err.println("ComputeEngine exception");
	    	e.printStackTrace();
	    }
	}
}
