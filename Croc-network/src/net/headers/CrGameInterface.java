package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import engine.models.Pirate;
import engine.models.Player;

public interface CrGameInterface extends Remote {
	public ArrayList<Pirate> getPirateOrder() throws RemoteException;
	public Player[] getPlayers() throws RemoteException;
	public Player getWinner() throws RemoteException;
}
