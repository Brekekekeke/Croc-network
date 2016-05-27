package net.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import engine.controller.GameResolver;
import engine.exceptions.NotEveryoneChoseCardException;
import engine.models.CrGame;
import engine.models.Pirate;
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
	
	private int nbSlot = 0;
	private int nbPlayersCo = 0;
	private int nbBot = 0;
	private int nbCard;
	
	private Player players[];
//	private Player gamePlayers[]; 
	private long IDs[] = new long[maxPlayers];
	
	private CrGame game;
	private GameResolver gameResolver;
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

	public int getNbSlot() {
		return nbSlot;
	}

	public void setNbSlot(int nbSlot) {
		this.nbSlot = nbSlot;
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
		this.nbCard = getNbSlot();
	}

	public Player[] getPlayers() {
//		RemotePlayer result[] = new RemotePlayer[nbPlayers];
//		for (int i = 0; i < nbPlayers; i++) {
//			System.out.println("in for");
//			if (players[i] != null) {
//				try {
//					System.out.println("Ok try");
//					result[i] = new Player(nbCard, players[i].getName(), players[i].getColor(), players[i].isBot());
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		System.out.println("getPlayers" + players.toString());
		return players;
//		System.out.println("fin for");
//		return result;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

//	private long getID(int indice) {
//		return IDs[indice];
//	}
	
	private void setID(int indice, long id) {
		System.out.println("indice " + indice + " id " + id);
		IDs[indice] = id;
	}
	
	public CrGame getGame() {
		return game;
	}

	public void setGame(CrGame game) {
		this.game = game;
	}

	public ServerStep getMyStep() {
		return myStep;
	}

	public void setMyStep(ServerStep myStep) {
		this.myStep = myStep;
	}

	
//	 Internal methods
	
	private int getIdIndice(long id) {
		
//		System.out.println(nbSlot + "wanted " + id);
		for (int i = 0; i < nbSlot; i++) {
//			System.out.println(IDs[i]);
			if (((Long)IDs[i]).equals(id)) {
				return i;
			}
		}
		System.out.println("ComputeEngine.GetIdIndice bug");
		return -1;
	}
	
	private boolean canPlay(long id, int cardToPlay) {
		int i = getIdIndice(id);
		return players[i].pirates[0].cards[cardToPlay].isInHand();
	}
	
	private void startGame() {
		setMyStep(ServerStep.PROCESSING);
		configureGame();
		gameResolver = new GameResolver(game);
		gameResolver.gameInit();
		resolveRound();
	}
	
	private boolean alreadyUsedColor(PirateColor expectedColor) {
		for (int i = 0; i < (nbPlayersCo + nbBot); i++) {
//			try {
//				if (players[i].getColor() == expectedColor) {
//					return true;
//				}
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}

	private boolean alreadyUsedName(String expectedName) {
		for (int i = 0; i < (nbPlayersCo + nbBot); i++) {
//			try {
//				if (players[i].getName() == expectedName) {
//					return true;
//				}
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}
	
	private void configureGame() {
		setMyStep(ServerStep.PROCESSING);
		System.out.println("CreateGame " + getNbSlot());
		try {
			game = new CrGame(getPlayers(), 1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("CreateD Game");
	}
	
	private void resolveRound() {
		while (getMyStep() == ServerStep.WAITCARD) {
//			Attente active, pas beau!
		}
//		setMyStep(ServerStep.PROCESSING);
		for(Player p : game.getPlayers()){
			if(p.isBot){
//				System.out.println("Bot Found");
				p.pirates[0].botCardChooser();
			}
		}
		try {
			gameResolver.roundResolve();
			for (int i = 0; i < nbSlot; i++) {
//				try {
//					players[i].getPirate()[0].setlastPlayedCard(players[i].getPirate()[0].getWannaPlay());
//					players[i].getPirate()[0].lastPlayedCardRead();
//				} catch (RemoteException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			
		} catch (NotEveryoneChoseCardException e) {
			e.printStackTrace();
		}
		setMyStep(ServerStep.WAITCARD);
	}
	
	private boolean isReadyToStart() {
		if (getMyStep() == ServerStep.WAITCO && hasEnoughPlayers()) {
			return true;
		}
		return false;	
	}
	
	private boolean hasEnoughPlayers() {
		if (nbPlayersCo + nbBot != nbSlot) {
			return false;
		}
		for (int i = 0; i < (nbPlayersCo + nbBot); i++) {
			if (players[i] == null) {
				return false;
			}
		}
		return true;
	}

	private long generateID() {
		//TODO Make it unique
		   Random random = new Random();
		   long value = random.nextLong();
		   return value;
	}

	


	
//	 Implementation of ComputeInterface
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#newGame(net.headers.GameStateInterface, int)
	 */
	@Override
	public GameStateInterface newGame(GameStateInterface client, int nbSlot) {
		System.out.println("Demande d'ouverture de partie pour " + nbSlot + " joueurs");
		players = new Player[nbSlot];
		if (getMyStep() == ServerStep.CLOSED) {
			System.out.println("Ouverture d'une partie à " + nbSlot + " joueurs.");
//			gamePlayers = new Player[nbPlayers];
			setNbSlot(nbSlot);
			setNbCard();
			setMyStep(ServerStep.WAITCO);
			try {
				client.setStep(getMyStep());
				client.setError(ServerError.NOERROR);
				client.setNbPlayers(getNbSlot());
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
	public long joinGame(GameStateInterface client, String expectedName, PirateColor expectedColor) {
		System.out.println(expectedName + " demande a rejoindre");
		
		long id = generateID();
		
		if (getMyStep() == ServerStep.CLOSED) {
			// La partie est fermée
			System.out.println("Joueur : Pas de partie dispo");
			try {
				client.setError(ServerError.NOAVAILABLEGAME);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (nbBot + nbPlayersCo == nbSlot) {
			// La partie est complete
			System.out.println("Joueur : Toutes les places sont prises");
			setMyStep(ServerStep.WAITCARD);
			try {
				client.setError(ServerError.GAMEISFULL);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (alreadyUsedName(expectedName)) {
			//Le nom est pris
			System.out.println("Joueur : Ce nom est déjà pris");
			try {
				client.setError(ServerError.INVALIDNAME);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (alreadyUsedColor(expectedColor)) {
			// La couleur est prise
			System.out.println("Joueur : Cette couleur est prise");
			try {
				client.setError(ServerError.INVALIDCOLOR);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			// Ok pour rejoindre
			System.out.println("Joueur : Ok pour rejoindre");
			Player p = null;
			try {
				p = new Player(getNbCard(), expectedName, expectedColor, false);
				client.setClientName(expectedName);
				client.setClientColor(expectedColor);
				client.setClientId(id);
				client.setError(ServerError.NOERROR);
//				p = new RemotePlayerImpl (getNbCard(), expectedName, expectedColor, false);
				
				//TODO remove DEBUG >
//				p.getPirate()[0].setHand(6, true);
				// </debug
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < nbSlot; i++) {
				if (players[i] == null) {
					players[i] = p;
//					gamePlayers[i] = gp;
					setID(nbPlayersCo, id);
					nbPlayersCo += 1;
					break;
				}
			}
		}
//		if (nbBot + nbPlayersCo == nbPlayers) {
//			startGame();
//		}
		try {
			client.setNbBot(nbBot);
			client.setNbPlayersCo(nbPlayersCo);
//			client.setPlayers(getPlayers());
			client.setStep(getMyStep());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("On est " + (nbPlayersCo + nbBot) + " joueurs sur " + nbSlot);
		System.out.println("End of join game");
		return id;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#addBotPlayer(net.headers.GameStateInterface, engine.models.PirateColor)
	 */
	@Override
	public GameStateInterface addBotPlayer(GameStateInterface client, PirateColor expectedColor) {
		if (getMyStep() == ServerStep.CLOSED) {
			// La partie est fermée
			System.out.println("Bot : Pas de partie dispo");
			try {
				client.setError(ServerError.NOAVAILABLEGAME);
				client.setStep(myStep);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (alreadyUsedColor(expectedColor)) {
			System.out.println("Bot : Cette couleur est utilisée");
			try {
				client.setError(ServerError.INVALIDCOLOR);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (nbBot + nbPlayersCo == nbSlot) {
			System.out.println("Bot : Toutes les places sont prises");
			try {
				client.setError(ServerError.GAMEISFULL);
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		} else {
			System.out.println("Ajout d'un bot");

//			long id = generateID();
			
			Player bot = null;
			try {
				Player gp = new Player(getNbCard(), "IAMBOT " + getNbBot(), expectedColor, true);
			} catch (RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				bot = new Player(getNbCard(), "Bot " + nbBot, expectedColor, true);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			for (int i = 0; i < nbSlot; i++) {
				if (players[i] == null) {
					System.out.println("Bot Ajouté");
					players[i] = bot;
//					gamePlayers[i] = gp;
					setID(i, -1);
					nbBot += 1;
					try {
						client.setError(ServerError.NOERROR);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				}
			}
//			try {
//				client.setPlayers(getPlayers());
//				client.setNbPlayersCo(nbPlayersCo);
//				client.setNbBot(nbBot);
//				client.setStep(getMyStep());
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
		}
		System.out.println("On est " + (nbPlayersCo + nbBot) + " joueurs sur " + nbSlot);
		System.out.println("End of add bot");
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#startGame(net.headers.GameStateInterface)
	 */
	@Override
	public GameStateInterface startGame(GameStateInterface client) {
		if (isReadyToStart()) {
			setMyStep(ServerStep.READY);
			System.out.println("Ok pour start");
			startGame();
//			while (gameResolver.getGame().getWinner() == null) {
//				resolveRound();			
//			}
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
	public GameStateInterface playCard(long id, GameStateInterface client) {
		int wannaPlay = -1;
		long idClient = id;
		String clientName = "";
		try {
			wannaPlay = client.getGardToPlay();
			clientName = players[getIdIndice(idClient)].name;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.out.println("Le client " + clientName + " demande à jouer la carte " + wannaPlay);
		
		if (getMyStep() == ServerStep.PROCESSING) {
			System.out.println("Tour en cours de résolution");
			try {
				client.setError(ServerError.NOTYOURTURN);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		else if (canPlay(idClient, wannaPlay)) {
			try {
				client.setLastPlayedCard(client.getGardToPlay());
				players[getIdIndice(idClient)].pirates[0].setlastPlayedCard(wannaPlay);
				System.out.println("Ok pour jouer la carte" + client.getGardToPlay());
				client.setError(ServerError.NOERROR);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			//TODO enregistrer l'info;
			
		} else {
			System.out.println("Pas possible de jouer la carte " + wannaPlay + " pour le joueur " + clientName);
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
		boolean ready = true;
		for (int i = 0; i < nbSlot; i++) {
//			try {
//				if (players[i] == null || players[i].getPirate()[0].getWannaPlay() == 10 || players[i].getPirate()[0].getWannaPlay() == -1) {
//					ready = false;
//				}
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		if (ready) {
			setMyStep(ServerStep.READY);
		}
		return client;
	}
	
	/* (non-Javadoc)
	 * @see net.headers.ComputeInterface#updateMe(net.headers.GameStateInterface)
	 */
	public GameStateInterface updateMe(GameStateInterface client) {
//		try {
////			client.setClientId(id);
////			client.setClientName(name);
////			client.setClientColor(color);
//			client.setStep(getMyStep());
//			client.setError(ServerError.NOERROR);
//			client.setNbPlayers(getNbSlot());
//			client.setNbCards(getNbCard());
//			client.setPlayers(getPlayers());
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
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
