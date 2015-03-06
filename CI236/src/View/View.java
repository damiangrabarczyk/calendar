package View;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.swing.JFrame;

public class View {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws SQLException 
	 */
	/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
	public static void main(String[] args) throws RemoteException, NotBoundException, SQLException {
		

		JFrame frame = new calendarView("Calendar");
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		   
		System.out.println("View is running");
		
		   
		
	}
	
	



}
