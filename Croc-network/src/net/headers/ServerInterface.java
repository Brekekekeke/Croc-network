package net.headers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import engine.models.PirateColor;

public interface ServerInterface extends Remote {
//	public boolean getSharkTurn() throws RemoteException;
	public void addPlayer(String name, boolean isBot, PirateColor color);
	public CrGameInterface createGame();
	public void initGame();
	public void resolveTurn();

}
