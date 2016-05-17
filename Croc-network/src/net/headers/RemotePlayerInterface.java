package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.Pirate;
import engine.models.PirateColor;

public interface RemotePlayerInterface extends Remote {
	public PirateColor getColor() throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public Pirate getPirate() throws RemoteException;
}
