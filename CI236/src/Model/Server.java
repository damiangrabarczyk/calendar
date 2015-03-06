package Model;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import CalendarInterface.ServerConstants;
import Controller.remoteController;


/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		remoteController remoteImp = new remoteController();

		Registry reg = LocateRegistry.createRegistry(ServerConstants.RMI_PORT);
		reg.rebind(ServerConstants.RMI_ID,remoteImp);


		System.out.println("server is started");
		System.out.println("connected");
	}

}
