package View;

import static org.junit.Assert.assertEquals;

//import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

//import org.junit.Before;
import org.junit.Test;

import Controller.remoteController;

/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class testCalendar {
	
	
	public remoteController initialize() throws RemoteException{
		remoteController rc = new remoteController();
		return rc;
	}
	
	public int numRec() throws RemoteException{
		int res= initialize().numRecords();
		return res;
	}
	
	public int numFri(int userid) throws RemoteException{
		int res= initialize().numFriends(userid); // 1 is the current user ID
		return res;
	}
	
	

	@Test
	public void testLogin() throws SQLException {
		assertEquals("Login attempt failed", loginCheck(), true);
	}
	
	@Test
	public void testAddFriend(){ // Needs to be added
		assertEquals("Friend wasn't added correctly", addFriend(1,2), true);
	}
	
	@Test
	public void testGetFriend(){
		assertEquals("Unable to retrieve friends", getFriend(1), true);
	}
	
	@Test
	public void testAddEvent() throws SQLException{
		assertEquals("Event wasn't added correctly", addEvent(), true);
	}
	
	@Test
	public void testGetNextEvent(){
		assertEquals("Next event isn't retrievable", getNextEvent(), true);
	}
	
	@Test
	public void testGetEvent(){
		assertEquals("Event with that eventid was unreachable or wasn't found", getEvent(2),true);
	}
	
	@Test
	public void batchTest() throws RemoteException, SQLException{
		
		// Checks that invalid logins aren't accepted
		assertEquals("Incorrect details were accepted", initialize().isLoginValid("username", "password"), false);
		assertEquals("", initialize().isLoginValid("dg000", "dg145"), false );
		// Checks correct details are accepted
		assertEquals("Login username and/or password are incorrect", initialize().isLoginValid("dg145", "dg000"), true);
		
		// Checks that the events are being 
		int events = numRec();
		assertEquals("Incorrect number of records", events, numRec());
		addEvent("new desc 1","attending unkown","LT1","2014-06-15","9:00");
		addEvent("new desc 2","attending unkown","LT2","2014-06-15","11:30");
		assertEquals("Events may not have been added correctly", events+2, numRec());
		
		// Checks that friends can be retrieved and add successfully
		int friends = numFri(1);
		addFriend(1,2);
		addFriend(1,3);
		assertEquals("", friends+2, numFri(1));
		
		// Checks that events can be edited
		
	}
	
	
	public boolean loginCheck() throws SQLException{
		try {
			if (initialize().isLoginValid("sp122","sp999")){
				return true;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addFriend(int userid, int friendid){
		try {
			if(initialize().addFriend(userid, friendid)){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getFriend(int userid){
		try {
			if (!initialize().getFriends(userid).isEmpty()){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean addEvent() throws SQLException{
		try {
			if (initialize().addEvent("Empty Descripton", "Attending Unknown", "W504", "2014-06-12", "16:00")){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addEvent(String desc, String attend, String room, String date, String time){
		return false;
	}
	
	public boolean getNextEvent(){
		try {
			if (initialize().getNextEvent()){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getEvent(int eventid){
		try {
			if (initialize().getEvent(eventid)){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean editEvent(String desc, String attend, String room, String date, String time) throws RemoteException, SQLException, ClassNotFoundException{
		int eventid = initialize().eventID(desc);
		try{
			if (initialize().editEvent(eventid, desc, attend, room, date, time)){
				return true;
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return false;
	}
}
