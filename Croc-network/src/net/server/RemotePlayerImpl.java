//package net.server;
//
//import java.rmi.RemoteException;
//import java.rmi.server.UnicastRemoteObject;
//
//import engine.models.Pirate;
//import engine.models.PirateColor;
//import net.headers.RemotePlayerInterface;
//
//public class RemotePlayerImpl extends UnicastRemoteObject implements RemotePlayerInterface {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	public String name;
//	public Pirate[] pirates;
//	public Boolean hasLost;
//	public Boolean isBot;
//	
//	public RemotePlayerImpl(int cardAmount, String name_, PirateColor color, Boolean isBot_) throws RemoteException {
//		name = name_;
//		
//		pirates = new Pirate[1];
//		pirates[0] = new Pirate(color, cardAmount);
//		hasLost = false;
//		isBot = isBot_;
//	}
//	
//	@Override
//	public PirateColor getColor() throws RemoteException {
//		return pirates[0].getColor();
//	}
//
//	@Override
//	public String getName() throws RemoteException {
//		return name;
//	}
//
//	@Override
//	public Pirate getPirate() throws RemoteException {
//		System.out.println("On arrive a RemotePlayerImpl.getPirate");
//		return pirates[0];
//	}
//
//}
