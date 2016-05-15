package launch;

import java.rmi.RemoteException;

import engine.controller.GameBuilder;
import engine.controller.GameSimulator;
import engine.exceptions.PlayerAmountException;
import engine.models.PirateColor;


public class Exec {

	public static void main ( String[] args) throws PlayerAmountException, RemoteException {
		//generateBotGame();
		//netServerTestingB();
		System.out.println("On lance le main frere");
		
	}
	
//	public static void netServerTestingB() {
//		System.out.println("Salut");
//		int numPort = 1304;
//		try {
//			System.out.println("Salut0");
//			GameServer skeleton = new GameServerImpl();
//			System.out.println("Salut1");
//			Registry registry = LocateRegistry.createRegistry(numPort);
//			System.out.println("Salut2");
//			registry.rebind("monNom", skeleton);
//			System.out.println("ca passe");
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Bye");
//	}
	
//	public static void netServerTestingA() {
//		System.out.println("Salut");
//		// TODO Auto-generated method stub
//		try {
//			LocateRegistry.createRegistry(1302); //numéro de port
//			
//			GameServerImpl myServerSide = new GameServerImpl();
//			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/protojeu";
//			System.out.println("Enregistrement de l'objet avec l'url : " + url);
//			Naming.rebind(url, myServerSide);
//			System.out.println("ca passe");
//		} catch (RemoteException e) {
//			System.err.println("REMOTE main.java");
//			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			System.err.println("UNKNOWNHOST main.java");
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			System.err.println("MALFORMEDURL main.java");
//			e.printStackTrace();
//		}
//		System.out.println("Bye");
//	}
	
	public static void generateBotGame() throws PlayerAmountException{
		
		GameBuilder gb = new GameBuilder(7);
		gb.chooseColor(PirateColor.WHITE);
		gb.chooseColor(PirateColor.BLACK);
		gb.chooseColor(PirateColor.GREEN);
		gb.chooseColor(PirateColor.ORANGE);
		gb.chooseColor(PirateColor.PURPLE);
		gb.chooseColor(PirateColor.RED);
		gb.chooseColor(PirateColor.YELLOW);
		gb.chooseName("one");
		gb.chooseName("two");
		gb.chooseName("three");
		gb.chooseName("four");
		gb.chooseName("five");
		gb.chooseName("six");
		gb.chooseName("seven");
		GameSimulator gs = new GameSimulator(gb);
		gs.PlayBotGame();
	}
}